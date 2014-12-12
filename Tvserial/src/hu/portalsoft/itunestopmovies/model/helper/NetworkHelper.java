package hu.portalsoft.itunestopmovies.model.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helper class for network utils
 * @author sagi
 *
 */
public class NetworkHelper {
	
	/**
	 * Check if network is avaliable
	 * @param context The context
	 * @return True if network (any kind) is avaliable, false otherwise
	 */
	public static boolean isNetworkAvailable(Context context) {
		//get connectivity manager
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    //get active network info
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    //return network status
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
