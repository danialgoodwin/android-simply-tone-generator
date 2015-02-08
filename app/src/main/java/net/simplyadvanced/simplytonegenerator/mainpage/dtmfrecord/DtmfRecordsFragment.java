package net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord;

import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DtmfRecordsFragment extends Fragment {
	private static final String LOG_TAG = "DEBUG: DtmfRecordFragment";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}



    // Required empty constructor for Fragments.
    public DtmfRecordsFragment() {}

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
        View mainView = inflater.inflate(R.layout.fragment_dtmf_records, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.

		getFragmentManager().beginTransaction()
                .replace(R.id.root, new DtmfRecordsListFragment(), DtmfRecordsListFragment.class.getSimpleName())
                .commit();
//		getFragmentManager().beginTransaction()
//              .addFragment(R.id.root, new DtmfRecordsListFragment()).commit();

		return mainView;
	}
	
}
