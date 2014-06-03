package net.simplyadvanced.simplytonegenerator.settings;

import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.utils.OrientationHelper;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/** Shows preference options for user. */
public class PrefActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);
		
		// Show preference fragment.
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		PrefFragment1 prefFragment1 = new PrefFragment1();
		fragmentTransaction.replace(android.R.id.content, prefFragment1);
//		fragmentTransaction.addToBackStack(null); // Allows user to press back button to dismiss fragment.
		fragmentTransaction.commit();
		
		OrientationHelper.setOrientationMode(PrefActivity.this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		OrientationHelper.setOrientationMode(PrefActivity.this);
	}
	
}
