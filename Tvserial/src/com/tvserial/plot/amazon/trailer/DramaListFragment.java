package com.tvserial.plot.amazon.trailer;

import hu.portalsoft.itunestopmovies.model.ITunesMovie;
import hu.portalsoft.itunestopmovies.model.Model;
import hu.portalsoft.itunestopmovies.model.helper.DramaArrayAdapter;
import hu.portalsoft.itunestopmovies.model.helper.DramaListLoader;
import hu.portalsoft.itunestopmovies.model.helper.MovieListArrayAdapter;
import hu.portalsoft.itunestopmovies.model.helper.MovieListLoader;

import java.util.ArrayList;
import java.util.List;

import com.tvserial.plot.amazon.trailer.R;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DramaListFragment extends Fragment implements LoaderCallbacks<List<ITunesMovie>>{

	//Constant
	private static final String TAG = "StationListFragment";
	
	//Member variables
	private DramaArrayAdapter mAdapter;
	private List<ITunesMovie> mStations;
	private OnItemClickListener mListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		/*	//TODO Refactor
			Station station = mAdapter.getItem(position);
			MainActivity activity = (MainActivity) getActivity();
			activity.setStation(station);
			activity.justStop();
			activity.justPlay();
			 ViewPager pager2 = (ViewPager) activity.findViewById(R.id.pager); 
			 pager2.setCurrentItem(1);*/
		}
	};
	//View member variables
	private ListView mListView;
	
	/* Lifecycle functions
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mStations == null)
			mStations = new ArrayList<ITunesMovie>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stations, null);
		
		//ListView init
		mListView = (ListView) view.findViewById(R.id.lv_stations);
		mAdapter = new DramaArrayAdapter(getActivity(), R.layout.list_item_layout,  mStations);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mListener);
		getLoaderManager().initLoader(0, null, this);
		return view;
	}
	
	/* Public property access function
	 * 
	 */
	public void setStations(ArrayList<ITunesMovie> stations) {
		mStations = stations;
	    if (mAdapter != null) {
	    	mAdapter.clear();
	    	mAdapter.addAll(mStations);
	    	mAdapter.notifyDataSetChanged();
	    }
	}
	
	@Override
	public Loader<List<ITunesMovie>> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG, "onCreateLoader");
		// return new loader
		return new DramaListLoader(getActivity(),URLlist.Drama_URL);
	}

	@Override
	public void onLoadFinished(Loader<List<ITunesMovie>> loader, List<ITunesMovie> movies) {

		Log.d(TAG, "onLoadFinished");
		
		if (null != movies) {
			Log.d(TAG, movies.size() + " movie" + (1 < movies.size() ? "s" : "") + " loaded");
		}//if
		getActivity().setProgressBarIndeterminateVisibility(false);
		//set data in movie list adapter
		mAdapter.setData(movies);
		mAdapter.notifyDataSetChanged();
		// The list should now be shown.
        
	}

	@Override
	public void onLoaderReset(Loader<List<ITunesMovie>> arg0) {

		Log.d(TAG, "onLoaderReset");
		//clear data in movie list adapter
		mAdapter.setData(null);

	}


}
