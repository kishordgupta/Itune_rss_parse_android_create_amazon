package hu.portalsoft.itunestopmovies.model.helper;

import hu.portalsoft.itunestopmovies.model.BitmapCache;
import hu.portalsoft.itunestopmovies.model.UrlCache;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Helper class for background image loading
 * @author sagi
 *
 */
public class ImageLoader extends AsyncTask<Object, Void, Bitmap> {

	/**
	 * Tag for logging
	 */
	private static final String TAG = ImageLoader.class.getSimpleName();
	
	private ProgressBar progressBar;
	private ImageView imageView;
	private URL imageUrl;
	private int width;
	private int height;
	
	public ImageLoader(ImageView imageView, ProgressBar progressBar, URL imageUrl, int width, int height) {
	
		//set image view
		this.imageView = imageView;
		//set imageView tag for checking its validity in onPostExecute (note: listview array adapter reuses views and tag is needed to identify correct image view in list view item)
		imageView.setTag(imageUrl);
		
		//set progress bar
		this.progressBar = progressBar;
		
		//set image url
		this.imageUrl = imageUrl;
		
		//set image width
		this.width = width;
		//set image height
		this.height = height;
		
		//show progress bar
		progressBar.setVisibility(View.VISIBLE);		
		//hide image view
		imageView.setVisibility(View.GONE);
	}
	
	@Override
	protected Bitmap doInBackground(Object... params) {

		
		//cached bitmap
		Bitmap cachedBitmap = null;
		
		//get bitmap cache
		BitmapCache bitmapCache = BitmapCache.getInstance();
		//get cached bitmap
		cachedBitmap = bitmapCache.get(imageUrl.toString());
		//bitmap found in bitmap cache
		if (null != cachedBitmap) {
			Log.d(TAG, "Bitmap found in bitmap cache");
			return cachedBitmap;
		}//if
			
		//otherwise...
		
		//get cache instance
		UrlCache cache = UrlCache.getInstance();
		//check if movie file image has been cached before
        String cachedFilePath = cache.get(imageView.getContext(), imageUrl);

        //if file not found in cache
        if (null == cachedFilePath) {
        	
        	//-
        	//download image
        	//-
        	
	        try {
	        	
	        	//create connection
	        	URLConnection conn = imageUrl.openConnection();
	        	//open connection
	        	conn.connect();
	        	//get connection input stream
	        	InputStream connectionInputStream = conn.getInputStream();
	        	//add url and its content to cache
	        	cache.put(imageView.getContext(), imageUrl, connectionInputStream);
	        	//close connection input stream
	        	connectionInputStream.close();

	            //get cached file path from cache
	            cachedFilePath = cache.get(imageView.getContext(), imageUrl);

	         } catch (Exception e) {
	        	//handle exceptions
	        	Log.e(TAG, "Error downloading image: URL: " + imageUrl.toString(), e);
				//return
	        	return null;
	        }//try
        	
	        //-
        	
        } else {
        	
        	Log.d(TAG, "Bitmap file found in cache: URL: " + imageUrl.toString());
        
        }//if
        
    	try {
        	//create bitmap from cached file
	        cachedBitmap = BitmapHelper.loadScaledBitmap(imageView.getContext(), cachedFilePath, width, height);
	        //put bitmap in bitmap cache
	        bitmapCache.put(imageUrl.toString(), cachedBitmap);
	        //return bitmap
	        return cachedBitmap;
    	} catch (Exception e) {
    		Log.d(TAG, "Error loading cached movie small image", e);
    	}//try
		
		//return
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {

		//if tag of this imageview has been changed (note: view reusing can change its tag)
		if (false == imageView.getTag().toString().equals(imageUrl.toString())) {
			Log.d(TAG, "ImageView is handled by another async task (possible view reusing), no image update required");
			return;
		}//if

		Log.d(TAG, "Updating ImageView...");
		
		//update bitmap in image view
		imageView.setImageBitmap(result);
		
		//show image view
		imageView.setVisibility(View.VISIBLE);
		//hide progress bar
		progressBar.setVisibility(View.GONE);

	}
	
	

}
