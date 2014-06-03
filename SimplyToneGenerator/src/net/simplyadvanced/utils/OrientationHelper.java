package net.simplyadvanced.utils;

import android.app.Activity;
import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;

/** Helper class to reduce duplicate code. The OrientationUtils version
 * of this class has no outside dependencies. */
public class OrientationHelper {
	private OrientationHelper() {}
	
	/** Sets whether or not window should be locked, as defined by user in
	 * settings. */
	public static void setOrientationMode(Activity activity) {
		UserPrefs mUserPrefs = UserPrefs.getInstance();
		if (mUserPrefs.isLockOrientationPortrait()) {
			OrientationUtils.lockOrientationPortrait(activity);
		} else {
			OrientationUtils.unlockOrientation(activity);
		}
	}
	
}
