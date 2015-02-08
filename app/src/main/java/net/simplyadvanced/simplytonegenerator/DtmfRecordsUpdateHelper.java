package net.simplyadvanced.simplytonegenerator;

import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.DtmfRecordsDatabase;
import android.content.Context;
import android.content.SharedPreferences;

/** Move old saved SharedPreferences to the database. */
public class DtmfRecordsUpdateHelper {
	private DtmfRecordsUpdateHelper() {}
	
	
	private static final String SAMPLE_TONE = "ABCD";
	
	private static SharedPreferences settings;
	private static DtmfRecordsDatabase mRecordsDatabase;


	
	public static void moveOldRecordsToNewDatabase() {
		Context context = App.getContext();
		settings = context.getSharedPreferences("MyPrefsFile", Context.MODE_MULTI_PROCESS);
		mRecordsDatabase = new DtmfRecordsDatabase(context);

		move("prefRecord 1");
		move("prefRecord 2");
		move("prefRecord 3");
		move("prefRecord 4");
		
		mRecordsDatabase.close();
		mRecordsDatabase = null; // Just in case it helps to get garbage collected sooner.
	}
	
	/** Only put in a key that will get a String in the SharedPreferences. */
	private static void move(String key) {
		if (settings.contains(key)) {
			String value = settings.getString(key, SAMPLE_TONE);
			mRecordsDatabase.createRecord(value, value);
			settings.edit().remove(key).apply();
		}
	}

}
