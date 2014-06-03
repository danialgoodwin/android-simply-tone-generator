package net.simplyadvanced.simplytonegenerator.main.dtmf;

import net.simplyadvanced.simplytonegenerator.App;
import net.simplyadvanced.utils.AudioUtils;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.text.TextUtils;

// All the "extra" null-checks are from heavy testing where crashes occurred. The `volatile`
// modifier for the ToneGenerator variable was added more recently than the null-checks, and
// I haven't gone back to check to see if all the null-checks were still needed.
/** Handles playing tones in a different thread. To use this class, make sure
 * you call newInstance() and not any of the constructors directly. To stop
 * this thread, call stopPlayback(), then a new thread must be created to use
 * again. */
public class ThreadedTonePlaybackThread extends Thread {
	
	private volatile ToneGenerator mToneGenerator;
	private String tonePhrase = "";
	private int timePerTone = 93; // Milliseconds.
	private int timePerPause = 1000; // Milliseconds.
	private int timeBetweenTones = 40; // Milliseconds.
	private int volume = ToneGenerator.MAX_VOLUME;
	
	private static final int TONE_INVALID = -1;
	private static final int TONE_PAUSE = -2;
	

    private final static Object mToneGeneratorLock = new Object();
	
	
	
	/** Returns a new instance of this thread. */
	public static ThreadedTonePlaybackThread newInstance(String tonePhrase,
			int timePerTone, int timePerPause, int timeBetweenTones, int volume) {
		return new ThreadedTonePlaybackThread(tonePhrase, timePerTone, timePerPause,
				timeBetweenTones, volume);
	}
	
	private ThreadedTonePlaybackThread(String tonePhrase, int timePerTone,
			int timePerPause, int timeBetweenTones, int volume) {
		super();
		this.tonePhrase = tonePhrase;
		this.timePerTone = timePerTone;
		this.timePerPause = timePerPause;
		this.timeBetweenTones = timeBetweenTones;
		this.volume = volume;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			mToneGenerator = new ToneGenerator(AudioManager.STREAM_DTMF, volume);
		} catch (RuntimeException e) {
			// Error creating ToneGenerator. Try again?
			// Don't continue, don't pass GO.
			return;
		}
		
		int toneToPlay;
		int totalToneWait = timePerTone + timeBetweenTones;
		int totalPauseWait = timePerPause /*+ timeBetweenTones*/;
		

		try {
			for (int i = 0; i < tonePhrase.length(); i++) {
				if (isInterrupted()) { break; }
				
				toneToPlay = getToneTypeFromString("" + tonePhrase.charAt(i));
				if (toneToPlay == TONE_INVALID) {
					// Do nothing.
				} else if (toneToPlay == TONE_PAUSE) {
					playTone(toneToPlay, timePerPause);
					Thread.sleep(totalPauseWait);
				} else {
					playTone(toneToPlay, timePerTone);
					Thread.sleep(totalToneWait);
				}				
			}
		} catch (InterruptedException e) {
			// Stop playback.
//			e.printStackTrace();
		}
		// These aren't needed because of the better design?
//		catch (IllegalArgumentException e) {
//			// totalWaitTime was likely too large. This is now handled by
//			// limiting max value settings.
//			e.printStackTrace();
//		} catch (RuntimeException e) {
//			// Likely that destroy() was called but tone was still playing.
//			// So, don't need to play anymore. TODOv2: This should be
//			// handled better.
//			break;
//		}
		

		if (mToneGenerator != null) {
			synchronized (mToneGeneratorLock) {
				if (mToneGenerator != null) {
					mToneGenerator.release();
					mToneGenerator = null;
				}
			}
		}
	}
	
	
	/** Stops the tone and interrupts the playback. */
	public void stopPlayback() {
		if (mToneGenerator != null) {
			synchronized (mToneGeneratorLock) {
				if (mToneGenerator != null) {
					mToneGenerator.stopTone();
					mToneGenerator = null;
				}
			}
		}
		if (isAlive()) {
			interrupt();
		}
	}
	

	private void playTone(int toneType, int duration) {
		if (!AudioUtils.isRingerSilent(App.getContext())) {
			if (mToneGenerator != null) {
				mToneGenerator.startTone(toneType, duration);
			}
		}
	}

	/** Returns the tone type that the string character represents. If input is
	 * empty, then returns -1. Returns TONE_PAUSE is input is a pause character. */
	private static int getToneTypeFromString(String toneString) {
		if (TextUtils.isEmpty(toneString)) { return TONE_INVALID; }
		if (isPause(toneString)) { return TONE_PAUSE; }
		
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
		return TONE_INVALID; // Error if code reaches here.
	}

	private static boolean isPause(String character) {
		return character.equals(" ") || character.equals(",") || character.equals(";")
				 || character.equals("_");
	}
	
}
