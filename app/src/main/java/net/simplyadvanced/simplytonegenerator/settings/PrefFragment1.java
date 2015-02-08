package net.simplyadvanced.simplytonegenerator.settings;

import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.util.OrientationHelper;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

/** The fragment that will display the preferences. */
public class PrefFragment1 extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	// Don't change any of these two lines unless also changing the default in preferences.xml.
	public static final String TAG_DEFAULT_TIME_FOR_TONE = "93"; // Milliseconds.
	public static final String TAG_DEFAULT_TIME_FOR_PAUSE = "1000"; // Milliseconds.
	public static final String TAG_DEFAULT_TIME_BETWEEN_TONES = "40"; // Milliseconds.
	
	// Using this because Integer.MAX_VALUE caused app to crash in Thread.sleep()
	private static final int MAX_TIME_VALUE = 1000*60*60; // Milliseconds for 1 hour.
	
	/** A singleton instance. */
	private static UserPrefs mUserPrefs;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

    	mUserPrefs = UserPrefs.getInstance();
    	
		setupSummaries();
	}

	private void setupSummaries() {
		int tempInt;
		
		tempInt = mUserPrefs.getTimeForDtmfTone();
		findPreference(getString(R.string.pref_time_for_tone)).setSummary(tempInt + " milliseconds");
		
		tempInt = mUserPrefs.getTimeForDtmfPause();
		findPreference(getString(R.string.pref_time_for_pause)).setSummary(tempInt + " milliseconds");
		
		tempInt = mUserPrefs.getTimeForBetweenDtmfTones();
		findPreference(getString(R.string.pref_time_between_tones)).setSummary(tempInt + " milliseconds");
	}
	
	
	
	@SuppressLint("CommitPrefEdits")
    @Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (sharedPreferences == null || TextUtils.isEmpty(key)) { return; }
		
//		if (key.equals(KEY_PREF_EDIT_WAIT_TIME)) {
//            Preference connectionPref = findPreference(key);
//            int tempKey = Integer.parseInt(sharedPreferences.getString(key, "1"));
//            connectionPref.setSummary("" + pref_editWaitTimeText[tempKey] + " between cycles"); // Set summary to be the user-description for the selected value
//        }
		if (key.equalsIgnoreCase(getString(R.string.pref_time_for_tone))) {
    		String timeString = sharedPreferences.getString(key, TAG_DEFAULT_TIME_FOR_TONE);
        	int timeForTone = validTimeLength(timeString);
			sharedPreferences.edit().putString(key, String.valueOf(timeForTone)).commit();
			findPreference(key).setSummary(timeForTone + " milliseconds");
		} else if (key.equalsIgnoreCase(getString(R.string.pref_time_for_pause))) {
    		String timeString = sharedPreferences.getString(key, TAG_DEFAULT_TIME_FOR_PAUSE);
        	int timeForPause = validTimeLength(timeString);
			sharedPreferences.edit().putString(key, String.valueOf(timeForPause)).commit();
			findPreference(key).setSummary(timeForPause + " milliseconds");
		} else if (key.equalsIgnoreCase(getString(R.string.pref_time_between_tones))) {
    		String timeBetweenTones = sharedPreferences.getString(key, TAG_DEFAULT_TIME_BETWEEN_TONES);
        	int timeForPause = validTimeLength(timeBetweenTones);
			sharedPreferences.edit().putString(key, String.valueOf(timeForPause)).commit();
			findPreference(key).setSummary(timeForPause + " milliseconds");
		} else if (key.equalsIgnoreCase(getString(R.string.pref_lock_in_portrait_orientation))) {
			OrientationHelper.setOrientationMode(getActivity());
		}
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	    super.onPause();
	}
	
	
	
	/** Returns a valid time length for play or pause. */
	private static int validTimeLength(String timeString) {
		if (TextUtils.isEmpty(timeString)) { return 0; }
		
		int time;
		try {
			time = Integer.parseInt(timeString);
			if (time > MAX_TIME_VALUE) { time = MAX_TIME_VALUE; }
		} catch (Exception e) { // Don't care which exception it is.
			time = MAX_TIME_VALUE;
		}
		return time;
	}
	
}
