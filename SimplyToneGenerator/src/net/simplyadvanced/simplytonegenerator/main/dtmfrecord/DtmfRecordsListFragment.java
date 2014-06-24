package net.simplyadvanced.simplytonegenerator.main.dtmfrecord;

import net.simplyadvanced.simplytonegenerator.HelperCommon;
import net.simplyadvanced.simplytonegenerator.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class DtmfRecordsListFragment extends Fragment {
	private static final String LOG_TAG = "DEBUG: DtmfRecordsListFragment";
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}
	
	
	
	/** The Activity holding this Fragment. */
	private Activity mActivity;
	
	/** The layout of this Fragment. */
	private View mMainView;
	
	private ListView mRecordsListView;
	
	private Button mButtonAddNewRecord;
	
	

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
		mMainView = inflater.inflate(R.layout.fragment_dtmf_records_list, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.
		
		mRecordsListView = (ListView) mMainView.findViewById(R.id.recordsListView);
		mButtonAddNewRecord = (Button) mMainView.findViewById(R.id.buttonAddNewRecord);

//		mDialpadFragment = (DialpadFragment) getFragmentManager().findFragmentById(R.id.dialpadFragment);
		
		return mMainView;
	}

}
