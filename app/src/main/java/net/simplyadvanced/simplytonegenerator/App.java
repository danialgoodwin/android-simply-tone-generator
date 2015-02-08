package net.simplyadvanced.simplytonegenerator;

import android.app.Application;
import android.content.Context;

/** The base application to initialize things once.
 * Provides a convenience method for getting Context, which should be used as little as possible. */
public class App extends Application {

	private static Context mAppContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAppContext = this;
		DtmfRecordsUpdateHelper.moveOldRecordsToNewDatabase();
	}
	
	/** Returns the application context. */
	public static Context getContext() {
		return mAppContext;
	}
	
}
