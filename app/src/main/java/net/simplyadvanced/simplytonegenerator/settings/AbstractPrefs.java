package net.simplyadvanced.simplytonegenerator.settings;

import net.simplyadvanced.simplytonegenerator.App;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

// More modes: http://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String, int)
/** Extend this class to easily create different classes for specific uses.
 * Then, be sure to call super(...) so that this is properly initialized.
 * The extended classes should be singletons.
 * 
 *  
 *  Example use:
 *  <code>
    public class MyPrefs extends AbstractPrefs {
        
		private static MyPrefs mMyPrefs;
		
		private MyPrefs() {
			// Don't change this value. By changing it, you'll "delete" all of the users preferences.
			initialize("MyPrefs");
		}
		
		public static MyPrefs getInstance() {
			if (mMyPrefs == null) {
				mMyPrefs = new MyPrefs();
			}
			return mMyPrefs;
		}
   
	    private static final String PREFS_KEY_REMEMBER_ME = "prefsRememberMe";
		public void saveRememberMeState(boolean rememberMe) { save(PREFS_KEY_REMEMBER_ME, rememberMe); }
		public boolean getRememberMeState() { return get(PREFS_KEY_REMEMBER_ME, false); }
   
    }
 *  </code>
 *  
 *  */
public abstract class AbstractPrefs {

	protected static Context mAppContext = App.getContext();
	
	private SharedPreferences settings;
    
	
	
	protected void initialize(String nameOfPrefs) {
//    	settings = mAppContext.getSharedPreferences(nameOfPrefs, Context.MODE_PRIVATE);
    	settings = mAppContext.getSharedPreferences(nameOfPrefs, Context.MODE_MULTI_PROCESS); // Requires API 11.
	}
	
	/** Used mainly for preferences from ActivityPreferences. */
	protected void initializeDefaultPreferences() {
		settings = PreferenceManager.getDefaultSharedPreferences(mAppContext);
	}
	
	
	
	protected void save(String key, boolean value) {
    	settings.edit().putBoolean(key, value).apply();
	}
	protected void save(String key, float value) {
        settings.edit().putFloat(key, value).apply();
	}
	protected void save(String key, int value) {
        settings.edit().putInt(key, value).apply();
	}
	protected void save(String key, long value) {
        settings.edit().putLong(key, value).apply();
	}
	protected void save(String key, String value) {
        settings.edit().putString(key, value).apply();
	}
	

	protected boolean get(String key, boolean defaultValue) {
		return settings.getBoolean(key, defaultValue);
	}
	protected float get(String key, float defaultValue) {
		return settings.getFloat(key, defaultValue);
	}
	protected int get(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}
	protected long get(String key, long defaultValue) {
		return settings.getLong(key, defaultValue);
	}
	protected String get(String key, String defaultValue) {
		return settings.getString(key, defaultValue);
	}
	

	protected void delete(String key) {
		settings.edit().remove(key).apply();
	}
	
	protected void deleteAll() {
		settings.edit().clear().apply();
	}
	
}
