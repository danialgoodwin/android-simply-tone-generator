package net.simplyadvanced.simplytonegenerator.main.dtmf;

import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;

public class DtmfUtilsHelper {
	private DtmfUtilsHelper() {}
	
	public static void playOrStopDtmfString(String tonePhrase) {
		int timePerTone = UserPrefs.getInstance().getTimeForDtmfTone();
		int timeBetweenTones = UserPrefs.getInstance().getTimeForBetweenDtmfTones();
		DtmfUtils.getInstance().playOrStopDtmfString(tonePhrase, timePerTone, timeBetweenTones);
	}

}
