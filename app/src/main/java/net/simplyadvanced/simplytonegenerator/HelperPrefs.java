package net.simplyadvanced.simplytonegenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

// How To Use:
//    /** A singleton instance of HelperPrefs. */
//	private HelperPrefs mHelperPrefs;
//    
//	@Override
//	public void onCreate() {
//    	mHelperPrefs = HelperPrefs.getInstance(mContext);
//	}

/** Provides a convenient way to save preferences. */
public class HelperPrefs {
	private static final String LOG_TAG = "DEBUG: HelperPrefs";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(final String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}
	


    /** A singleton instance of HelperPrefs. */
    private static HelperPrefs sHelper;
    
    /** Returns a singleton instance of HelperPrefs. */
    public static HelperPrefs getInstance(final Context context) {
        if (sHelper == null) {
            // Always pass in the Application Context
            sHelper = new HelperPrefs(context.getApplicationContext());
        }
        return sHelper;
    }

    private HelperPrefs(Context context) {
//        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context); // Mainly user preferences from ActivityPreferences.
//    	settings = context.getSharedPreferences(PREFS_NAME, 0); // Use this if don't need to access multiple processes.
    	settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); // Use this if need to access multiple processes.
    	// More modes: http://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String, int)
    }
	
    
    
	/******************************/
	/* Unchanging Preference Tags */
	/******************************/
	
    private static final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences settings;
	
	private static final int DEFAULT_FRAGMENT_ACTIVITY_MAIN_SAVED_TAB_POSITION = 1; // Okay to change.
	
	private static final String TAG_PREF_FRAGMENT_ACTIVITY_MAIN_SAVED_TAB_POSITION = "prefFragmentActivityMainSavedTabPosition";
	private static final String TAG_PREF_NUMBER_OF_TIMES_OPENED_THIS_VERSION = "prefNumberOfTimesOpenedThisVersion" + HelperCommon.APP_VERSION;
	private static final String TAG_PREF_NUMBER_OF_TIMES_OPENED_APP = "prefNumberOfTimesOpenedApp";
	
    public static final String TAG_PREF_TOTAL_NUMBER_TIMES_OPENED = "prefTotalNumberTimesOpened";
    
    public static final String TAG_PREF_NAME = "prefName";
    
    
    
    /**************************************/
    /* Convenience Functions For All Apps */
    /**************************************/

    public void saveFragmentActivityMainSavedTabPosition(final int tabPosition) {
    	saveSharedPreference(TAG_PREF_FRAGMENT_ACTIVITY_MAIN_SAVED_TAB_POSITION, tabPosition);
    }
    
    public int getFragmentActivityMainSavedTabPosition() {
    	return getSharedPreference(TAG_PREF_FRAGMENT_ACTIVITY_MAIN_SAVED_TAB_POSITION, DEFAULT_FRAGMENT_ACTIVITY_MAIN_SAVED_TAB_POSITION);
    }

    public void saveNumberOfTimesOpenedApp(final int numTimesOpened) {
    	saveSharedPreference(TAG_PREF_NUMBER_OF_TIMES_OPENED_APP, numTimesOpened);
    }
    
    public int getNumberOfTimesOpenedApp() {
    	return getSharedPreference(TAG_PREF_NUMBER_OF_TIMES_OPENED_APP, 0);
    }
    
    public void saveNumberOfTimesOpenedThisVersion(final int numTimesOpened) {
    	saveSharedPreference(TAG_PREF_NUMBER_OF_TIMES_OPENED_THIS_VERSION, numTimesOpened);
    }
    
    public int getNumberOfTimesOpenedThisVersion() {
    	return getSharedPreference(TAG_PREF_NUMBER_OF_TIMES_OPENED_THIS_VERSION, 0);
    }
    

	
	/****************************/
    /* Shared Preferences Stuff */
	/****************************/

	public void saveSharedPreference(String key, int value) {
    	settings.edit().putInt(key, value).apply();
	}

	public int getSharedPreference(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}

}
