package net.simplyadvanced.simplytonegenerator;

import net.simplyadvanced.simplytonegenerator.dtmf.DtmfUtils;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.PorterDuff; // Eclipse doesn't give this as an option
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The app description.
 * 
 * @author Danial Goodwin
 */
public class FragmentDtmf extends Fragment implements OnTouchListener {
	private static final String LOG_TAG = "DEBUG: FragmentDtmf";
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}

	
	/** The Activity Context for FragmentActivityMain. */
	private static Activity mActivity;

    /** A singleton instance of HelperPrefs. */
	private static HelperPrefs mHelperPrefs;

    /** A singleton instance of DtmfUtils. */
	private static DtmfUtils mDtmfUtils;

    /** A singleton instance of UserPrefs. */
	private static UserPrefs mUserPrefs;
	
	/** The View representing what FragmentActivityMain is showing. */
	private View mViewActivity;

	private Button buttonRecord1;
	private Button buttonRecord2;
	private Button buttonRecord3;
	private Button buttonRecord4;
	
	private EditText editTextRecord;
	
	private Button button0, button1, button2, button3;
	private Button button4, button5, button6, button7;
	private Button button8, button9, buttonP, buttonS;
	private Button buttonA, buttonB, buttonC, buttonD;
	
	

	/******************/
	/* Initialization */
	/******************/
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
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
		mViewActivity = inflater.inflate(R.layout.fragment_dtmf, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.

    	mHelperPrefs = HelperPrefs.getInstance(mActivity);

    	mDtmfUtils = DtmfUtils.getInstance();
    	mUserPrefs = UserPrefs.getInstance(mActivity);
		
		setupViews(); // First.
		setupOnClickListeners(); // After setupViews();
		
		return mViewActivity;
	}
	
	private void setupViews() {
		buttonRecord1 = (Button) mViewActivity.findViewById(R.id.buttonRecord1);
		buttonRecord2 = (Button) mViewActivity.findViewById(R.id.buttonRecord2);
		buttonRecord3 = (Button) mViewActivity.findViewById(R.id.buttonRecord3);
		buttonRecord4 = (Button) mViewActivity.findViewById(R.id.buttonRecord4);

		editTextRecord = (EditText) mViewActivity.findViewById(R.id.editTextRecord);
		
		button0 = (Button) mViewActivity.findViewById(R.id.button0);
		button1 = (Button) mViewActivity.findViewById(R.id.button1);
		button2 = (Button) mViewActivity.findViewById(R.id.button2); // ABC
		button3 = (Button) mViewActivity.findViewById(R.id.button3); // DEF
		button4 = (Button) mViewActivity.findViewById(R.id.button4); // GHI
		button5 = (Button) mViewActivity.findViewById(R.id.button5); // JKL
		button6 = (Button) mViewActivity.findViewById(R.id.button6); // MNO
		button7 = (Button) mViewActivity.findViewById(R.id.button7); // PQRS
		button8 = (Button) mViewActivity.findViewById(R.id.button8); // TUV
		button9 = (Button) mViewActivity.findViewById(R.id.button9); // WXYZ
		buttonA = (Button) mViewActivity.findViewById(R.id.buttonA);
		buttonB = (Button) mViewActivity.findViewById(R.id.buttonB);
		buttonC = (Button) mViewActivity.findViewById(R.id.buttonC);
		buttonD = (Button) mViewActivity.findViewById(R.id.buttonD);
		buttonP = (Button) mViewActivity.findViewById(R.id.buttonP);
		buttonS = (Button) mViewActivity.findViewById(R.id.buttonS);
	}
	
	/** Returns the input with all but first two characters a little smaller. */
	private SpannableString setStyledText(String value) {
		 SpannableString ss=  new SpannableString(value);
		 ss.setSpan(new RelativeSizeSpan(0.618f), 2, value.length(), 0); // set size
//		 ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
		 return ss;
	}

	private void setupOnClickListeners() {

		if (mUserPrefs.isShowLettersUnderDtmfNumbers()) {
			button1.setText(setStyledText("1\n   ")); // So that the number lines up better with the others.
			button2.setText(setStyledText("2\nABC"));
			button3.setText(setStyledText("3\nDEF"));
			button4.setText(setStyledText("4\nGHI"));
			button5.setText(setStyledText("5\nJKL"));
			button6.setText(setStyledText("6\nMNO"));
			button7.setText(setStyledText("7\nPQRS"));
			button8.setText(setStyledText("8\nTUV"));
			button9.setText(setStyledText("9\nWXYZ"));
		} else {
			button1.setText("1");
			button2.setText("2");
			button3.setText("3");
			button4.setText("4");
			button5.setText("5");
			button6.setText("6");
			button7.setText("7");
			button8.setText("8");
			button9.setText("9");
		}
		
		buttonRecord1.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord1(); }});
		buttonRecord2.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord2(); }});
		buttonRecord3.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord3(); }});
		buttonRecord4.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord4(); }});

		buttonRecord1.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord1(); return false; }});
		buttonRecord2.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord2(); return false; }});
		buttonRecord3.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord3(); return false; }});
		buttonRecord4.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord4(); return false; }});

		boolean isContinuousTone = mHelperPrefs.getUserPreference(getString(R.string.pref_continuous_dtmf_tones), true);
		log("onCreate(), isContinuousTone: " + isContinuousTone);
		if (!isContinuousTone) {
			button0.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_0); }});
			button1.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_1); }});
			button2.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_2); }});
			button3.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_3); }});
			button4.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_4); }});
			button5.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_5); }});
			button6.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_6); }});
			button7.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_7); }});
			button8.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_8); }});
			button9.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_9); }});
			buttonA.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_A); }});
			buttonB.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_B); }});
			buttonC.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_C); }});
			buttonD.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_D); }});
			buttonP.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_P); }});
			buttonS.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonDtmfTone(ToneGenerator.TONE_DTMF_S); }});

			button0.setOnTouchListener(null);
			button1.setOnTouchListener(null);
			button2.setOnTouchListener(null);
			button3.setOnTouchListener(null);
			button4.setOnTouchListener(null);
			button5.setOnTouchListener(null);
			button6.setOnTouchListener(null);
			button7.setOnTouchListener(null);
			button8.setOnTouchListener(null);
			button9.setOnTouchListener(null);
			buttonA.setOnTouchListener(null);
			buttonB.setOnTouchListener(null);
			buttonC.setOnTouchListener(null);
			buttonD.setOnTouchListener(null);
			buttonP.setOnTouchListener(null);
			buttonS.setOnTouchListener(null);
		} else {
			button0.setOnTouchListener(this);
			button1.setOnTouchListener(this);
			button2.setOnTouchListener(this);
			button3.setOnTouchListener(this);
			button4.setOnTouchListener(this);
			button5.setOnTouchListener(this);
			button6.setOnTouchListener(this);
			button7.setOnTouchListener(this);
			button8.setOnTouchListener(this);
			button9.setOnTouchListener(this);
			buttonA.setOnTouchListener(this);
			buttonB.setOnTouchListener(this);
			buttonC.setOnTouchListener(this);
			buttonD.setOnTouchListener(this);
			buttonP.setOnTouchListener(this);
			buttonS.setOnTouchListener(this);

			button0.setOnClickListener(null);
			button1.setOnClickListener(null);
			button2.setOnClickListener(null);
			button3.setOnClickListener(null);
			button4.setOnClickListener(null);
			button5.setOnClickListener(null);
			button6.setOnClickListener(null);
			button7.setOnClickListener(null);
			button8.setOnClickListener(null);
			button9.setOnClickListener(null);
			buttonA.setOnClickListener(null);
			buttonB.setOnClickListener(null);
			buttonC.setOnClickListener(null);
			buttonD.setOnClickListener(null);
			buttonP.setOnClickListener(null);
			buttonS.setOnClickListener(null);
		}
	}
	
	

	/*************/
	/* Lifecycle */
	/*************/

	@Override
	public void onResume() {
		log("onResume()");
		setupOnClickListeners();
		super.onResume();
	}

	@Override
	public void onStart() {
		log("onStart()");
		buttonRecord1.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_1, PHRASE_RECORD_1));
		buttonRecord2.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_2, PHRASE_RECORD_2));
		buttonRecord3.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_3, PHRASE_RECORD_3));
		buttonRecord4.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_4, PHRASE_RECORD_4));
		
		super.onStart();
	}
	
	
	
	/*************/
	/* Callbacks */
	/*************/

