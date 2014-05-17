package net.simplyadvanced.simplytonegenerator.dtmf;

import android.media.ToneGenerator;

/** Handles common methods related to DTMF tones. */
public class DtmfUtils {
	
	/** Prevent instantiation. */
	private DtmfUtils() {}
	
	
	

	
	
	
// Only public temporarily.
	public static int getToneTypeFromString(final String toneString) {
		if (toneString.equalsIgnoreCase("0")) { return ToneGenerator.TONE_DTMF_0; }
		else if (toneString.equalsIgnoreCase("1")) { return ToneGenerator.TONE_DTMF_1; }
		else if (toneString.equalsIgnoreCase("2")) { return ToneGenerator.TONE_DTMF_2; }
		else if (toneString.equalsIgnoreCase("3")) { return ToneGenerator.TONE_DTMF_3; }
		else if (toneString.equalsIgnoreCase("4")) { return ToneGenerator.TONE_DTMF_4; }
		else if (toneString.equalsIgnoreCase("5")) { return ToneGenerator.TONE_DTMF_5; }
		else if (toneString.equalsIgnoreCase("6")) { return ToneGenerator.TONE_DTMF_6; }
		else if (toneString.equalsIgnoreCase("7")) { return ToneGenerator.TONE_DTMF_7; }
		else if (toneString.equalsIgnoreCase("8")) { return ToneGenerator.TONE_DTMF_8; }
		else if (toneString.equalsIgnoreCase("9")) { return ToneGenerator.TONE_DTMF_9; }
		else if (toneString.equalsIgnoreCase("A")) { return ToneGenerator.TONE_DTMF_A; }
		else if (toneString.equalsIgnoreCase("B")) { return ToneGenerator.TONE_DTMF_B; }
		else if (toneString.equalsIgnoreCase("C")) { return ToneGenerator.TONE_DTMF_C; }
		else if (toneString.equalsIgnoreCase("D")) { return ToneGenerator.TONE_DTMF_D; }
		else if (toneString.equalsIgnoreCase("#")) { return ToneGenerator.TONE_DTMF_P; }
		else if (toneString.equalsIgnoreCase("*")) { return ToneGenerator.TONE_DTMF_S; }
		return ToneGenerator.TONE_DTMF_0; // Error if code reaches here.
	}
	
}
