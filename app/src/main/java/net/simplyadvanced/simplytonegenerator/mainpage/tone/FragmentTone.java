package net.simplyadvanced.simplytonegenerator.mainpage.tone;

import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.HelperPrefs;
import net.simplyadvanced.simplytonegenerator.R;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/** The Tone tab. */
public class FragmentTone extends Fragment {
	private static final String LOG_TAG = "DEBUG: FragmentTone";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}

	private static final int DEFAULT_FREQ = 31415;
	private static final int DEFAULT_MAX_FREQ = 100000;
	private static final int DEFAULT_MIN_FREQ = 0;

	private static final String TAG_PREF_TONE_FREQ = "prefToneFreq";
	private static final String TAG_PREF_TONE_FREQ_MAX = "prefToneFreqMax";
	private static final String TAG_PREF_TONE_FREQ_MIN = "prefToneFreqMin";
	
	/** The Activity Context for FragmentActivityMain. */
	private static Activity mActivity;

    private AudioTrack mAudioTrack;
	
    /** A singleton instance of HelperPrefs. */
	private static HelperPrefs mHelperPrefs;
    
	/** The View representing what FragmentActivityMain is showing. */
	private View mViewActivity;
	
	private SeekBar seekBarToneFreq;
	private EditText editTextToneFreq, editTextToneFreqMax, editTextToneFreqMin;
	private Button buttonA, buttonB;




    // Required empty constructor for Fragments.
    public FragmentTone() {}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		mViewActivity = inflater.inflate(R.layout.fragment_tone, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.

    	mHelperPrefs = HelperPrefs.getInstance(mActivity);
    	
		setupViews();
		setupOnClickListeners(); // After setupViews();
		
		return mViewActivity;
	}

	/** Defines and links code variables to the XML/layout UI. */
	private void setupViews() {
		seekBarToneFreq = (SeekBar) mViewActivity.findViewById(R.id.seekBarToneFreq);
		editTextToneFreq = (EditText) mViewActivity.findViewById(R.id.editTextToneFreq);
		editTextToneFreqMax = (EditText) mViewActivity.findViewById(R.id.editTextToneFreqMax);
		editTextToneFreqMin = (EditText) mViewActivity.findViewById(R.id.editTextToneFreqMin);
		buttonA = (Button) mViewActivity.findViewById(R.id.buttonA);
		buttonB = (Button) mViewActivity.findViewById(R.id.buttonB);
	}

	/** Defines actions for the UI widgets. */
	private void setupOnClickListeners() {
		
		seekBarToneFreq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			// Ensures that seekBarToneFreq is properly set after done editing EditText's.
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				validateAllInput();
				
				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				log("seekBarToneFreq.onStartTrackingTouch()");
				
				seekBarToneFreq.setMax(maxFreq - minFreq);
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				validateAllInput();
				
				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				log("seekBarToneFreq.onProgressChanged()");

//				seekBarToneFreq.setMax(maxFreq - minFreq);
//				
//				int freqShownToUser = minFreq + progress;
//				if (freqShownToUser > maxFreq) { freqShownToUser = maxFreq;}
//				editTextToneFreq.setText(String.valueOf(freqShownToUser));
				
				editTextToneFreq.setText(String.valueOf(minFreq + progress));
				
			}
		});

		editTextToneFreq.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				validateAllInput();

				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				log("editTextToneFreq.onEditorAction()");
				
				if (freq < minFreq) { editTextToneFreq.setText(editTextToneFreqMin.getText().toString()); }
				if (freq > maxFreq) { editTextToneFreq.setText(editTextToneFreqMax.getText().toString()); }

