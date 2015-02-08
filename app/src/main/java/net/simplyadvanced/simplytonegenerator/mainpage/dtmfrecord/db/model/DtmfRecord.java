package net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model;

/** Model to represent a single DTMF record. */
public class DtmfRecord {

    private long id;
    private String title;
    private String tone;

    public DtmfRecord(long id, String title, String tone) {
    	this.id = id;
    	this.title = title;
    	this.tone = tone;
    }
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	// This will be used by the ArrayAdapter in the ListView.
    @Override
    public String toString() {
        return title;
    }
    
}
