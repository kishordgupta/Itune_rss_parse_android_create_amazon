package hu.portalsoft.itunestopmovies.model;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * File style cache class for URL -> content caching
 * @author sagi
 *
 */
public class UrlCache {

	/**
	 * Tag for logging
	 */
	private static final String TAG = UrlCache.class.getSimpleName();

	/**
	 * Singleton instance
	 */
	private static UrlCache instance = null;
	
	/**
	 * Singleton constructor
	 */
	private UrlCache() {}
	
	/**
	 * Get model instance
	 * @return Singleton model instance
	 */
	public synchronized static UrlCache getInstance() {
		//lazy instantiation
		//if no instance has been created
		if (null == instance) {
			//create new cache instance
			instance = new UrlCache();
		}
		//
		
		//return singleton instance
		return instance;
	}	
	
	/**
	 * Get file path for given key
	 * @param context The context
	 * @param key The key for the cached file
	 * @return The file path to the cached file
	 */
	public synchronized String get(Context context, URL key) {
		
			//create shared preferences
			SharedPreferences prefs = context.getSharedPreferences(UrlCache.class.getName() + "-Cache", Context.MODE_PRIVATE);
			//get cached value
			return prefs.getString(key.toString(), null);
	}
	
	/**
	 * Save data to the cache
	 * @param context The context
	 * @param key The key for the data
	 * @param dataStream The input stream of the data
	 */
	public synchronized void put(Context context, URL key, InputStream dataStream) {

		//create shared preferences
		SharedPreferences prefs = context.getSharedPreferences(UrlCache.class.getName() + "-Cache", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		//if key has already put to the cache
		if (null != prefs.getString(key.toString(), null)) {
			
			//remove cached value first
			//get cached file
			File cachedFile = new File(prefs.getString(key.toString(), null));
			//delete cached file
			cachedFile.delete();
			//delete key from cache
			editor.remove(key.toString());
			//
		}//if

		//file output stream for cached file
		FileOutputStream outputStream = null;
		
		//save file data
		try {
			
			//create random UUID for file
			String uuidFileName = UUID.randomUUID().toString();

			//create file output stream with uuid file name
			outputStream = context.openFileOutput(uuidFileName, Context.MODE_PRIVATE);

			//create data
			byte[] buffer = new byte[4096];
			//create counter for bytes read
			int bytes_read;

			//transfer data from input stream to output stream
			while ((bytes_read = dataStream.read(buffer)) != -1) {
			    // Read until EOF
			    outputStream.write(buffer, 0, bytes_read);
			}//while
			
			//put key - value to cache
			editor.putString(key.toString(), uuidFileName);
			
		} catch (Exception e) {
			
			Log.e(TAG, "Error saving data to the cache", e);
			return;
			
		} finally {
			
			try {
			      dataStream.close();
			} catch (Exception e) {
			}//try
			try {
				outputStream.close();
			} catch (Exception e) {
			}//try
		}//try
		//
		
		//commit changes
		editor.commit();
	}
}
