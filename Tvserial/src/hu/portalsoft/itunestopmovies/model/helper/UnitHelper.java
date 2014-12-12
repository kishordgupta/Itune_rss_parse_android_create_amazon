package hu.portalsoft.itunestopmovies.model.helper;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Helper class for converting between android units
 * @author sagi
 *
 */
public class UnitHelper {

	/**
	 * Convert dp value to px value
	 * @param context The context of operation
	 * @param dpValue The dp value to be converted to px
	 * @return The px value
	 */
	public static int dpToPx(Context context, int dpValue) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int)((dpValue * displayMetrics.density) + 0.5);
	}
	
	/**
	 * Convert px value to dp value
	 * @param context The context of operation
	 * @param pxValue The px value to be converted to dp
	 * @return The dp value
	 */
	public static int pxToDp(Context context, int pxValue) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int) ((pxValue/displayMetrics.density)+0.5);
	}

	/**
	 * Convert sp value to px value
	 * @param context The context of operation
	 * @param spValue The dp value to be converted to px
	 * @return The px value
	 */
	public static int spToPx(Context context, int spValue) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int)((spValue * displayMetrics.scaledDensity) + 0.5);
	}
	
	/**
	 * Convert px value to sp value
	 * @param context The context of operation
	 * @param pxValue The sx value to be converted to dp
	 * @return The sp value
	 */
	public static int pxToSp(Context context, int pxValue) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int) ((pxValue/displayMetrics.scaledDensity)+0.5);
	}
	
	
}
