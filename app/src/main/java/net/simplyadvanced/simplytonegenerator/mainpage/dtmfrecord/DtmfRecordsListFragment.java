package net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord;

import java.util.List;

import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmf.DtmfUtilsHelper;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.DtmfRecordsDatabase;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model.DtmfRecord;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DtmfRecordsListFragment extends Fragment {
	private static final String LOG_TAG = "DEBUG: DtmfRecordsListFragment";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}
	
	
	
	private Activity mActivity;
	private DtmfRecordsDatabase mRecordsDatabase;

    private static ListView mRecordsListView;


    // Required empty constructor for Fragments.
    public DtmfRecordsListFragment() {}
	
	// Called by FragmentDtmf.
	/** TODO: This is bad and need to access it a better way, like using the
	 * callback pattern. Also, when this is updated, remove static modifier for 
	 * mRecordsListView if it is no longer needed. */
	public static void updateListView(DtmfRecord record) {
        ArrayAdapter<DtmfRecord> adapter = (ArrayAdapter<DtmfRecord>) mRecordsListView.getAdapter();
        adapter.add(record);
        adapter.notifyDataSetChanged();
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
        View mainView = inflater.inflate(R.layout.fragment_dtmf_records_list, container, false);
        mRecordsListView = (ListView) mainView.findViewById(R.id.recordsListView);
        Button addFromContactsButton = (Button) mainView.findViewById(R.id.addFromContactsButton);
        Button dialFromContactsButton = (Button) mainView.findViewById(R.id.dialFromContactsButton);

		setupListView();
		
		return mainView;
	}
	
	@Override
	public void onDestroy() {
		mRecordsDatabase.close();
		super.onDestroy();
	}
	
	
	
	private void setupListView() {
		final List<DtmfRecord> values = mRecordsDatabase.getAllRecords();

        // TODOv2: Eventually, possibly use the View-Holder pattern for this.
		ArrayAdapter<DtmfRecord> adapter = new ArrayAdapter<DtmfRecord>(mActivity,
                android.R.layout.simple_list_item_2, android.R.id.text1, values) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
				TextView text2 = (TextView) view.findViewById(android.R.id.text2);

				DtmfRecord data = values.get(position);
				text1.setText(data.getTitle());
				text2.setText(data.getTone());
				return view;
			}
		};
        
        mRecordsListView.setAdapter(adapter);
        mRecordsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        ArrayAdapter<DtmfRecord> adapter = (ArrayAdapter<DtmfRecord>) mRecordsListView.getAdapter();
		        if (adapter.getCount() > 0) {
		        	DtmfRecord record = adapter.getItem(position);
		        	String tonePhrase = record.getTone();
		        	DtmfUtilsHelper.playOrStopDtmfString(tonePhrase);
		        }
			}
		});
        mRecordsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Delete tone. And, provide option to undo, edit, play.
		        ArrayAdapter<DtmfRecord> adapter = (ArrayAdapter<DtmfRecord>) mRecordsListView.getAdapter();
		        if (adapter.getCount() > 0) {
		        	DtmfRecord record = adapter.getItem(position);
		        	mRecordsDatabase.deleteRecord(record);
                    adapter.remove(record);
                    CustomToast.show(mActivity, "Record deleted");
		        }
		        adapter.notifyDataSetChanged();
				return false;
			}
		});
	}
	
}
