/**
 * Created by Danial on 7/1/2015.
 */
package net.simplyadvanced.simplytonegenerator;

import android.app.Activity;
import android.support.annotation.NonNull;

import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;
import net.simplyadvanced.util.OrientationHelper;
import net.simplyadvanced.util.OrientationUtils;

/** Use this class by calling it methods in the appropriate lifecycles of Activities */
public class ActivityCommon {

    /** No need to instantiate this class. */
    private ActivityCommon() {}

    /** Run common methods that should be called in each Activity. */
    public static void onCreate(@NonNull Activity activity) {
        OrientationHelper.setOrientationMode(activity);
    }

    /** Run common methods that should be called in each Activity. */
    public static void onStart(@NonNull Activity activity) {
        OrientationHelper.setOrientationMode(activity);
        OrientationUtils.keepScreenOn(activity, UserPrefs.getInstance().isKeepScreenOn());
    }

}
