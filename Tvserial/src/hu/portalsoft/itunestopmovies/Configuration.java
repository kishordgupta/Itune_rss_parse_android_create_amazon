package hu.portalsoft.itunestopmovies;

/**
 * Configuration settings
 * @author sagi
 *
 */
public class Configuration {
	
	/**
	 * URL string of iTunes top movies list
	 */
	private static String ITUNES_TOP_MOVIES_LIST_URL = "https://itunes.apple.com/us/rss/toptvepisodes/limit=100/json";
	
	/**
	 * Value for small image height
	 */
	private static final int SMALL_IMAGE_HEIGHT = 60;

	/**
	 * Value for image height
	 */
	private static final int IMAGE_HEIGHT = 170;
	
	/**
	 * Value for bitmap cache size
	 */
	private static final int BITMAP_CACHE_SIZE = 4 * 1024 * 1024;
	
	
	/**
	 * Get iTunes top movies list URL string
	 * @return The URL string of iTunes top movies list
	 */
	public static String getITunesTopMoviesListUrlString() {
    	return ITUNES_TOP_MOVIES_LIST_URL;
	}
	
	/**
	 * Get small iTunes movie image height
	 * @return The height of iTunes movie small image
	 */
	public static int getSmallImageHeight() {
		return SMALL_IMAGE_HEIGHT;
	}

	/**
	 * Get iTunes movie image height
	 * @return The height of iTunes movie image
	 */
	public static int getImageHeight() {
		return IMAGE_HEIGHT;
	}

	/**
	 * Get bitmap cache size
	 * @return The size of the bitmap cache in bytes
	 */
	public static int getBitmapCacheSize() {
		return BITMAP_CACHE_SIZE;
	}
}
