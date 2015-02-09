package net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model;

/** Model to represent a single DTMF record. */
public class DtmfRecord {

    // Constants used to pass records between activities. May have other uses in future.
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TONE = "tone";

    private long id;
    private String title;
    private String tone;

    public DtmfRecord(long id, String title, String tone) {
    	this.id = id;
    	this.title = title;
    	this.tone = tone;
    }

    /** Returns the unique value for storage in the database. */
    public long getId() {
		return id;
	}

	public DtmfRecord setId(long id) {
		this.id = id;
        return this;
	}

    /** Returns the label for this record, can be any string. */
	public String getTitle() {
		return title;
	}

	public DtmfRecord setTitle(String title) {
		this.title = title;
        return this;
	}

    /** Returns the DTMF tones that were recorded. */
	public String getTone() {
		return tone;
	}

	public DtmfRecord setTone(String tone) {
		this.tone = tone;
        return this;
	}

	// This will be used by the ArrayAdapter in the ListView.
    @Override
    public String toString() {
        return title;
    }
    
}
