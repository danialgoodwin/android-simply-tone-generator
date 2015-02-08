package net.simplyadvanced.util;

import android.content.Context;
import android.content.res.Configuration;

// More info: http://developer.android.com/guide/practices/screens_support.html
/** Static methods about device. */
public class DeviceUtils {
	private DeviceUtils() {}
	
	/** Returns true if device is at least 480x640 dp, otherwise false. */
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	/** Returns true if device is at least 720x960 dp, otherwise false. */
	public static boolean isTabletLarge(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

}
