package net.simplyadvanced.simplytonegenerator.main.dtmfrecord;

import java.util.List;

import net.simplyadvanced.simplytonegenerator.HelperCommon;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.main.dtmf.DtmfUtils;
import net.simplyadvanced.simplytonegenerator.main.dtmf.DtmfUtilsHelper;
import net.simplyadvanced.simplytonegenerator.main.dtmfrecord.db.DtmfRecordsDatabase;
import net.simplyadvanced.simplytonegenerator.main.dtmfrecord.db.model.DtmfRecord;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DtmfRecordsListFragment extends Fragment {
	private static final String LOG_TAG = "DEBUG: DtmfRecordsListFragment";
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}
	
	
	
	private DtmfRecordsDatabase mRecordsDatabase;
	
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
		mMainView = inflater.inflate(R.layout.fragment_dtmf_records_list, container, false);
//    	setHasOptionsMenu(true); // Uncomment this if this fragment has specific menu options.
		
		mRecordsListView = (ListView) mMainView.findViewById(R.id.recordsListView);
		mButtonAddNewRecord = (Button) mMainView.findViewById(R.id.buttonAddNewRecord);
		
		mButtonAddNewRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Open up page to add DTMF record, then return results somehow, possibly through onActivityResults().
				// Or, the called Fragment can save directly to database instead?
		        ArrayAdapter<DtmfRecord> adapter = (ArrayAdapter<DtmfRecord>) mRecordsListView.getAdapter();
		        DtmfRecord record = mRecordsDatabase.createRecord("Title", "1234567890ABCD*#");
                adapter.add(record);
		        adapter.notifyDataSetChanged();
			}
		});
		
		setupListView();
		
		return mMainView;
	}
	
	@Override
	public void onDestroy() {
		mRecordsDatabase.close();
		super.onDestroy();
	}
	
	
	
	private void setupListView() {
		final List<DtmfRecord> values = mRecordsDatabase.getAllRecords();

		// TODOv2: This can be done better.
		ArrayAdapter<DtmfRecord> adapter = new ArrayAdapter<DtmfRecord>(mActivity, android.R.layout.simple_list_item_2, android.R.id.text1, values) {
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
//        ArrayAdapter<DtmfRecord> adapter = new ArrayAdapter<DtmfRecord>(mActivity, android.R.layout.simple_list_item_1, values);
        
        mRecordsListView.setAdapter(adapter);
        mRecordsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        ArrayAdapter<DtmfRecord> adapter = (ArrayAdapter<DtmfRecord>) mRecordsListView.getAdapter();
		        if (adapter.getCount() > 0) {
		        	DtmfRecord record = (DtmfRecord) adapter.getItem(position);
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
		        	DtmfRecord record = (DtmfRecord) adapter.getItem(position);
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
