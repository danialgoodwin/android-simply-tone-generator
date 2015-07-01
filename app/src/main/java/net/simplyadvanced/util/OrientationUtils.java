package net.simplyadvanced.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.Window;

/** Static methods related to device orientation. */
public class OrientationUtils {
    private OrientationUtils() {}
    
    /** Returns true if device in in portrait mode, otherwise false. */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /** Locks the device window in portrait mode. */
    public static void lockOrientationPortrait(Activity activity) {
    	activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
    /** Allows user to freely use portrait or landscape mode. */
    public static void unlockOrientation(Activity activity) {
    	activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    // Another method to keep screen on is to set a flag in the layout xml for `android:keepScreenOn="true"`.
    // More info: http://developer.android.com/reference/android/view/View.html#attr_android%3akeepScreenOn
    /** Force screen to stay on for a certain Activity.
     * @param activity the page to keep screen on
     * @param isKeepScreenOn set true to force screen on, set false to allow screen to turn off normally
     */
    public static void keepScreenOn(@NonNull Activity activity, boolean isKeepScreenOn) {
        Window window = activity.getWindow();
        if (window == null) { return; }
        if (isKeepScreenOn) {
            // More info: http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_KEEP_SCREEN_ON
            window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            // More info: http://developer.android.com/reference/android/view/Window.html#clearFlags%28int%29
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
