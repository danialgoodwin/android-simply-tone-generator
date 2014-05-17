package net.simplyadvanced.simplytonegenerator.ui;

import android.app.Activity;
import android.widget.Toast;

public class CustomToast {

	// Prevent instantiation.
	private CustomToast() {}
	
	public static void show(Activity activity, String message) {
		Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
	}
	
}
