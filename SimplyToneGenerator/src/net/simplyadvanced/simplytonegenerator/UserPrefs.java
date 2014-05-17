package net.simplyadvanced.simplytonegenerator;

import android.content.Context;

public class UserPrefs {

	private static UserPrefs mUserPrefsInstance;
	
	private static Context mContext;
	
	/** The default volume to play sound at. */
	private static final int DEFAULT_VOLUME = 80; // 0-100.
	
	

    /** A singleton instance of HelperPrefs. */
	private HelperPrefs mHelperPrefs;
	
	// Prevent instantiation.
	private UserPrefs(Context context) {
		mContext = context;
    	mHelperPrefs = HelperPrefs.getInstance(context);
	}
	
	
	public static UserPrefs getInstance(Context context) {
		if (mUserPrefsInstance == null) {
			mUserPrefsInstance = new UserPrefs(context);
		}
		return mUserPrefsInstance;
	}
	
	
	
	
	
	/** Returns the default volume to play sound at. This is in
	 * a range of 0-100. */
	public static int getVolume() {
		return DEFAULT_VOLUME;
	}
	
	public boolean isShowLettersUnderDtmfNumbers() {
		return mHelperPrefs.getUserPreference(mContext.getString(R.string.pref_show_letters_with_dtmf_numbers), true);
	}
	
	
}