//				final int freqShownToUser = minFreq + freq;
				seekBarToneFreq.setProgress(freq - minFreq);
				return false;
			}
		});
		
		editTextToneFreqMin.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				validateAllInput();

				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				log("editTextToneFreqMin.onEditorAction()");

				seekBarToneFreq.setMax(maxFreq - minFreq);
				
				if (minFreq > maxFreq) {
					editTextToneFreqMin.setText(editTextToneFreqMax.getText().toString());
				}
				if (minFreq > freq) {
					editTextToneFreq.setText(editTextToneFreqMin.getText().toString());
					freq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				}

				seekBarToneFreq.setProgress(freq - minFreq);
				return false;
			}
		});
		
		editTextToneFreqMax.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				validateAllInput();

				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				log("editTextToneFreqMax.onEditorAction()");
				
				seekBarToneFreq.setMax(maxFreq - minFreq);

				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
				if (maxFreq < minFreq) {
					editTextToneFreqMax.setText("" + minFreq);
				}
				if (maxFreq < freq) {
					editTextToneFreq.setText(editTextToneFreqMax.getText().toString());
					freq = Integer.valueOf(editTextToneFreqMax.getText().toString());
				}

				seekBarToneFreq.setProgress(freq - minFreq);
				return false;
			}
		});

		/* onFocus lost */
		
		editTextToneFreq.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					validateAllInput();
					final int freq = Integer.valueOf(editTextToneFreq.getText().toString());
					final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
					final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
					log("editTextToneFreq.onFocusChange()");
					setCorrectSeekBarPosition(minFreq, freq, maxFreq);
				}
			}
		});
		
		editTextToneFreqMin.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					validateAllInput();
					final int freq = Integer.valueOf(editTextToneFreq.getText().toString());
					final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
					final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
					log("editTextToneFreqMin.onFocusChange()");
					setCorrectSeekBarPosition(minFreq, freq, maxFreq);
				}
			}
		});

		editTextToneFreqMax.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					validateAllInput();
					final int freq = Integer.valueOf(editTextToneFreq.getText().toString());
					final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
					final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
					log("editTextToneFreqMax.onFocusChange()");
					setCorrectSeekBarPosition(minFreq, freq, maxFreq);
				}
			}
		});
		
		/* Start/Stop buttons */
		
		buttonA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAudioTrack != null) {
					mAudioTrack.flush();
//					mAudioTrack.stop(); // When looping, gives error: IllegalStateException: stop() called on uninitialized AudioTrack.
					mAudioTrack.release();
				}
				
				// Use a new tread as this can take a while
				final Thread thread = new Thread(new Runnable() {
					public void run() {
						genTone();
						handler.post(new Runnable() {
							public void run() {
								playSound();
							}
						});
					}
				});
				thread.start();
			}
		});

		buttonB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAudioTrack != null) {
					mAudioTrack.flush();
//					mAudioTrack.stop(); // When looping, gives error: IllegalStateException: stop() called on uninitialized AudioTrack.
					mAudioTrack.release();
				}
			}
		});
	}
	

	/** Defines actions for the UI widgets. */ // Saved just in case the above doesn't work.
