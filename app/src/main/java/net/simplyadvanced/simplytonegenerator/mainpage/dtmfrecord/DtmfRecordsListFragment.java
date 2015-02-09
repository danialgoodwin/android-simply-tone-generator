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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
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
//    private LinearLayout mRootView; // Originally was going to be used for custom keyboard.
    private ListView mRecordsListView;
    private static ArrayAdapter<DtmfRecord> mAdapter;

    private DtmfRecordsDatabase mRecordsDatabase;



    // Required empty constructor for Fragments.
    public DtmfRecordsListFragment() {
        log("DtmfRecordsListFragment()");
    }
	
	// Called by FragmentDtmf.
	/** TODO: This is bad and need to access it a better way, like using the
	 * callback pattern. Also, when this is updated, remove static modifier for 
	 * mRecordsListView if it is no longer needed.
     *
     * Or, just every time this is navigated to, check the database to get the most up-to-date
     * records. */
	public static void updateListView(DtmfRecord record) {
        if (mAdapter != null) {
            mAdapter.add(record);
            mAdapter.notifyDataSetChanged();
        }
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
//        mRootView = (LinearLayout) mainView.findViewById(R.id.root);
        mRecordsListView = (ListView) mainView.findViewById(R.id.recordsListView);
        TextView emptyListViewView = (TextView) mainView.findViewById(R.id.emptyList);

        mRecordsListView.setEmptyView(emptyListViewView);

        // TODOv2: Add functionality for these.
//        Button addFromContactsButton = (Button) mainView.findViewById(R.id.addFromContactsButton);
//        Button dialFromContactsButton = (Button) mainView.findViewById(R.id.dialFromContactsButton);
		
		return mainView;
	}

    @Override
    public void onResume() {
        super.onResume();
        // Make sure that list is updated from when the database may be edited by other Fragments
        // and Activities.
        setupListView();
    }

    @Override
	public void onDestroy() {
		mRecordsDatabase.close();
		super.onDestroy();
	}
	
	
	
	private void setupListView() {
		final List<DtmfRecord> values = mRecordsDatabase.getAllRecords();

        // TODOv2: Eventually, possibly use the View-Holder pattern for this.
        mAdapter = new ArrayAdapter<DtmfRecord>(mActivity,
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

        mRecordsListView.setAdapter(mAdapter);
        mRecordsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playRecord(position);
			}
		});
        mRecordsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(mActivity, view, position);
				return true;
			}
		});
	}

    /** Show a small menu at the view that allows user to choose between "Edit" and "Delete". */
    public void showPopupMenu(Activity activity, View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.inflate(R.menu.popup_dtmf_records_item);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_edit:
                        editRecord(position);
                        return true;
                    case R.id.menu_action_delete:
                        deleteRecord(position);
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /** Play the recorded DTMF tone at the position in the list/adapter. If there is already a
     * tone playing, then this will stop all tones. */
    private void playRecord(int position) {
        DtmfRecord record = mAdapter.getItem(position);
        DtmfUtilsHelper.playOrStopDtmfString(record.getTone());
    }

    /** Update a single DTMF record at the position in teh list/adapter. */
    private void editRecord(final int position) {
        // Show prompt for editing record.
//        boolean isUseDialog = false;
//        //noinspection ConstantConditions
//        if (isUseDialog) {
//            // This "dialog" way isn't used because the UX doesn't seem like it will be as good.
//            // Also, it doesn't really afford the ability to reuse the custom keyboard that is
//            // already created.
//            View inflatedView = mActivity.getLayoutInflater().inflate(R.layout.dialog_edit_dtmf_record, null);
//            EditText dtmfRecordTitleInput = (EditText) inflatedView.findViewById(R.id.dtmfRecordTitleInput);
//            EditText dtmfRecordToneInput = (EditText) inflatedView.findViewById(R.id.dtmfRecordToneInput);
//            DtmfRecord record = mAdapter.getItem(position);
//            dtmfRecordTitleInput.setText(record.getTitle());
//            dtmfRecordToneInput.setText(record.getTone());
//            new AlertDialog.Builder(mActivity)
//                    .setTitle(mActivity.getString(R.string.edit_dtmf_record_title))
////                .setMessage("")
//                    .setView(inflatedView)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            // TODOv2: Somehow re-use the already pre-built DTMF record entry UI.
//                            // TODOv2: Create updateRecord in database file.
////                            mRecordsDatabase.updateRecord();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel, null)
//                    .show();
//        } else {
            // Navigate to a page dedicated for updating record. Doing this in a new Activity gets
            // around the UX issue of having sliding tabs when editing, thus two tabs of large
            // DTMF keyboards side-by-side.
            EditDtmfRecordActivity.navigate(mActivity, mAdapter.getItem(position));
//        }
    }

    // TODOv2: Possibly add an UndoBar to undo delete.
    private void deleteRecord(int position) {
        DtmfRecord record = mAdapter.getItem(position);
        mRecordsDatabase.deleteRecord(record);
        mAdapter.remove(record);
        CustomToast.show(mActivity, mActivity.getString(R.string.record_deleted));
        mAdapter.notifyDataSetChanged();
    }

}