//	private long mTimeTouchStart;
//	private long mTimeTouchEnd;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) { // From TouchListener.
//		v.setFocusable(false);
		v.onWindowFocusChanged(false);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			mTimeTouchStart = HelperTime.getCurrentTimeMillis();
			log("ACTION_DOWN");
			playIndefiniteDtmfTone(v);
//    		v.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
    		v.getBackground().setColorFilter(0xFF33B5E5, PorterDuff.Mode.MULTIPLY);
//			v.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF0055E5));
//			v.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF0099CC));
			return false;
		case MotionEvent.ACTION_CANCEL:
			log("ACTION_CANCEL");
			stopIndefiniteDtmfToneNow(v);
    		v.getBackground().clearColorFilter();
//			v.setPressed(false); // Works, clears button focus.
			return true; // Has consumed event. // Don't want to do anything else from this gesture.
//		case MotionEvent.ACTION_MOVE:
//			log("ACTION_MOVE");
//			return false;
		case MotionEvent.ACTION_UP:
//			mTimeTouchEnd = HelperTime.getCurrentTimeMillis();
			log("ACTION_UP");
			inputToneToEditText(v);
			stopIndefiniteDtmfToneNow(v);
    		v.getBackground().clearColorFilter();
//			v.onWindowFocusChanged(false);
//			v.setPressed(false); // Works, clears button focus.
			return true;
