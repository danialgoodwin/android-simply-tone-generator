package net.simplyadvanced.simplytonegenerator.settings;

import net.simplyadvanced.simplytonegenerator.ActivityCommon;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.util.OrientationHelper;
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

        ActivityCommon.onCreate(this);
	}

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCommon.onStart(this);
    }
	
}
