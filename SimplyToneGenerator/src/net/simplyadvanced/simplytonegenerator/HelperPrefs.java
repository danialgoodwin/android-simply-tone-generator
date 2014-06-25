package net.simplyadvanced.simplytonegenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}
	
	
	
	/******************/
	/* Initialization */
	/******************/
	
	/** The application context. */
	private Context mContext;

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

    private HelperPrefs(final Context context) {
    	mContext = context;
    	sPref = PreferenceManager.getDefaultSharedPreferences(mContext); // Mainly user preferences from ActivityPreferences.
//    	settings = context.getSharedPreferences(PREFS_NAME, 0); // Use this if don't need to access multiple processes.
    	settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); // Use this if need to access multiple processes.
    	// More modes: http://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String, int)
    }
	
    
    
	/******************************/
	/* Unchanging Preference Tags */
	/******************************/
	
    private static final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences sPref;
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
    
    
    
        
	/************************************/
    /* Default Shared Preferences Stuff */ // Call these for user preferences in Settings.
	/************************************/

    // How To Use:
	// boolean b = mHelperPrefs.getUserPreference(HelperPrefs.TAG_PREF_IS_ACTIVE, false);
	
	/** Returns default shared preference. */
	public boolean getUserPreference(final String key, final boolean defaultValue) {
		return sPref.getBoolean(key, defaultValue);
	}

	/** Returns default shared preference. */
	public String getUserPreference(final String key, final String defaultValue) {
		return sPref.getString(key, defaultValue);
	}

	/** Saves default shared preference. */
	public void setUserPreference(final String key, final String value) {
    	SharedPreferences.Editor editor = sPref.edit(); // Needed to make changes
    	editor.putString(key, value);
    	editor.commit(); // This line saves the edits
	}
    

	
	/****************************/
    /* Shared Preferences Stuff */
	/****************************/
    
	public void deleteSharedPreference(String key) {
		settings.edit().remove(key).commit();
	}
	public void deleteAllSharedPreference() { // UNTESTED
		settings.edit().clear().commit();
	}
	
	// Ex: HelperPrefs.saveSharedPreference(key, value);
	public void saveSharedPreference(String key, boolean value) {
    	SharedPreferences.Editor editor = settings.edit(); // Needed to make changes
    	editor.putBoolean(key, value);
    	editor.commit(); // This line saves the edits
	}
	public void saveSharedPreference(String key, float value) {
    	SharedPreferences.Editor editor = settings.edit(); // Needed to make changes
    	editor.putFloat(key, value);
    	editor.commit(); // This line saves the edits
	}
	public void saveSharedPreference(String key, int value) {
    	SharedPreferences.Editor editor = settings.edit(); // Needed to make changes
    	editor.putInt(key, value);
    	editor.commit(); // This line saves the edits
	}
	public void saveSharedPreference(String key, long value) {
    	SharedPreferences.Editor editor = settings.edit(); // Needed to make changes
    	editor.putLong(key, value);
    	editor.commit(); // This line saves the edits
	}
	public void saveSharedPreference(String key, String value) {
    	SharedPreferences.Editor editor = settings.edit(); // Needed to make changes
    	editor.putString(key, value);
    	editor.commit(); // This line saves the edits
	}

	// Ex: HelperPrefs.getSharedPreference(key, value);
	public boolean getSharedPreference(String key, boolean defaultValue) {
		return settings.getBoolean(key, defaultValue);
	}
	public float getSharedPreference(String key, float defaultValue) {
		return settings.getFloat(key, defaultValue);
	}
	public int getSharedPreference(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}
	public long getSharedPreference(String key, long defaultValue) {
		return settings.getLong(key, defaultValue);
	}
	public String getSharedPreference(String key, String defaultValue) {
		return settings.getString(key, defaultValue);
	}

}
