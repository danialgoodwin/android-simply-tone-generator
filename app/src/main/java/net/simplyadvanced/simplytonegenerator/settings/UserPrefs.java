package net.simplyadvanced.simplytonegenerator.settings;

import net.simplyadvanced.simplytonegenerator.R;

/** Use this class to access preferences that the user has set in the settings. */
public class UserPrefs extends AbstractPrefs {

	private static UserPrefs mUserPrefsInstance;
	
	/** The default volume to play sound at. */
	private static final int DEFAULT_VOLUME = 80; // 0-100.
	
	
	
	/** Returns a singleton of this class. */
	public static UserPrefs getInstance() {
		if (mUserPrefsInstance == null) {
			mUserPrefsInstance = new UserPrefs();
		}
		return mUserPrefsInstance;
	}

	private UserPrefs() {
		initializeDefaultPreferences();
	}
	
	
	
	/** Returns the default volume to play sound at. This is in
	 * a range of 0-100. */
	public static int getVolume() {
		return DEFAULT_VOLUME;
	}

	
	
	/** Returns true if letters should be shown underneath the DTMF numbers,
	 * otherwise false. Currently, not used. */
	public boolean isContinuousDtmfTone() {
		return get(mAppContext.getString(R.string.pref_continuous_dtmf_tones), true);
	}
	
	/** Returns true if letters should be shown underneath the DTMF numbers,
	 * otherwise false. */
	public boolean isShowLettersUnderDtmfNumbers() {
		return get(mAppContext.getString(R.string.pref_show_letters_with_dtmf_numbers), false);
	}
	
	/** Returns true if window orientation should be locked in portrait mode,
	 * otherwise false. */
	public boolean isLockOrientationPortrait() {
		return get(mAppContext.getString(R.string.pref_lock_in_portrait_orientation), false);
	}

	/** Returns the length of time that the DTMF tone should sound for. */
	public int getTimeForDtmfTone() {
		String timeString = get(mAppContext.getString(R.string.pref_time_for_tone), PrefFragment1.TAG_DEFAULT_TIME_FOR_TONE); 
		return Integer.parseInt(timeString);
	}

	/** Returns the length of time for the DTMF pause character. */
	public int getTimeForDtmfPause() {
		String timeString = get(mAppContext.getString(R.string.pref_time_for_pause), PrefFragment1.TAG_DEFAULT_TIME_FOR_PAUSE); 
		return Integer.parseInt(timeString);
	}
	
	/** Returns the length of time between DTMF tones. */
	public int getTimeForBetweenDtmfTones() {
		String timeString = get(mAppContext.getString(R.string.pref_time_between_tones), PrefFragment1.TAG_DEFAULT_TIME_BETWEEN_TONES); 
		return Integer.parseInt(timeString);
	}
	
}
