package net.simplyadvanced.simplytonegenerator.ui;

import android.app.Activity;
import android.widget.Toast;

/** Makes it easier to later customize the Toasts. */
public class CustomToast {
	private CustomToast() {}
	
	public static void show(Activity activity, String message) {
		Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
	}
	
}
