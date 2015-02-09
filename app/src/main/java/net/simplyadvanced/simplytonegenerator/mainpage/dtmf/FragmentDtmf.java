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


	private static final String IS_EDIT_ONE_RECORD_ONLY = "is_edit_one_record_only";

	private Activity mActivity;
	private UserPrefs mUserPrefs;

    private DialpadFragment mDialpadFragment;
    private DtmfRecordsDatabase mRecordsDatabase;

	private Button mButtonRecord;
	private Button mButtonCancel;

    /** The one DtmfRecord that may be passed in as an argument and pre-filled in the inputs. */
    private DtmfRecord mDtmfRecordFromArgs;

    /** Whether or not this Fragment instance should only allow one record to be pre-filled
     * in input fields and updated just once. */
    private boolean mIsEditOneRecordOnly = false;


    // Required empty constructor for Fragments.
    public FragmentDtmf() {}

    /** Create a new Fragment with a DTMF record that should be edited and saved/cancelled. */
    public static FragmentDtmf newInstance(DtmfRecord record) {
        FragmentDtmf fragment = new FragmentDtmf();
        Bundle args = new Bundle();
        args.putBoolean(IS_EDIT_ONE_RECORD_ONLY, true);
        args.putLong(DtmfRecord.ID, record.getId());
        args.putString(DtmfRecord.TITLE, record.getTitle());
        args.putString(DtmfRecord.TONE, record.getTone());
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordsDatabase = new DtmfRecordsDatabase(mActivity);

        Bundle args = getArguments();
        if (args != null) {
            mIsEditOneRecordOnly = args.getBoolean(IS_EDIT_ONE_RECORD_ONLY);
            long id = args.getLong(DtmfRecord.ID);
            String title = args.getString(DtmfRecord.TITLE);
            String tone = args.getString(DtmfRecord.TONE);
            mDtmfRecordFromArgs = new DtmfRecord(id, title, tone);
        }
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
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.
        View viewActivity = inflater.inflate(R.layout.fragment_dtmf, container, false);
		mDialpadFragment = (DialpadFragment) getChildFragmentManager().findFragmentById(R.id.dialpadFragment);
        mButtonRecord = (Button) viewActivity.findViewById(R.id.buttonRecord);
        mButtonCancel = (Button) viewActivity.findViewById(R.id.buttonCancel);

    	mUserPrefs = UserPrefs.getInstance();

		mButtonRecord.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonRecordSave(); }});
		mButtonCancel.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { pressButtonCancel(); }});	
		
		return viewActivity;
	}

	@Override
	public void onResume() {
		super.onResume();
        if (mDtmfRecordFromArgs != null) {
            mDialpadFragment.showInputArea(mDtmfRecordFromArgs, true);
            mButtonRecord.setText(PHRASE_SAVE);
            mButtonCancel.setVisibility(View.VISIBLE);
        }
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

			String tonePhrase = mDialpadFragment.getToneInput();
			String title = mDialpadFragment.getTitleInput();
			title = TextUtils.isEmpty(title) ? tonePhrase : title;
            if (mDtmfRecordFromArgs != null) {
                mRecordsDatabase.updateRecord(mDtmfRecordFromArgs.setTitle(title).setTone(tonePhrase));
                DtmfRecordsListFragment.updateListView(mDtmfRecordFromArgs); // TODOv2: Don't call this static method way because it will lead to complications in the future. Create callback instead.
                CustomToast.show(mActivity, "Saved");

                if (mIsEditOneRecordOnly) {
                    mActivity.finish();
                    return; // Prevents button text from changing while navigating away.
                }
            } else if (!tonePhrase.isEmpty()) {
		        DtmfRecord record = mRecordsDatabase.insertRecord(title, tonePhrase);
		        DtmfRecordsListFragment.updateListView(record); // TODOv2: Don't call this static method way because it will lead to complications in the future. Create callback instead.
				CustomToast.show(mActivity, "Saved");
				mDialpadFragment.clearInputArea();

                if (mIsEditOneRecordOnly) {
                    mActivity.finish();
                    return; // Prevents button text from changing while navigating away.
                }
			}
			
			mButtonRecord.setText(PHRASE_RECORD);
			mButtonCancel.setVisibility(View.GONE);
		}
	}
	
	private void pressButtonCancel() {
        if (mIsEditOneRecordOnly) {
            mActivity.finish();
        } else {
            mDialpadFragment.showInputArea(false);
//	        mDialpadFragment.clearInputArea(); // Nah, just in case user wants to edit same values again.
            mButtonRecord.setText(PHRASE_RECORD);
            mButtonCancel.setVisibility(View.GONE);
        }
	}
	
}
