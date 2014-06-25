package net.simplyadvanced.simplytonegenerator.main.dtmfrecord.db;

import java.util.ArrayList;
import java.util.List;

import net.simplyadvanced.simplytonegenerator.main.dtmfrecord.db.model.DtmfRecord;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** Make sure to call `close()` when done with this. */
public class DtmfRecordsDatabase extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "dtmfrecords.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_DTMF_RECORDS = "DtmfRecords";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_TONE = "tone";
	
	private static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_TITLE, COLUMN_TONE};

	private static final int INDEX_OF_ID = 0;
	private static final int INDEX_OF_TITLE = 1;
	private static final int INDEX_OF_TONE = 2;
	
	private static final String CREATE_DATABASE =
			"create table " + TABLE_DTMF_RECORDS + "(" + 
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_TITLE + " text not null, " +
			COLUMN_TONE + " text not null" + 
			");";


	
	public DtmfRecordsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DATABASE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Currently, upgrading database from old version to new version
		// will destroy all the old data.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DTMF_RECORDS);
        onCreate(db);
	}

	public DtmfRecord createRecord(String title, String tone) {
        SQLiteDatabase db = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TONE, tone);
        
        long insertId = db.insert(TABLE_DTMF_RECORDS, null, values);
        
        Cursor cursor = db.query(TABLE_DTMF_RECORDS, ALL_COLUMNS,
        		COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        DtmfRecord newRecord = getDtmfRecord(cursor);
        cursor.close();
        return newRecord;
	}
	
	public List<DtmfRecord> getAllRecords() {
        List<DtmfRecord> records = new ArrayList<DtmfRecord>();

        Cursor cursor = getReadableDatabase().query(TABLE_DTMF_RECORDS, ALL_COLUMNS,
        		null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	DtmfRecord record = getDtmfRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
	}
	
	public void deleteRecord(DtmfRecord record) {
        long id = record.getId();
        getWritableDatabase().delete(TABLE_DTMF_RECORDS, COLUMN_ID + " = " + id, null);
	}
	
	private DtmfRecord getDtmfRecord(Cursor cursor) {
		long id = cursor.getLong(INDEX_OF_ID);
		String title = cursor.getString(INDEX_OF_TITLE);
		String tone = cursor.getString(INDEX_OF_TONE);
		DtmfRecord record = new DtmfRecord(id, title, tone);
		return record;
	}
	
}
