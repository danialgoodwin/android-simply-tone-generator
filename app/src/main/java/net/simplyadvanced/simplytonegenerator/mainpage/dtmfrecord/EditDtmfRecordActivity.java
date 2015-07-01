package net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import net.simplyadvanced.simplytonegenerator.ActivityCommon;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmf.FragmentDtmf;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model.DtmfRecord;

/** This page holds DtmfFragment, which is being used to update a single DtmfRecord.
 * Use `EditDtmfRecordActivity.navigate(...)` to open this page.
 * This file also handles the animations of opening and closing this Activity. */
public class EditDtmfRecordActivity extends FragmentActivity {
    private static final String LOGCAT_TAG = "DEBUG: EditDtmfRecordActivity";
    @SuppressWarnings("UnusedDeclaration")
    @SuppressLint("LongLogTag")
    private static void log(String message) {
        Log.d(LOGCAT_TAG, message);
    }
    @SuppressLint("LongLogTag")
    private static void loge(String message) {
        Log.d(LOGCAT_TAG, message);
    }

    /** Navigate to this page. This handles the animations.
     * @param record DtmfRecord that must hold a valid ID in order to successfully navigate to this page
     */
    public static void navigate(Activity activity, DtmfRecord record) {
        Intent intent = new Intent(activity, EditDtmfRecordActivity.class);
        intent.putExtra(DtmfRecord.ID, record.getId());
        intent.putExtra(DtmfRecord.TITLE, record.getTitle());
        intent.putExtra(DtmfRecord.TONE, record.getTone());
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dtmf_record);
        ActivityCommon.onCreate(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                long id = intent.getLongExtra(DtmfRecord.ID, -1);
                if (id != -1) {
                    String title = intent.getStringExtra(DtmfRecord.TITLE);
                    String tone = intent.getStringExtra(DtmfRecord.TONE);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, FragmentDtmf.newInstance(new DtmfRecord(id, title, tone)))
                            .commit();
                } else {
                    loge("No valid DtmfRecord id.");
                    finish();
                }
            } else {
                loge("Navigating to this Activity requires DtmfRecord to be passed in Intent.");
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCommon.onStart(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

}
