package net.simplyadvanced.simplytonegenerator.mainpage.dtmf;

import net.simplyadvanced.simplytonegenerator.App;
import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;
import net.simplyadvanced.util.AudioUtils;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.text.TextUtils;
import android.util.Log;

/** Handles common methods related to DTMF tones. */
public class DtmfUtils {
	private static final String LOG_TAG = "DEBUG: DtmfUtils";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}
	
	private static final int DEFAULT_TONE_LENGTH_MS = 537; // Milliseconds. // -1 for infinite playback.

	/** Stream type used to play the DTMF tones, and mapped to the volume control keys. */
    private static final int DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_DTMF;

    private static volatile Object mToneGeneratorLock = new Object();
    
	private static volatile DtmfUtils mDtmfUtilsInstance;

	/** This handles the DTMF phrase playbacks. */
	private ThreadedTonePlaybackThread mThreadedTonePlaybackThread;

	private volatile ToneGenerator mToneGenerator;
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

//	private volatile ToneGenerator mToneGenerator;
//	private volatile ToneGenerator mToneGenerator0;
//	private volatile ToneGenerator mToneGenerator1;
//	private volatile ToneGenerator mToneGenerator2;
//	private volatile ToneGenerator mToneGenerator3;
//	private volatile ToneGenerator mToneGenerator4;
//	private volatile ToneGenerator mToneGenerator5;
//	private volatile ToneGenerator mToneGenerator6;
//	private volatile ToneGenerator mToneGenerator7;
//	private volatile ToneGenerator mToneGenerator8;
//	private volatile ToneGenerator mToneGenerator9;
//	private volatile ToneGenerator mToneGeneratorA;
//	private volatile ToneGenerator mToneGeneratorB;
//	private volatile ToneGenerator mToneGeneratorC;
//	private volatile ToneGenerator mToneGeneratorD;
//	private volatile ToneGenerator mToneGeneratorP;
//	private volatile ToneGenerator mToneGeneratorS;
	
	
	private DtmfUtils() {
    	try {
			mToneGenerator  = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator0 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator1 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator2 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator3 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator4 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator5 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator6 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator7 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGenerator8 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume()); // TODO: Fix crash bug here! Too many new ToneGenerator.
			mToneGenerator9 = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorA = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorB = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorC = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorD = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorP = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
			mToneGeneratorS = new ToneGenerator(DIAL_TONE_STREAM_TYPE, UserPrefs.getVolume());
    	} catch (Exception e) {
//    		if (HelperCommon.IS_DEBUG_MODE) {
	    		log("ERROR: " + e.getMessage());
	    		e.printStackTrace();
//    		}
	        // Allows to try again. Hmmm, not really.. would have to return null?
    		mDtmfUtilsInstance = null;
    	}
	}
	
	public static DtmfUtils getInstance() {
		if (mDtmfUtilsInstance == null) {
			synchronized (mToneGeneratorLock) {
				if (mDtmfUtilsInstance == null) {
					mDtmfUtilsInstance = new DtmfUtils();
				}
			}
		}
		return mDtmfUtilsInstance;
	}
	
	

	public void playTone(final int toneType) {
		playTone(toneType, DEFAULT_TONE_LENGTH_MS);
	}
	
	public void playTone(final int toneType, final int duration) {
		if (!AudioUtils.isRingerSilent(App.getContext())) {
			if (mToneGenerator != null) {
				mToneGenerator.startTone(toneType, duration);
			}
		}
	}
	
	/** Plays the input string in a separate thread. */
	public void playOrStopDtmfString(String tonePhrase, int timePerTone, int timeBetweenTones) {
		if (TextUtils.isEmpty(tonePhrase)) { return; }
		
		if (mThreadedTonePlaybackThread != null  && mThreadedTonePlaybackThread.isAlive()) {
			mThreadedTonePlaybackThread.stopPlayback();
		} else {
			mThreadedTonePlaybackThread = ThreadedTonePlaybackThread.newInstance(
					tonePhrase, timePerTone, 1000, timeBetweenTones, UserPrefs.getVolume());
			mThreadedTonePlaybackThread.start();
//			mThreadedTonePlaybackThread.run();
		}
	}
	
	// Old code. May reuse later.
