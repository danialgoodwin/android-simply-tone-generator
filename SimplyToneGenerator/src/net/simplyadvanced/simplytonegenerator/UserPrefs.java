package net.simplyadvanced.simplytonegenerator;

public class UserPrefs {

	// Prevent instantiation.
	private UserPrefs() {}
	
	/** The default volume to play sound at. */
	private static final int mVolume = 80; // 0-100.
	
	/** Returns the default volume to play sound at. This is in
	 * a range of 0-100. */
	public static int getVolume() {
		return mVolume;
	}
	
}
