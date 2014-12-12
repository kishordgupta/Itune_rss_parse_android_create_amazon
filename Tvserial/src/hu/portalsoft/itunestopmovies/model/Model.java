package hu.portalsoft.itunestopmovies.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Application model class
 * @author sagi
 *
 */
public class Model {

	/**
	 * Tag for logging
	 */
	private static final String TAG = Model.class.getSimpleName();

	/**
	 * Singleton instance
	 */
	private static Model instance = null;

	/**
	 * Timestamp of last update
	 */
	private Long lastUpdateTimestamp = null;
	
	/**
	 * List of movies
	 */
	private List<ITunesMovie> movies = new ArrayList<ITunesMovie>();
	
	/**
	 * Singleton constructor
	 */
	public Model() {
		//reset movies list
		movies = new ArrayList<ITunesMovie>();
		
		Log.d(TAG, "Model created");
	};
	
	/**
	 * Get model instance
	 * @return Singleton model instance
	 */
	public synchronized static Model getInstance() {
		//lazy instantiation
		//if no instance has been created
		if (null == instance) {
			//create new model instance
			instance = new Model();
		}
		//
		
		//return singleton instance
		return instance;
	}	

	/**
	 * Get timestamp of last update
	 * @return Timestamp of last update
	 */
	public Long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	/**
	 * Set last update timestamp
	 * @param lastUpdateTimestamp Timestamp of last update
	 */
	public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	/**
	 * Get movies list
	 * @return The list of movies
	 */
	public List<ITunesMovie> getMovies() {
		//return a copy of the movie list (note: movie list must be read only for the layout)
		return (new ArrayList<ITunesMovie>(movies));
	}

	/**
	 * Set movies
	 * @param movies The list of movies
	 */
	public void setMovies(ArrayList<ITunesMovie> movies) {
		this.movies = movies;
	}
	
	/**
	 * Get movie by its movie list position 
	 * @param position The movie list position of the movie
	 * @return The movie if found by position, null otherwise
	 */
	public ITunesMovie getMovieByPosition(int position) {
		//return movie
		return movies.get(position);
	}

	/**
	 * Get movie by its movie id
	 * @param id The id of the movie
	 * @return The movie if found by id, null otherwise
	 */
	public ITunesMovie getMovieById(long id) {
		
		//process each movie
		for (int movieIndex = 0; movieIndex < movies.size(); movieIndex += 1) {
			//get next movie
			ITunesMovie movie = movies.get(movieIndex);
			//if movie id matches
			if (id == movie.getId()) {
				return movie;
			}//if
		}//for
		
		//movie not found, return null
		return null;
	}
	
}
