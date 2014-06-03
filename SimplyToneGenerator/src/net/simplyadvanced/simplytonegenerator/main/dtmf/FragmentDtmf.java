package net.simplyadvanced.simplytonegenerator.main.dtmf;

import net.simplyadvanced.simplytonegenerator.HelperCommon;
import net.simplyadvanced.simplytonegenerator.HelperPrefs;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import net.simplyadvanced.utils.DeviceUtils;
import net.simplyadvanced.utils.OrientationUtils;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/** The DTMF tab. */
public class FragmentDtmf extends Fragment {
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

    /** A singleton instance of UserPrefs. */
	private static UserPrefs mUserPrefs;
	
	/** The View representing what FragmentActivityMain is showing. */
	private View mViewActivity;
	
	private DialpadFragment mDialpadFragment;
	
	private Button buttonRecord1;
	private Button buttonRecord2;
	private Button buttonRecord3;
	private Button buttonRecord4;
	
	

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

		mDialpadFragment = (DialpadFragment) getFragmentManager().findFragmentById(R.id.dialpadFragment);
		
    	mHelperPrefs = HelperPrefs.getInstance(mActivity);

    	mUserPrefs = UserPrefs.getInstance();
		
		setupViews(); // First.
		setupOnClickListeners(); // After setupViews();
		
		return mViewActivity;
	}
	
	private void setupViews() {
		buttonRecord1 = (Button) mViewActivity.findViewById(R.id.buttonRecord1);
		buttonRecord2 = (Button) mViewActivity.findViewById(R.id.buttonRecord2);
		buttonRecord3 = (Button) mViewActivity.findViewById(R.id.buttonRecord3);
		buttonRecord4 = (Button) mViewActivity.findViewById(R.id.buttonRecord4);
	}

	private void setupOnClickListeners() {
		
		mDialpadFragment.showPhoneLettersOnNumbers(mUserPrefs.isShowLettersUnderDtmfNumbers() &&
				(OrientationUtils.isPortrait(mActivity) || DeviceUtils.isTabletLarge(mActivity)));
		
		buttonRecord1.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord1(); }});
		buttonRecord2.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord2(); }});
		buttonRecord3.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord3(); }});
		buttonRecord4.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecord4(); }});

		buttonRecord1.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord1(); return false; }});
		buttonRecord2.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord2(); return false; }});
		buttonRecord3.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord3(); return false; }});
		buttonRecord4.setOnLongClickListener(new OnLongClickListener() { @Override public boolean onLongClick(View v) { longPressButtonRecord4(); return false; }});
	}
	
	

	/*************/
	/* Lifecycle */
	/*************/

	@Override
	public void onResume() {
		log("onResume()");
		super.onResume();
		setupOnClickListeners();
	}

	@Override
	public void onStart() {
		log("onStart()");
		super.onStart();
		buttonRecord1.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_1, PHRASE_RECORD_1));
		buttonRecord2.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_2, PHRASE_RECORD_2));
		buttonRecord3.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_3, PHRASE_RECORD_3));
		buttonRecord4.setText(mHelperPrefs.getSharedPreference(TAG_PREF + PHRASE_RECORD_4, PHRASE_RECORD_4));
	}
	
	
	
	/*************/
	/* Recording */
	/*************/

	private static final String PHRASE_RECORD_1 = "Record 1";
	private static final String PHRASE_RECORD_2 = "Record 2";
	private static final String PHRASE_RECORD_3 = "Record 3";
	private static final String PHRASE_RECORD_4 = "Record 4";
	private static final String PHRASE_SAVE = "Save";
	private static final String PHRASE_SAVED = "Saved";
//	private static final String PHRASE_USE_TONES_FOR_INPUT = "Use tones for input";
	
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
			mDialpadFragment.showInputArea(true);
//			CustomToast.show(mActivity, PHRASE_USE_TONES_FOR_INPUT);
		} else if (s.equalsIgnoreCase(PHRASE_SAVE)) {
			// Save whatever is in editTextRecord for playback later.
			String recordText = mDialpadFragment.getInput();
			if (recordText.equalsIgnoreCase("")) {
				recordText = phraseRecord;
			} else {
				CustomToast.show(mActivity, PHRASE_SAVED);
			}
			buttonRecord.setText(recordText);
			mHelperPrefs.saveSharedPreference(TAG_PREF + phraseRecord, recordText);
			mDialpadFragment.showInputArea(false);
			mDialpadFragment.clearInputArea();
		} else if (s.equalsIgnoreCase("")) {
			buttonRecord.setText(phraseRecord);
		} else { // If a record is already stored.
			// Play record.
			int timePerTone = mUserPrefs.getTimeForDtmfTone();
			int timeBetweenTones = mUserPrefs.getTimeForBetweenDtmfTones();
			mDialpadFragment.playOrStopDtmfInput(s, timePerTone, timeBetweenTones);
		}
	}
	
	

	private void longPressButtonRecord1() {
		CustomToast.show(mActivity, "Cleared Record 1");
		buttonRecord1.setText(PHRASE_RECORD_1);
		mDialpadFragment.showInputArea(false);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_1, PHRASE_RECORD_1);
	}
	private void longPressButtonRecord2() {
		CustomToast.show(mActivity, "Cleared Record 2");
		buttonRecord2.setText(PHRASE_RECORD_2);
		mDialpadFragment.showInputArea(false);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_2, PHRASE_RECORD_2);
	}
	private void longPressButtonRecord3() {
		CustomToast.show(mActivity, "Cleared Record 3");
		buttonRecord3.setText(PHRASE_RECORD_3);
		mDialpadFragment.showInputArea(false);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_3, PHRASE_RECORD_3);
	}
	private void longPressButtonRecord4() {
		CustomToast.show(mActivity, "Cleared Record 4");
		buttonRecord4.setText(PHRASE_RECORD_4);
		mDialpadFragment.showInputArea(false);
		mHelperPrefs.saveSharedPreference(TAG_PREF + PHRASE_RECORD_4, PHRASE_RECORD_4);
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