//	private void setupOnClickListeners() {
//		
//		seekBarToneFreq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) { }
//			
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) { }
//			
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
//				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
//				int freqShownToUser = minFreq + progress;
//				if (freqShownToUser > maxFreq) { freqShownToUser = maxFreq;}
//				editTextToneFreq.setText(String.valueOf(freqShownToUser));
//			}
//		});
//
//		editTextToneFreq.setOnEditorActionListener(new OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (v.getText().toString().equalsIgnoreCase("")) { v.setText("0"); }
//
//				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
//				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
//				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
//				if (freq < minFreq) { editTextToneFreq.setText(editTextToneFreqMin.getText().toString()); }
//				if (freq > maxFreq) { editTextToneFreq.setText(editTextToneFreqMax.getText().toString()); }
//
////				final int freqShownToUser = minFreq + freq;
//				seekBarToneFreq.setProgress(freq);
//				return false;
//			}
//		});
//		
//		editTextToneFreqMin.setOnEditorActionListener(new OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (v.getText().toString().equalsIgnoreCase("")) { v.setText("0"); }
//
//				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
//				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
//				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
//				if (minFreq > maxFreq) { editTextToneFreqMin.setText(editTextToneFreqMax.getText().toString()); }
//				if (minFreq > freq) { editTextToneFreq.setText(editTextToneFreqMin.getText().toString()); }
//				
//				return false;
//			}
//		});
//		
//		editTextToneFreqMax.setOnEditorActionListener(new OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (v.getText().toString().equalsIgnoreCase("")) { v.setText("0"); }
//				seekBarToneFreq.setMax(Integer.valueOf(v.getText().toString()));
//
//				int freq = Integer.valueOf(editTextToneFreq.getText().toString());
//				final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
//				final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
////				if (maxFreq < minFreq) { editTextToneFreqMax.setText(editTextToneFreqMin.getText().toString()); }
//				if (maxFreq < minFreq) {
//					editTextToneFreqMax.setText("" + minFreq);
//					editTextToneFreq.setText("" + minFreq);
//				} else if (maxFreq < freq) { editTextToneFreq.setText(editTextToneFreqMax.getText().toString()); }
//				
//				return false;
//			}
//		});
//		
//		buttonA.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (mAudioTrack != null) {
//					mAudioTrack.flush();
////					mAudioTrack.stop(); // When looping, gives error: IllegalStateException: stop() called on uninitialized AudioTrack.
//					mAudioTrack.release();
//				}
//				
//				// Use a new tread as this can take a while
//				final Thread thread = new Thread(new Runnable() {
//					public void run() {
//						genTone();
//						handler.post(new Runnable() {
//							public void run() {
//								playSound();
//							}
//						});
//					}
//				});
//				thread.start();
//			}
//		});
//
//		buttonB.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (mAudioTrack != null) {
//					mAudioTrack.flush();
////					mAudioTrack.stop(); // When looping, gives error: IllegalStateException: stop() called on uninitialized AudioTrack.
//					mAudioTrack.release();
//				}
//			}
//		});
//	}
	
	

	/*************/
	/* Lifecycle */
	/*************/
	
	@Override
	public void onStart() {
		final int freq = mHelperPrefs.getSharedPreference(TAG_PREF_TONE_FREQ, DEFAULT_FREQ);
		final int maxFreq = mHelperPrefs.getSharedPreference(TAG_PREF_TONE_FREQ_MAX, DEFAULT_MAX_FREQ);
		final int minFreq = mHelperPrefs.getSharedPreference(TAG_PREF_TONE_FREQ_MIN, DEFAULT_MIN_FREQ);
		editTextToneFreq.setText(String.valueOf(freq));
		editTextToneFreqMax.setText(String.valueOf(maxFreq));
		editTextToneFreqMin.setText(String.valueOf(minFreq));
		seekBarToneFreq.setProgress(freq - minFreq);
		seekBarToneFreq.setMax(maxFreq - minFreq);
		super.onStart();
	}

	@Override
	public void onStop() {
		validateAllInput();
		final int freq = Integer.valueOf(editTextToneFreq.getText().toString());
		final int maxFreq = Integer.valueOf(editTextToneFreqMax.getText().toString());
		final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
		mHelperPrefs.saveSharedPreference(TAG_PREF_TONE_FREQ, freq);
		mHelperPrefs.saveSharedPreference(TAG_PREF_TONE_FREQ_MAX, maxFreq);
		mHelperPrefs.saveSharedPreference(TAG_PREF_TONE_FREQ_MIN, minFreq);
		super.onStop();
	}

	
	
	/********************/
	/* Input Validation */
	/********************/
	
	private void validateAllInput() {
		if (editTextToneFreqMin.getText().toString().trim().equalsIgnoreCase("")) { editTextToneFreqMin.setText("0"); }
		if (editTextToneFreqMax.getText().toString().trim().equalsIgnoreCase("")) { editTextToneFreqMax.setText("0"); }
		if (editTextToneFreq.getText().toString().trim().equalsIgnoreCase(""))    { editTextToneFreq.setText("0"); }

		normalizeInput(editTextToneFreqMin);
		normalizeInput(editTextToneFreqMax);
		normalizeInput(editTextToneFreq);
	}
	
	private static void normalizeInput(EditText editText) {
		if (editText == null) { return; }
		double value = Double.valueOf(editText.getText().toString());
		if (value > Integer.MAX_VALUE) {
			editText.setText("" + Integer.MAX_VALUE);
		} else if (value < Integer.MIN_VALUE) {
			editText.setText("" + Integer.MIN_VALUE);
		}
	}
	
	private void setCorrectSeekBarPosition(final int min, final int progress, final int max) {
		seekBarToneFreq.setMax(max - min);
		seekBarToneFreq.setProgress(progress - min);
	}


	/*****************/
	/* Functionality */
	/*****************/

    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private final int duration = 1; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone = 440; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];

    private Handler handler = new Handler();

	private void genTone() {
		validateAllInput();
		final int minFreq = Integer.valueOf(editTextToneFreqMin.getText().toString());
		final int freq = Integer.valueOf(editTextToneFreq.getText().toString());
		
		// Need to add minFreq because it is subtracted from progress when showing it to user.
//		freqOfTone = seekBarToneFreq.getProgress() + minFreq;
		freqOfTone = freq;
		
		// fill out the array
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : sample) {
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767));
			// in 16 bit wav PCM, first byte is the low order byte
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}

	private void playSound() {
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, numSamples,
				AudioTrack.MODE_STATIC);
		mAudioTrack.write(generatedSnd, 0, generatedSnd.length);
		mAudioTrack.setLoopPoints(0, generatedSnd.length / 4, -1);
		mAudioTrack.play();
	}
	
}
