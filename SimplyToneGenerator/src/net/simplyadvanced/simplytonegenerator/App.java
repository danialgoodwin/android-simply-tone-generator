package net.simplyadvanced.simplytonegenerator;

import android.app.Application;
import android.content.Context;

/** The base Application class for Simply Tone Generator. This is the
 * very first and last thing that is run. */
public class App extends Application {

	private static Context mAppContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAppContext = this;
	}
	
	/** Returns the application context. */
	public static Context getContext() {
		return mAppContext;
	}
	
}
