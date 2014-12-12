package hu.portalsoft.itunestopmovies;



import com.tvserial.plot.amazon.trailer.MainActivity;
import com.tvserial.plot.amazon.trailer.R;

import hu.portalsoft.itunestopmovies.model.ITunesMovie;
import hu.portalsoft.itunestopmovies.model.Model;
import hu.portalsoft.itunestopmovies.model.helper.ImageLoader;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single Movie detail screen. This fragment is either
 * contained in a {@link MovieListActivity} in two-pane mode (on tablets) or a
 * {@link MovieDetailActivity} on handsets.
 */
public class MovieDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * Tag for logging
	 */
	private static final String TAG = MovieDetailFragment.class.getSimpleName();

	/**
	 * The dummy content this fragment is presenting.
	 */
	private ITunesMovie mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MovieDetailFragment() {
	}
 Context c = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate");
c=getActivity().getApplicationContext();
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			
			Model model = Model.getInstance();
			
			// Load the content specified by the fragment arguments. 
			mItem = model.getMovieByPosition(getArguments().getInt(ARG_ITEM_ID));
			
			
			//if no valid movie selected by the arguments and there is at least one movie
			if (null == mItem && 0 < model.getMovies().size()) {
				//select the first movie from the movies list
				Log.d(TAG, "Selecting the first available movie...");
				mItem = model.getMovieByPosition(0);
			}
			
		//	Toast.makeText(getActivity().getApplicationContext(), mItem.getPrivewUrl().toString()+"", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");
		
		//set movie title
		this.getActivity().setTitle(mItem.getTitle());

		View rootView = inflater.inflate(R.layout.fragment_movie_detail,
				container, false);

		if (null == mItem) {
			Log.w(TAG, "Movie item is null");
			return rootView;
		}//if
		
		//get movie details image loading progress bar
		ProgressBar movieImageLoadingProgressBar = (ProgressBar)rootView.findViewById(R.id.movieDetailsImageLoadingProgressBar);
		//get movie details image view
		ImageView movieImage = (ImageView)rootView.findViewById(R.id.movieDetailsImageView);
		movieImage.setOnClickListener(new OnClickListener() {
			final String s = mItem.getPrivewUrl().toString();
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i = new Intent(c,MainActivity.class)	;
			i.putExtra("ur",  s);
			startActivity(i);
			}
		});
		//-
        //set movie image (if possible)
        //-
        if (null != mItem.getImageUrl()) {

        	//start background image loader (using multiple cores)
        	new ImageLoader(movieImage, movieImageLoadingProgressBar, mItem.getImageUrl(), 113, 170).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        	Log.w("dfgdfg",  mItem.getPrivewUrl().toString()+"");
        }//if
        //-		
		
        //set movie summary
		((TextView) rootView.findViewById(R.id.movieDetailsSummaryTextView)).setText(mItem.getSummary());

		return rootView;
	}

	
}