//	private void start0() { if (!isNull(mToneGenerator0)) { mToneGenerator0.startTone(ToneGenerator.TONE_DTMF_0); }}
//	private void start1() { if (!isNull(mToneGenerator1)) { mToneGenerator1.startTone(ToneGenerator.TONE_DTMF_1); }}
//	private void start2() { if (!isNull(mToneGenerator2)) { mToneGenerator2.startTone(ToneGenerator.TONE_DTMF_2); }}
//	private void start3() { if (!isNull(mToneGenerator3)) { mToneGenerator3.startTone(ToneGenerator.TONE_DTMF_3); }}
//	private void start4() { if (!isNull(mToneGenerator4)) { mToneGenerator4.startTone(ToneGenerator.TONE_DTMF_4); }}
//	private void start5() { if (!isNull(mToneGenerator5)) { mToneGenerator5.startTone(ToneGenerator.TONE_DTMF_5); }}
//	private void start6() { if (!isNull(mToneGenerator6)) { mToneGenerator6.startTone(ToneGenerator.TONE_DTMF_6); }}
//	private void start7() { if (!isNull(mToneGenerator7)) { mToneGenerator7.startTone(ToneGenerator.TONE_DTMF_7); }}
//	private void start8() { if (!isNull(mToneGenerator8)) { mToneGenerator8.startTone(ToneGenerator.TONE_DTMF_8); }}
//	private void start9() { if (!isNull(mToneGenerator9)) { mToneGenerator9.startTone(ToneGenerator.TONE_DTMF_9); }}
//	private void startA() { if (!isNull(mToneGeneratorA)) { mToneGeneratorA.startTone(ToneGenerator.TONE_DTMF_A); }}
//	private void startB() { if (!isNull(mToneGeneratorB)) { mToneGeneratorB.startTone(ToneGenerator.TONE_DTMF_B); }}
//	private void startC() { if (!isNull(mToneGeneratorC)) { mToneGeneratorC.startTone(ToneGenerator.TONE_DTMF_C); }}
//	private void startD() { if (!isNull(mToneGeneratorD)) { mToneGeneratorD.startTone(ToneGenerator.TONE_DTMF_D); }}
//	private void startP() { if (!isNull(mToneGeneratorP)) { mToneGeneratorP.startTone(ToneGenerator.TONE_DTMF_P); }}
//	private void startS() { if (!isNull(mToneGeneratorS)) { mToneGeneratorS.startTone(ToneGenerator.TONE_DTMF_S); }}


	public void stopTone() {
		if (mThreadedTonePlaybackThread != null) {
			mThreadedTonePlaybackThread.stopPlayback();
		}
		if (!isNull(mToneGenerator)) { mToneGenerator.stopTone(); }
	}
	private void stop0() { if (!isNull(mToneGenerator0)) { mToneGenerator0.stopTone(); }}
	private void stop1() { if (!isNull(mToneGenerator1)) { mToneGenerator1.stopTone(); }}
	private void stop2() { if (!isNull(mToneGenerator2)) { mToneGenerator2.stopTone(); }}
	private void stop3() { if (!isNull(mToneGenerator3)) { mToneGenerator3.stopTone(); }}
	private void stop4() { if (!isNull(mToneGenerator4)) { mToneGenerator4.stopTone(); }}
	private void stop5() { if (!isNull(mToneGenerator5)) { mToneGenerator5.stopTone(); }}
	private void stop6() { if (!isNull(mToneGenerator6)) { mToneGenerator6.stopTone(); }}
	private void stop7() { if (!isNull(mToneGenerator7)) { mToneGenerator7.stopTone(); }}
	private void stop8() { if (!isNull(mToneGenerator8)) { mToneGenerator8.stopTone(); }}
	private void stop9() { if (!isNull(mToneGenerator9)) { mToneGenerator9.stopTone(); }}
	private void stopA() { if (!isNull(mToneGeneratorA)) { mToneGeneratorA.stopTone(); }}
	private void stopB() { if (!isNull(mToneGeneratorB)) { mToneGeneratorB.stopTone(); }}
	private void stopC() { if (!isNull(mToneGeneratorC)) { mToneGeneratorC.stopTone(); }}
	private void stopD() { if (!isNull(mToneGeneratorD)) { mToneGeneratorD.stopTone(); }}
	private void stopP() { if (!isNull(mToneGeneratorP)) { mToneGeneratorP.stopTone(); }}
	private void stopS() { if (!isNull(mToneGeneratorS)) { mToneGeneratorS.stopTone(); }}
	
	public void stopAll() {
		if (mThreadedTonePlaybackThread != null) {
			mThreadedTonePlaybackThread.stopPlayback();
		}
		stopTone();
		stop0();
		stop1();
		stop2();
		stop3();
		stop4();
		stop5();
		stop6();
		stop7();
		stop8();
		stop9();
		stopA();
		stopB();
		stopC();
		stopD();
		stopP();
		stopS();
	}
	
	// Later might instantiate ToneGenerator for start().
	/** Returns true if input is null, otherwise false. */
	private boolean isNull(ToneGenerator tg) {
		return tg == null;
	}
	
	/** Returns the tone type that the string character represents. If input is
	 * empty, then returns -1. */
	public static int getToneTypeFromString(String toneString) {
		if (TextUtils.isEmpty(toneString)) { return -1; }
		
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

	/** Returns a string character that represents the tone type. if input is
	 * invalid, then returns an empty string. */
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
	
	/** Releases references to prevent memory leaks. Make sure to call
	 * getInstance() again to use this class. */
//	public void destroy() {
//		if (mDtmfUtilsInstance == null) { return; }
//		
//        synchronized (mToneGeneratorLock) {
//    		if (mDtmfUtilsInstance == null) { return; }
//    		stopAll();
//			destroyHelper(mToneGenerator);
//			destroyHelper(mToneGenerator0);
//			destroyHelper(mToneGenerator1);
//			destroyHelper(mToneGenerator2);
//			destroyHelper(mToneGenerator3);
//			destroyHelper(mToneGenerator4);
//			destroyHelper(mToneGenerator5);
//			destroyHelper(mToneGenerator6);
//			destroyHelper(mToneGenerator7);
//			destroyHelper(mToneGenerator8);
//			destroyHelper(mToneGenerator9);
//			destroyHelper(mToneGeneratorA);
//			destroyHelper(mToneGeneratorB);
//			destroyHelper(mToneGeneratorC);
//			destroyHelper(mToneGeneratorD);
//			destroyHelper(mToneGeneratorP);
//			destroyHelper(mToneGeneratorS);
//			mDtmfUtilsInstance = null;
//        }
//	}
//	
//	private void destroyHelper(ToneGenerator tg) {
//		if (tg == null) { return; }
//
//        synchronized (mToneGeneratorLock) {
//    		if (tg != null) {
//				tg.release();
//				tg = null;
//    		}
//        }
//	}
	
}