//			if (mTimeTouchEnd - mTimeTouchStart > 200) {
//				v.setPressed(false); // Works, clears button focus.
//				return true; // Don't play sound anymore.
//			} else {
//				stopIndefiniteDtmfToneSoon();
//				v.setPressed(false); // Works, clears button focus.
//				return true; // Play default length for button.
//			}
//		default:
//			log("ACTION_UNKNOWN: " + event.getAction());
//			return false;
		}
		return false;
	}



	/*****************/
	/* Create Sounds */
	/*****************/

	private void pressButtonDtmfTone(final int toneType) {
		mDtmfUtils.playSoundDtmf(toneType);
		
		inputToneToEditText(toneType);
//		if (editTextRecord.getVisibility() == View.VISIBLE) {
//			// Insert characters into editTextRecord.
//			String oldRecord = editTextRecord.getText().toString();
//			String newTone = getStringFromToneType(toneType);
//			editTextRecord.setText(oldRecord + newTone);
//		}
	}

	public void playIndefiniteDtmfTone(final View v) {
		if      (v.equals(button0)) { mDtmfUtils.start0(); }
		else if (v.equals(button1)) { mDtmfUtils.start1(); }
		else if (v.equals(button2)) { mDtmfUtils.start2(); }
		else if (v.equals(button3)) { mDtmfUtils.start3(); }
		else if (v.equals(button4)) { mDtmfUtils.start4(); }
		else if (v.equals(button5)) { mDtmfUtils.start5(); }
		else if (v.equals(button6)) { mDtmfUtils.start6(); }
		else if (v.equals(button7)) { mDtmfUtils.start7(); }
		else if (v.equals(button8)) { mDtmfUtils.start8(); }
		else if (v.equals(button9)) { mDtmfUtils.start9(); }
		else if (v.equals(buttonA)) { mDtmfUtils.startA(); }
		else if (v.equals(buttonB)) { mDtmfUtils.startB(); }
		else if (v.equals(buttonC)) { mDtmfUtils.startC(); }
		else if (v.equals(buttonD)) { mDtmfUtils.startD(); }
		else if (v.equals(buttonP)) { mDtmfUtils.startP(); }
		else if (v.equals(buttonS)) { mDtmfUtils.startS(); }
	}
	
	private void stopIndefiniteDtmfToneNow(final View v) {
		if      (v.equals(button0)) { mDtmfUtils.stop0(); }
		else if (v.equals(button1)) { mDtmfUtils.stop1(); }
		else if (v.equals(button2)) { mDtmfUtils.stop2(); }
		else if (v.equals(button3)) { mDtmfUtils.stop3(); }
		else if (v.equals(button4)) { mDtmfUtils.stop4(); }
		else if (v.equals(button5)) { mDtmfUtils.stop5(); }
		else if (v.equals(button6)) { mDtmfUtils.stop6(); }
		else if (v.equals(button7)) { mDtmfUtils.stop7(); }
		else if (v.equals(button8)) { mDtmfUtils.stop8(); }
		else if (v.equals(button9)) { mDtmfUtils.stop9(); }
		else if (v.equals(buttonA)) { mDtmfUtils.stopA(); }
		else if (v.equals(buttonB)) { mDtmfUtils.stopB(); }
		else if (v.equals(buttonC)) { mDtmfUtils.stopC(); }
		else if (v.equals(buttonD)) { mDtmfUtils.stopD(); }
		else if (v.equals(buttonP)) { mDtmfUtils.stopP(); }
		else if (v.equals(buttonS)) { mDtmfUtils.stopS(); }
	}


	// This is actually the original working version. I should use this if I can't get the more complicated example working.
	@SuppressWarnings("unused")
	private void playIndefiniteDtmfTone1(final View v) {
		mDtmfUtils.stopSoundDtmf();
		mDtmfUtils.playSoundDtmf(getToneTypeFromView(v));
	}


	// This is actually the original working version. I should use this if I can't get the more complicated example working.
	@SuppressWarnings("unused")
	private void stopIndefiniteDtmfToneNow1(final View v) {
		mDtmfUtils.stopSoundDtmf();
	}
	
	/*************/
	/* Recording */
	/*************/

	private static final String PHRASE_RECORD_1 = "Record 1";
	private static final String PHRASE_RECORD_2 = "Record 2";
	private static final String PHRASE_RECORD_3 = "Record 3";
	private static final String PHRASE_RECORD_4 = "Record 4";
	private static final String PHRASE_ = "";
	private static final String PHRASE_SAVE = "Save";
	private static final String PHRASE_SAVED = "Saved";
	private static final String PHRASE_USE_TONES_FOR_INPUT = "Use tones for input";
	
	private static final String TAG_PREF = "pref";
	
	private void pressButtonRecord1() {
		pressButtonRecordGeneral(buttonRecord1, PHRASE_RECORD_1);
	}
	
	private void pressButtonRecord2() {
		pressButtonRecordGeneral(buttonRecord2, PHRASE_RECORD_2);
	}
	
	private void pressButtonRecord3() {
		pressButtonRecordGeneral(buttonRecord3, PHRASE_RECORD_3);
	}
	
	private void pressButtonRecord4() {
		pressButtonRecordGeneral(buttonRecord4, PHRASE_RECORD_4);
	}
	
	private void pressButtonRecordGeneral(final Button buttonRecord, final String phraseRecord) {
		// Make sure two buttons both don't say "Save". Basically, click cancel.
		if (buttonRecord != buttonRecord1 && buttonRecord1.getText().toString().equalsIgnoreCase(PHRASE_SAVE)) { buttonRecord1.setText(PHRASE_RECORD_1); }
		if (buttonRecord != buttonRecord2 && buttonRecord2.getText().toString().equalsIgnoreCase(PHRASE_SAVE)) { buttonRecord2.setText(PHRASE_RECORD_2); }
		if (buttonRecord != buttonRecord3 && buttonRecord3.getText().toString().equalsIgnoreCase(PHRASE_SAVE)) { buttonRecord3.setText(PHRASE_RECORD_3); }
		if (buttonRecord != buttonRecord4 && buttonRecord4.getText().toString().equalsIgnoreCase(PHRASE_SAVE)) { buttonRecord4.setText(PHRASE_RECORD_4); }
		
		// Regular recording of tones.
		String s = buttonRecord.getText().toString();
		if (s.equalsIgnoreCase(phraseRecord)) {
			// Setup EditText to enter record.
			buttonRecord.setText(PHRASE_SAVE);
			editTextRecord.setText(PHRASE_);
			editTextRecord.setVisibility(View.VISIBLE);
			CustomToast.show(mActivity, PHRASE_USE_TONES_FOR_INPUT);
		} else if (s.equalsIgnoreCase(PHRASE_SAVE)) {
			// Save whatever is in editTextRecord for playback later.
			editTextRecord.setVisibility(View.GONE);
			String recordText = editTextRecord.getText().toString();
			if (recordText.equalsIgnoreCase(PHRASE_)) {
				recordText = phraseRecord;
			} else {
				CustomToast.show(mActivity, PHRASE_SAVED);
			}
			buttonRecord.setText(recordText);
			mHelperPrefs.saveSharedPreference(TAG_PREF + phraseRecord, recordText);
		} else if (s.equalsIgnoreCase(PHRASE_)) {
			buttonRecord.setText(phraseRecord);
		} else { // If a record is already stored.
			// Play record.
			int timePerDigit = Integer.parseInt(mHelperPrefs.getUserPreference(getString(R.string.pref_time_for_tone), ActivityPreferences.TAG_DEFAULT_TIME_FOR_TONE));
			int pause = Integer.parseInt(mHelperPrefs.getUserPreference(getString(R.string.pref_time_for_pause), ActivityPreferences.TAG_DEFAULT_TIME_FOR_PAUSE));
			mDtmfUtils.playOrStopDtmfString(s, timePerDigit, pause);
//			playDtmfString(s);
		}
	}
	

	private void longPressButtonRecord1() {
		Toast.makeText(mActivity, "Cleared Record 1", Toast.LENGTH_LONG).show();
		buttonRecord1.setText(PHRASE_RECORD_1);
		editTextRecord.setVisibility(View.GONE);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_1, PHRASE_RECORD_1);
	}
	private void longPressButtonRecord2() {
		Toast.makeText(mActivity, "Cleared Record 2", Toast.LENGTH_LONG).show();
		buttonRecord2.setText(PHRASE_RECORD_2);
		editTextRecord.setVisibility(View.GONE);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_2, PHRASE_RECORD_2);
	}
	private void longPressButtonRecord3() {
		Toast.makeText(mActivity, "Cleared Record 3", Toast.LENGTH_LONG).show();
		buttonRecord3.setText(PHRASE_RECORD_3);
		editTextRecord.setVisibility(View.GONE);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_3, PHRASE_RECORD_3);
	}
	private void longPressButtonRecord4() {
		Toast.makeText(mActivity, "Cleared Record 4", Toast.LENGTH_LONG).show();
		buttonRecord4.setText(PHRASE_RECORD_4);
		editTextRecord.setVisibility(View.GONE);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_4, PHRASE_RECORD_4);
	}

	
	
	/********************/
	/* Helper Functions */
	/********************/

	/** When editTextRecord is showing, allow buttons to input characters there, so that records can be created and saved. */
	private void inputToneToEditText(final View v) {
		inputToneToEditText(getToneTypeFromView(v));
	}
	
	
	/** When editTextRecord is showing, allow buttons to input characters there, so that records can be created and saved. */
	private void inputToneToEditText(final int toneType) {
		if (editTextRecord.getVisibility() == View.VISIBLE) {
			// Insert characters into editTextRecord.
			String oldRecord = editTextRecord.getText().toString();
			String newTone = DtmfUtils.getStringFromToneType(toneType);
			editTextRecord.setText(oldRecord + newTone);
		}
	}
	
	
	private int getToneTypeFromView(final View v) {
		if      (v.equals(button0)) { return ToneGenerator.TONE_DTMF_0; }
		else if (v.equals(button1)) { return ToneGenerator.TONE_DTMF_1; }
		else if (v.equals(button2)) { return ToneGenerator.TONE_DTMF_2; }
		else if (v.equals(button3)) { return ToneGenerator.TONE_DTMF_3; }
		else if (v.equals(button4)) { return ToneGenerator.TONE_DTMF_4; }
		else if (v.equals(button5)) { return ToneGenerator.TONE_DTMF_5; }
		else if (v.equals(button6)) { return ToneGenerator.TONE_DTMF_6; }
		else if (v.equals(button7)) { return ToneGenerator.TONE_DTMF_7; }
		else if (v.equals(button8)) { return ToneGenerator.TONE_DTMF_8; }
		else if (v.equals(button9)) { return ToneGenerator.TONE_DTMF_9; }
		else if (v.equals(buttonA)) { return ToneGenerator.TONE_DTMF_A; }
		else if (v.equals(buttonB)) { return ToneGenerator.TONE_DTMF_B; }
		else if (v.equals(buttonC)) { return ToneGenerator.TONE_DTMF_C; }
		else if (v.equals(buttonD)) { return ToneGenerator.TONE_DTMF_D; }
		else if (v.equals(buttonP)) { return ToneGenerator.TONE_DTMF_P; }
		else if (v.equals(buttonS)) { return ToneGenerator.TONE_DTMF_S; }
		return ToneGenerator.TONE_DTMF_0; // Error if code reaches here.
	}

	
	
	/**************/
	/* Menu Stuff */
	/**************/

