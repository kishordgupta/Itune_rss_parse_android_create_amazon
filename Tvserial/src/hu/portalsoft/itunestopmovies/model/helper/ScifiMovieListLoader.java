package hu.portalsoft.itunestopmovies.model.helper;

import hu.portalsoft.itunestopmovies.Configuration;
import hu.portalsoft.itunestopmovies.model.ITunesMovie;
import hu.portalsoft.itunestopmovies.model.Model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tvserial.plot.amazon.trailer.MainActivity;
import com.tvserial.plot.amazon.trailer.URLlist;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Async task loader class for background loading movie list
 * @author sagi
 *
 */
public class ScifiMovieListLoader extends AsyncTaskLoader<List<ITunesMovie>> {

	/**
	 * Tag for logging
	 */
	private static final String TAG = ScifiMovieListLoader.class.getSimpleName();
	public static  String WebURL ="https://itunes.apple.com/us/rss/topmovies/limit=100/json";

	public ScifiMovieListLoader(Context context) {
		super(context);
	}
	
	public ScifiMovieListLoader(Context context,String Url) {
		super(context);
		WebURL=Url;
	}

	@Override
	protected void onStartLoading() {
		
		Log.d("KD", "onStartLoading()" +"Action movie");

		super.onStartLoading();
		
		//get model
		Model model = Model.getInstance();
		//get movies from model
		List<ITunesMovie> movies = model.getMovies();
		
		//if movie list is empty
		if (0 == movies.size()) {
			Log.d(TAG, "No movie found in model, downloading top list from iTunes...");
			//start downloading movie list from iTunes
			forceLoad();
		} else {
			Log.d(TAG, "Delivering movies...");
			//deliver movies to adapter
			deliverResult(movies);
		}
	}

	

	
	@Override
	public List<ITunesMovie> loadInBackground() {
		
		Log.d(TAG, "loadInBackground()");
		
		//get target URL 
		String urlString =WebURL ;//Configuration.getITunesTopMoviesListUrlString();
		
    	//content data string
    	String contentDataString = null;

		//create HTTP client
        DefaultHttpClient client = new DefaultHttpClient();
        //create GET request with target URL
        HttpGet httpGet = new HttpGet(urlString);
    	
    	Log.d(TAG, "Downloading movie list from iTunes...");

    	//downloading content data
        try {
        	//get HTTP response
        	HttpResponse execute = client.execute(httpGet);
        	//get response entity input stream
        	InputStream content = execute.getEntity().getContent();
        	//open buffered reader to the input stream
        	BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
          
        	//stringbuffer for entity content data
        	StringBuffer contentDataBuffer = new StringBuffer();
        	//string for next entity data line
        	String dataLine = null;
        	//read entity content data to content data buffer
        	while ((dataLine = buffer.readLine()) != null) {
        		//add next data line to entity content data buffer
        		contentDataBuffer.append(dataLine + "\n");
        	}//while

        	//get content data from buffer
        	contentDataString = contentDataBuffer.toString();
        	
         } catch (Exception e) {
        	//handle exceptions
        	Log.e(TAG, "Error downloading iTunes movie list", e);
			//send 'operation failed' signal
        	return null;
        }//try
        //
        
        //set timestamp of last update
        Long modelLastUpdateTimestamp = System.currentTimeMillis();
        
        //create movie list for model
        ArrayList<ITunesMovie> modelMovies = new ArrayList<ITunesMovie>();
        //id for next movie
        long modelMovieId = 0;

        Log.d(TAG, "Parsing downloaded movie list...");
        
    	//parse content data
        try {
        	//create JSON object from content data string
        	JSONObject contentDataJson = new JSONObject(contentDataString);
        	
        	//get iTunes movie list items
        	JSONArray movieListItems = contentDataJson.getJSONObject("feed").getJSONArray("entry");
        	
        	//if movie list not found
        	if (null == movieListItems) {
        		Log.e(TAG, "iTunes movie list not found in response");
    			//send 'operation failed' signal
        		return null;
        	}//if
        	
        	//process each movie list item
        	for (int movieListItemIndex = 0; movieListItemIndex < movieListItems.length(); movieListItemIndex += 1) {

                //get next movie list itme
        		JSONObject movieListItem = movieListItems.getJSONObject(movieListItemIndex);
       	
        		//get movie title
        		String movieTitle = null;
        		try {
        			movieTitle = movieListItem.getJSONObject("title").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No title value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
        		
        		//get movie copyright notice
        		String movieCopyright = null;
        		try {
        			movieCopyright = movieListItem.getJSONObject("rights").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No copyright value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
        		URL movieSmallpriviewUrl = null;
        		
        		try {
        			
        			JSONArray movieImages = movieListItem.getJSONArray("link");

        			//process all images for small image
        			for (int movieImageIndex = 0; movieImageIndex < movieImages.length(); movieImageIndex += 1) {
        				//get next image
        				JSONObject movieImageJson = movieImages.getJSONObject(movieImageIndex);
        				//get next image height
        				String movieImageHeight = movieImageJson.getJSONObject("attributes").getString("href");
        				//if this image is a small image
        				//if (Configuration.getSmallImageHeight() == Integer.valueOf(movieImageHeight)) {
        					//set movie small URL
        				movieSmallpriviewUrl = new URL(movieImageHeight);
        					//skip further processing
        				//	break;
        				//}//if
        			}//for
        			//movieSmallpriviewUrl =new URL( movieListItem.getJSONObject("attributes").getString("href"));
        		} catch (JSONException je) {
        			Log.w(TAG, "No copyright value for movie, list item index: " + movieListItemIndex);
        		}
        		//get movie images
        		URL movieSmallImageUrl = null;
        		URL movieImageUrl = null;
        		try {
        			
        			//get movie images
        			JSONArray movieImages = movieListItem.getJSONArray("im:image");

        			//process all images for small image
        			for (int movieImageIndex = 0; movieImageIndex < movieImages.length(); movieImageIndex += 1) {
        				//get next image
        				JSONObject movieImageJson = movieImages.getJSONObject(movieImageIndex);
        				//get next image height
        			//	String movieImageHeight = movieImageJson.getJSONObject("attributes").getString("height");
        				//if this image is a small image
        			//	if (Configuration.getSmallImageHeight() == Integer.valueOf(movieImageHeight)) {
        					//set movie small URL
        					movieSmallImageUrl = new URL(movieImageJson.getString("label"));
        					//skip further processing
        					break;
        				//}//if
        			}//for

        			//process all images for image
        			for (int movieImageIndex = 0; movieImageIndex < movieImages.length(); movieImageIndex += 1) {
        				//get next image
        				JSONObject movieImageJson = movieImages.getJSONObject(movieImageIndex);
        				//get next image height
        			//	String movieImageHeight = movieImageJson.getJSONObject("attributes").getString("height");
        				//if this image is an image
        			//	if (Configuration.getImageHeight() == Integer.valueOf(movieImageHeight)) {
        					//set movie URL
        					movieImageUrl = new URL(movieImageJson.getString("label"));
        					//skip further processing
        			//		break;
        			//	}//if
        			}//for

        		} catch (JSONException je) {
        			Log.w(TAG, "No copyright value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
           		
        		//get movie summary
        		String movieSummary = null;
        		try {
        			movieSummary = movieListItem.getJSONObject("summary").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No summary value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
 
        		//create new movie instance
        		ITunesMovie movie = new ITunesMovie();
        		//set movie id
        		movie.setId(modelMovieId++);
        		//set movie title
        		movie.setTitle(movieTitle);
        		//set movie copyright notice
        		movie.setCopyright(movieCopyright);
        		//set movie small image URL
        		movie.setSmallImageUrl(movieSmallImageUrl);
        		//set movie image URL
        		movie.setImageUrl(movieImageUrl);
        		//set movie summary
        		movie.setSummary(movieSummary);
        		movie.setPrivewUrl(movieSmallpriviewUrl);
        		//add movie to movie list
        		modelMovies.add(movie);
        		
        	}//for
        	
        	//reset model
        	URLlist.Scifi= new Model();
        	//reset last update timestamp
        	URLlist.Scifi.setLastUpdateTimestamp(modelLastUpdateTimestamp);
        	//reset movies
        	URLlist.Scifi.setMovies(modelMovies);
        	//
        	
        	return modelMovies;
        } catch (Exception e) {
        	//handle exceptions
        	Log.e(TAG, "Error parsing iTunes movie list", e);
			//send 'operation failed' signal
        	return null;
        }//try
        //	
      }

}
