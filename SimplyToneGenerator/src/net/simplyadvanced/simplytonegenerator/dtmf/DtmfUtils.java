package net.simplyadvanced.simplytonegenerator.dtmf;

import net.simplyadvanced.simplytonegenerator.HelperCommon;
import net.simplyadvanced.simplytonegenerator.UserPrefs;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.text.TextUtils;
import android.util.Log;

/** Handles common methods related to DTMF tones. */
public class DtmfUtils {
	private static final String LOG_TAG = "DEBUG: DtmfUtils";
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}
	
	private static final int DEFAULT_TONE_LENGTH_MS = 537; // Milliseconds. // -1 for infinite loop.
	
	private static DtmfUtils mDtmfUtilsInstance;
	
	/** To only be used in the one method to allow stopping when pressed again while playing. */
	private static volatile boolean mIsTonePlaying = false;

	private ToneGenerator mToneGenerator;
	private ToneGenerator mToneGenerator0;
	private ToneGenerator mToneGenerator1;
	private ToneGenerator mToneGenerator2;
	private ToneGenerator mToneGenerator3;
	private ToneGenerator mToneGenerator4;
	private ToneGenerator mToneGenerator5;
	private ToneGenerator mToneGenerator6;
	private ToneGenerator mToneGenerator7;
	private ToneGenerator mToneGenerator8;
	private ToneGenerator mToneGenerator9;
	private ToneGenerator mToneGeneratorA;
	private ToneGenerator mToneGeneratorB;
	private ToneGenerator mToneGeneratorC;
	private ToneGenerator mToneGeneratorD;
	private ToneGenerator mToneGeneratorP;
	private ToneGenerator mToneGeneratorS;
	
	
	private DtmfUtils() {
    	try {
			mToneGenerator  = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator0 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator1 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator2 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator3 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator4 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator5 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator6 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator7 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGenerator8 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume()); // TODO: Fix crash bug here! Too many new ToneGenerator.
			mToneGenerator9 = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorA = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorB = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorC = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorD = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorP = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
			mToneGeneratorS = new ToneGenerator(AudioManager.STREAM_MUSIC, UserPrefs.getVolume());
    	} catch (Exception e) {
    		if (HelperCommon.IS_DEBUG_MODE) {
	    		log("ERROR: " + e.getMessage());
	    		e.printStackTrace();
    		}
    	}
	}
	
	public static DtmfUtils getInstance() {
		if (mDtmfUtilsInstance == null) {
			mDtmfUtilsInstance = new DtmfUtils();
		}
		return mDtmfUtilsInstance;
	}
	
	

	public void playSoundDtmf(final int toneType) {
		playSoundDtmf(toneType, DEFAULT_TONE_LENGTH_MS);
	}
	
	public void playSoundDtmf(final int toneType, final int duration) {
		stopSoundDtmf();
		mToneGenerator.startTone(toneType, duration);
	}
	
	public void stopSoundDtmf() {
		if (mToneGenerator != null) {
			mToneGenerator.stopTone();	
		}
	}


	/**
	 * Plays the input string in a separate thread.
	 * 
	 * @param tonePhrase
	 * @param timePerDigit
	 * @param pause
	 */
	public void playOrStopDtmfString(final String tonePhrase, final int timePerDigit, final int pause) {
		if (TextUtils.isEmpty(tonePhrase)) { return; }
		
		if (mIsTonePlaying) {
			// Stop tone.
			mIsTonePlaying = false;
		} else {
			mIsTonePlaying = true;
			final int length = tonePhrase.length();
			final int totalWaitTime = pause + timePerDigit;
			
		    new Thread(new Runnable() {
		        public void run() {
		    		for (int i = 0; i < length; i++) {
		    			if (!mIsTonePlaying) { break; }
		    			final int index = i;
		    			
						playSoundDtmf(getToneTypeFromString("" + tonePhrase.charAt(index)), timePerDigit);
					    try {
							Thread.sleep(totalWaitTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		    		}
					mIsTonePlaying = false;
		        }
		    }).start();
		}
	}
	

	public void start0() { if (!isNull(mToneGenerator0)) { mToneGenerator0.startTone(ToneGenerator.TONE_DTMF_0); }}
	public void start1() { if (!isNull(mToneGenerator1)) { mToneGenerator1.startTone(ToneGenerator.TONE_DTMF_1); }}
	public void start2() { if (!isNull(mToneGenerator2)) { mToneGenerator2.startTone(ToneGenerator.TONE_DTMF_2); }}
	public void start3() { if (!isNull(mToneGenerator3)) { mToneGenerator3.startTone(ToneGenerator.TONE_DTMF_3); }}
	public void start4() { if (!isNull(mToneGenerator4)) { mToneGenerator4.startTone(ToneGenerator.TONE_DTMF_4); }}
	public void start5() { if (!isNull(mToneGenerator5)) { mToneGenerator5.startTone(ToneGenerator.TONE_DTMF_5); }}
	public void start6() { if (!isNull(mToneGenerator6)) { mToneGenerator6.startTone(ToneGenerator.TONE_DTMF_6); }}
	public void start7() { if (!isNull(mToneGenerator7)) { mToneGenerator7.startTone(ToneGenerator.TONE_DTMF_7); }}
	public void start8() { if (!isNull(mToneGenerator8)) { mToneGenerator8.startTone(ToneGenerator.TONE_DTMF_8); }}
	public void start9() { if (!isNull(mToneGenerator9)) { mToneGenerator9.startTone(ToneGenerator.TONE_DTMF_9); }}
	public void startA() { if (!isNull(mToneGeneratorA)) { mToneGeneratorA.startTone(ToneGenerator.TONE_DTMF_A); }}
	public void startB() { if (!isNull(mToneGeneratorB)) { mToneGeneratorB.startTone(ToneGenerator.TONE_DTMF_B); }}
	public void startC() { if (!isNull(mToneGeneratorC)) { mToneGeneratorC.startTone(ToneGenerator.TONE_DTMF_C); }}
	public void startD() { if (!isNull(mToneGeneratorD)) { mToneGeneratorD.startTone(ToneGenerator.TONE_DTMF_D); }}
	public void startP() { if (!isNull(mToneGeneratorP)) { mToneGeneratorP.startTone(ToneGenerator.TONE_DTMF_P); }}
	public void startS() { if (!isNull(mToneGeneratorS)) { mToneGeneratorS.startTone(ToneGenerator.TONE_DTMF_S); }}

	public void stop0() { if (!isNull(mToneGenerator0)) { mToneGenerator0.stopTone(); }}
	public void stop1() { if (!isNull(mToneGenerator1)) { mToneGenerator1.stopTone(); }}
	public void stop2() { if (!isNull(mToneGenerator2)) { mToneGenerator2.stopTone(); }}
	public void stop3() { if (!isNull(mToneGenerator3)) { mToneGenerator3.stopTone(); }}
	public void stop4() { if (!isNull(mToneGenerator4)) { mToneGenerator4.stopTone(); }}
	public void stop5() { if (!isNull(mToneGenerator5)) { mToneGenerator5.stopTone(); }}
	public void stop6() { if (!isNull(mToneGenerator6)) { mToneGenerator6.stopTone(); }}
	public void stop7() { if (!isNull(mToneGenerator7)) { mToneGenerator7.stopTone(); }}
	public void stop8() { if (!isNull(mToneGenerator8)) { mToneGenerator8.stopTone(); }}
	public void stop9() { if (!isNull(mToneGenerator9)) { mToneGenerator9.stopTone(); }}
	public void stopA() { if (!isNull(mToneGeneratorA)) { mToneGeneratorA.stopTone(); }}
	public void stopB() { if (!isNull(mToneGeneratorB)) { mToneGeneratorB.stopTone(); }}
	public void stopC() { if (!isNull(mToneGeneratorC)) { mToneGeneratorC.stopTone(); }}
	public void stopD() { if (!isNull(mToneGeneratorD)) { mToneGeneratorD.stopTone(); }}
	public void stopP() { if (!isNull(mToneGeneratorP)) { mToneGeneratorP.stopTone(); }}
	public void stopS() { if (!isNull(mToneGeneratorS)) { mToneGeneratorS.stopTone(); }}
	
	// Later might instantiate ToneGenerator for start().
	private boolean isNull(ToneGenerator tg) {
		return tg == null;
	}
	
	
	public static int getToneTypeFromString(String toneString) {
		if      (toneString.equalsIgnoreCase("0")) { return ToneGenerator.TONE_DTMF_0; }
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

	
	public static String getStringFromToneType(int toneType) {
		switch(toneType) {
		case ToneGenerator.TONE_DTMF_0: return "0";
		case ToneGenerator.TONE_DTMF_1: return "1";
		case ToneGenerator.TONE_DTMF_2: return "2";
		case ToneGenerator.TONE_DTMF_3: return "3";
		case ToneGenerator.TONE_DTMF_4: return "4";
		case ToneGenerator.TONE_DTMF_5: return "5";
		case ToneGenerator.TONE_DTMF_6: return "6";
		case ToneGenerator.TONE_DTMF_7: return "7";
		case ToneGenerator.TONE_DTMF_8: return "8";
		case ToneGenerator.TONE_DTMF_9: return "9";
		case ToneGenerator.TONE_DTMF_A: return "A";
		case ToneGenerator.TONE_DTMF_B: return "B";
		case ToneGenerator.TONE_DTMF_C: return "C";
		case ToneGenerator.TONE_DTMF_D: return "D";
		case ToneGenerator.TONE_DTMF_P: return "#";
		case ToneGenerator.TONE_DTMF_S: return "*";
		default: return "";
		}
	}
	
	
}