//	private Menu mMenu;
//	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//	    mMenu = menu;
//        inflater.inflate(R.menu.activity_main, menu);
//		
//		mHasMenuLoaded = true;
//		updateMenuAndSpinner();
//		
//        super.onCreateOptionsMenu(menu, inflater);
//	}
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//	        case R.id.menu_goToGpsList:
//	        	startActivity(new Intent(mActivity, GpsListActivity.class));
//	            return true;
//            case R.id.menu_settings:
//            	startActivity(new Intent(mActivity, PreferencesActivity.class));
//                return true;
//            case R.id.menu_help:
//            	HelperCommon.showHelpPrompt(mActivity);
//                return true;
//            case R.id.menu_feedback:
//            	HelperCommon.sendEmail(mActivity);
//                return true;
//            case R.id.menu_rateApp:
//            	HelperCommon.launchAppInGooglePlay(mActivity); // Allows users to rate this app.
//            	return true;
//            case R.id.menu_donate: // NOTE: open a new WebView page instead? It could look better
//            	Intent i = new Intent(Intent.ACTION_VIEW);
//            	i.setData(Uri.parse("http://m.simplyadvanced.net/donate.html"));
//            	startActivity(i);
//            	return true;
//            case R.id.menu_upgrade:
//            	promptUserToUpgrade();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//	private void hideMenuOption(final int id) {
//		if (mMenu != null) {
//			mMenu.findItem(id).setVisible(false);
//		}
//	}
//	private void showMenuOption(final int id) {
//		if (mMenu != null) {
//		    mMenu.findItem(id).setVisible(true);
//		}
//	}
	
}
