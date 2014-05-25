package net.simplyadvanced.simplytonegenerator;

import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.support.v4.app.NavUtils;

public class ActivityPreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	private static final String LOG_TAG = "DEBUG: PreferenceActivity";


	// Don't change any of these two lines unless also changing the default in preferences.xml.
	public static final String TAG_DEFAULT_TIME_FOR_TONE = "93"; // milliseconds.
	public static final String TAG_DEFAULT_TIME_FOR_PAUSE = "40"; // milliseconds.
	
	
	private String[] pref_editWaitTimeText;

	
	private HelperPrefs mHelperPrefs;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Goes before setContextView().
        addPreferencesFromResource(R.xml.preferences); // Mainly use this line for API 11 and prior // Use fragments for API 11+

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		if (actionBar == null) { // ADDED: Hopefully, this will prevent some errors. TODOv2: Use a different method if null. 2014-05-23.
			// NOTE: If this happens, make sure that the ActionBar is enabled, preferrably via style xml.
			CustomToast.show(ActivityPreferences.this, "Sorry, this device is not supported yet, please email developer with your device type.");
			finish();
			return; // Just-in-case.
		}
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

        mHelperPrefs = HelperPrefs.getInstance(this);
		
		// Initializes summary
//        int tempKey = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_PREF_EDIT_WAIT_TIME, "1"));
//        String summary = "" + pref_editWaitTimeText[tempKey] + " between cycles";
//        findPreference(KEY_PREF_EDIT_WAIT_TIME).setSummary(summary);
		
		setupSummaries();
	}

	private void setupSummaries() {
		int tempInt;
		
		tempInt = Integer.parseInt(mHelperPrefs.getUserPreference(getString(R.string.pref_time_for_tone), TAG_DEFAULT_TIME_FOR_TONE));
		findPreference(getString(R.string.pref_time_for_tone)).setSummary(tempInt + " milliseconds");
		
		tempInt = Integer.parseInt(mHelperPrefs.getUserPreference(getString(R.string.pref_time_for_pause), TAG_DEFAULT_TIME_FOR_PAUSE));
		findPreference(getString(R.string.pref_time_for_pause)).setSummary(tempInt + " milliseconds");
	}
	

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//		if (key.equals(KEY_PREF_EDIT_WAIT_TIME)) {
//            Preference connectionPref = findPreference(key);
//            int tempKey = Integer.parseInt(sharedPreferences.getString(key, "1"));
//            connectionPref.setSummary("" + pref_editWaitTimeText[tempKey] + " between cycles"); // Set summary to be the user-description for the selected value
//        }
		if (key.equalsIgnoreCase(getString(R.string.pref_time_for_tone))) {
			String tempString;
        	int timeForTone;
        	try {
        		tempString = sharedPreferences.getString(key, TAG_DEFAULT_TIME_FOR_TONE);
        		if (tempString.equalsIgnoreCase("")) {
        			timeForTone = 0;
        			sharedPreferences.edit().putString(key, "0").commit();
        		} else {
        			timeForTone = Integer.parseInt(tempString);
        		}
        	} catch (Exception e) { // Number is too large probably.
        		timeForTone = Integer.MAX_VALUE;
    			sharedPreferences.edit().putString(key, String.valueOf(timeForTone)).commit();
        	}
			findPreference(key).setSummary(timeForTone + " milliseconds");
		} else if (key.equalsIgnoreCase(getString(R.string.pref_time_for_pause))) {
			String tempString;
        	int timeForPause;
        	try {
        		tempString = sharedPreferences.getString(key, TAG_DEFAULT_TIME_FOR_TONE);
        		if (tempString.equalsIgnoreCase("")) {
        			timeForPause = 0;
        			sharedPreferences.edit().putString(key, "0").commit();
        		} else {
        			timeForPause = Integer.parseInt(tempString);
        		}
        	} catch (Exception e) { // Number is too large probably.
        		timeForPause = Integer.MAX_VALUE;
    			sharedPreferences.edit().putString(key, String.valueOf(timeForPause)).commit();
        	}
			findPreference(key).setSummary(timeForPause + " milliseconds");
		} 
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	
	
	
	
	
	
	
	
	/**************/
	/* Menu Stuff */
	/**************/
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_preferences, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			finish();
//			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
