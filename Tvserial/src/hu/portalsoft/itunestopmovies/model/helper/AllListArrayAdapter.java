package hu.portalsoft.itunestopmovies.model.helper;


import hu.portalsoft.itunestopmovies.model.ITunesMovie;
import hu.portalsoft.itunestopmovies.model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.w3c.dom.Text;

import com.tvserial.plot.amazon.trailer.MainActivity;
import com.tvserial.plot.amazon.trailer.R;
import com.tvserial.plot.amazon.trailer.URLlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Array adapter class for movie list
 * @author sagi
 *
 */
public class AllListArrayAdapter extends ArrayAdapter<ITunesMovie> {
    
	/**
	 * Tag for logging
	 */
	private static final String TAG = AllListArrayAdapter.class.getSimpleName();
	
	String webdata="";
	private final LayoutInflater mInflater;
    
	public AllListArrayAdapter(Context context, int resource, List<ITunesMovie> objects) {
		super(context, resource, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	
	/**
	 * Set data in array adapter
	 * @param data Movie data list
	 */
	public void setData(List<ITunesMovie> data) {
		//clear current data
		clear();
		webdata=URLlist.Sdata;
		//if new data list is not null
		if (data != null) {
			//add all movie from new data list to array adapter
			addAll(data);
			Log.d(TAG, "Movie list updated: " + data.size() + " movie" + (1 < data.size() ? "s" : "") + " are in the updated list");
		} else {
			Log.d(TAG, "Movie list cleared");
		}//if
		
	} 

    /**
     * Populate new items in the list.
     */
	int i =0;
	int j =50;
    @Override public View getView(int position, View convertView, ViewGroup parent) {
    	
    	Log.d("kd", "getView(" + position + ")" + "All");
    	
    	  int l = j%50;
    	  Log.d("kd1",l+" i " + j + " j");
    	//get model instance
    	  Model model = URLlist.All;
        //get movie by position
        ITunesMovie movie = model.getMovieByPosition(position);
    	
    	//view holder keeps reference to avoid further findViewbyId() calls
    	ViewHolder viewHolder;
    	 
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item_layout, parent, false);
            //create view holder for new view
            viewHolder = new ViewHolder();
            //set view holder fields from layout
            viewHolder.wa = (WebView) view.findViewById(R.id.amazon);
            viewHolder.rankTextView = ((TextView)view.findViewById(R.id.rankTextView));
            viewHolder.movieSmallImageLoadingProgressBar = ((ProgressBar)view.findViewById(R.id.movieSmalleImageLoadingProgressBar));
            viewHolder.movieSmallImageView = ((ImageView)view.findViewById(R.id.movieSmallImageView));
            viewHolder.movieTitleTextView = ((TextView)view.findViewById(R.id.movieTitleTextView));
            viewHolder.movieCopyrightTextView = ((TextView)view.findViewById(R.id.movieCopyrightTextView));
            viewHolder.freeitune = ((TextView)view.findViewById(R.id.freeitune));
           
            //set view holder in view
            view.setTag(viewHolder);
           
        } else {
        	j++;
   
        	//use convert view
            view = convertView;

            //get view holder from view
            viewHolder = (ViewHolder) view.getTag();
          
            //this movie is ready
            if (movie.getId() == viewHolder.movieId) {
            	Log.d(TAG, "View is up to date, reusing...");
            	
            
                
            	return view;
            }
         
            //if
            
        }//if

        Log.d(TAG, "Setting up view...");
        
        //update movie id
        
        
        viewHolder.movieId = movie.getId();
        
        	
        viewHolder.wa.getSettings().setJavaScriptEnabled(true);
        String D = webdata;
        viewHolder.wa.setLabelFor(position);
    
        viewHolder.wa.clearHistory();
        viewHolder.wa.loadUrl("about:blank");
       
        viewHolder.wa.loadData(D.replace("#XX#",URLlist.All.getMovieByPosition(position).getTitle().replaceAll(".*-", "").replace(" ","%20") ), "text/html", "utf-8");
        viewHolder.wa.setBackgroundColor(Color.TRANSPARENT);
        viewHolder.wa.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
   
      
          viewHolder.rankTextView.setTag(movie.getId());
             viewHolder.rankTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 ITunesMovie movie = URLlist.All.getMovieByPosition(Integer.parseInt(v.getTag()+""));
				Intent browserIntent = new Intent(MainActivity.context, com.bangla.natok.prova.MainActivity.class);
				 com.bangla.natok.prova.MainActivity.url=movie.getTitle().replaceAll(".*-", "").toString();
				MainActivity.context.startActivity(browserIntent);
			}
		});
        //
        
        //-
        //update movie image (if possible)
        //-
        if (null != movie.getSmallImageUrl()) {

        	//start background image loader (using multiple cores)
        	new ImageLoader(viewHolder.movieSmallImageView, viewHolder.movieSmallImageLoadingProgressBar, movie.getImageUrl(), 60, 90).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        
        }//if
        //-
        
        //update movie title
        viewHolder.movieTitleTextView.setText(movie.getTitle());
        
        //update movie copyright
        viewHolder.movieCopyrightTextView.setText(movie.getSummary());
        viewHolder.movieCopyrightTextView.setMaxLines(3);
        viewHolder.movieCopyrightTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 ((TextView) v).setMaxLines(100);
			}
		});
        return view;
    }
    
    /**
     * View holder for performance speedup
     * @author sagi
     *
     */
    static class ViewHolder {
    	 WebView wa;
		Long movieId;
    	TextView rankTextView;
    	ProgressBar movieSmallImageLoadingProgressBar;
    	ImageView movieSmallImageView;
    	TextView movieTitleTextView;
    	TextView movieCopyrightTextView;
    	TextView freeitune;
    }
}
