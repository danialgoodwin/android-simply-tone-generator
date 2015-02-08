package net.simplyadvanced.simplytonegenerator.mainpage.dtmf;

import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.DtmfRecordsListFragment;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.DtmfRecordsDatabase;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model.DtmfRecord;
import net.simplyadvanced.simplytonegenerator.settings.UserPrefs;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import net.simplyadvanced.util.DeviceUtils;
import net.simplyadvanced.util.OrientationUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/** The DTMF tab. */
public class FragmentDtmf extends Fragment {
	private static final String LOG_TAG = "DEBUG: FragmentDtmf";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}


	
	private Activity mActivity;
	private UserPrefs mUserPrefs;

    private DialpadFragment mDialpadFragment;
    private DtmfRecordsDatabase mRecordsDatabase;

	private Button mButtonRecord;
	private Button mButtonCancel;



    // Required empty constructor for Fragments.
    public FragmentDtmf() {}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordsDatabase = new DtmfRecordsDatabase(mActivity);
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
		/* The View representing what FragmentActivityMain is showing. */
        View viewActivity = inflater.inflate(R.layout.fragment_dtmf, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.

		mDialpadFragment = (DialpadFragment) getChildFragmentManager().findFragmentById(R.id.dialpadFragment);
//		mDialpadFragment = (DialpadFragment) getFragmentManager().findFragmentById(R.id.dialpadFragment);

    	mUserPrefs = UserPrefs.getInstance();
		
    	mButtonRecord = (Button) viewActivity.findViewById(R.id.buttonRecord);
		mButtonCancel = (Button) viewActivity.findViewById(R.id.buttonCancel);
		
		mButtonRecord.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecordSave(); }});
		mButtonCancel.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonCancel(); }});	
		
		setupViews();
		
		return viewActivity;
	}

	@Override
	public void onResume() {
		super.onResume();
		setupViews();
	}
	
	@Override
	public void onDestroy() {
		mRecordsDatabase.close();
		super.onDestroy();
	}
	
	private void setupViews() {
		mDialpadFragment.showPhoneLettersOnNumbers(mUserPrefs.isShowLettersUnderDtmfNumbers() &&
				(OrientationUtils.isPortrait(mActivity) || DeviceUtils.isTabletLarge(mActivity)));
	}
	
	

	private static final String PHRASE_RECORD = "Record";
	private static final String PHRASE_SAVE = "Save";

	private void pressButtonRecordSave() {
		if (mButtonRecord.getText().toString().equalsIgnoreCase(PHRASE_RECORD)) {
			mDialpadFragment.showInputArea(true);
			mButtonRecord.setText(PHRASE_SAVE);
			mButtonCancel.setVisibility(View.VISIBLE);
		} else { // Clicked "Save"
			mDialpadFragment.showInputArea(false);

			String tonePhrase = mDialpadFragment.getInput();
			String title = mDialpadFragment.getInputTitle();
			title = TextUtils.isEmpty(title) ? tonePhrase : title;
			if (!tonePhrase.isEmpty()) {
		        DtmfRecord record = mRecordsDatabase.createRecord(title, tonePhrase);
		        DtmfRecordsListFragment.updateListView(record); // TODOv2: Don't call this static method way because it will lead to complications in the future.
				CustomToast.show(mActivity, "Saved");
				mDialpadFragment.clearInputArea();
			}
			
			mButtonRecord.setText(PHRASE_RECORD);
			mButtonCancel.setVisibility(View.GONE);
		}
	}
	
	private void pressButtonCancel() {
		mDialpadFragment.showInputArea(false);
//		mDialpadFragment.clearInputArea(); // Nah, just in case.
		mButtonRecord.setText(PHRASE_RECORD);
		mButtonCancel.setVisibility(View.GONE);
	}
	
}
