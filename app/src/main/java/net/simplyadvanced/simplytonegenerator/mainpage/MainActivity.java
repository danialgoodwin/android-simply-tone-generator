package net.simplyadvanced.simplytonegenerator.mainpage;

import java.util.Locale;

import net.simplyadvanced.simplytonegenerator.AppConfig;
import net.simplyadvanced.simplytonegenerator.HelperCommon;
import net.simplyadvanced.simplytonegenerator.HelperPrefs;
import net.simplyadvanced.simplytonegenerator.HelperPrompts;
import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmf.FragmentDtmf;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.DtmfRecordsListFragment;
import net.simplyadvanced.simplytonegenerator.mainpage.tone.FragmentTone;
import net.simplyadvanced.simplytonegenerator.settings.PrefActivity;
import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import net.simplyadvanced.util.OrientationHelper;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ShareActionProvider;

/** This is the main Activity that holds each of the Fragment tabs. */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	private static final String LOG_TAG = "DEBUG: MainActivity";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}



    private HelperCommon mHelperCommon;
	private HelperPrefs mHelperPrefs;

    private ViewPager mViewPager;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Make sure ActionBar is available. Goes before `setContextView()`.
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.fragment_activity_main);

		OrientationHelper.setOrientationMode(this);
		
	 	mHelperCommon = HelperCommon.getInstance(this);
    	mHelperPrefs = HelperPrefs.getInstance(this);

		setupSettings();
		setupTabs();
	}

    @Override
    protected void onResume() {
        super.onResume();
        OrientationHelper.setOrientationMode(MainActivity.this);
    }

    @Override
    public void onPause() {
        mHelperPrefs.saveFragmentActivityMainSavedTabPosition(mViewPager.getCurrentItem());
        super.onPause();
    }


	
	private void setupSettings() {
		int numTimesOpenedApp = mHelperPrefs.getNumberOfTimesOpenedApp();
		int numTimesOpenedThisVersion = mHelperPrefs.getNumberOfTimesOpenedThisVersion();
		
		if (numTimesOpenedThisVersion == 0) {
			mHelperCommon.showRecentAppUpdates(this);
		} else if (numTimesOpenedApp == 10) {
			mHelperCommon.askUserToRateApp(this);
		}
//        else if (numberOfTimesOpened == 22) {
//    		if (!mHelperCommon.isProMode()) {
//    			promptUserToUpgrade();
//    		}
//    	}
		
		mHelperPrefs.saveNumberOfTimesOpenedApp(++numTimesOpenedApp);
		mHelperPrefs.saveNumberOfTimesOpenedThisVersion(++numTimesOpenedThisVersion);
	}
	
	private void setupTabs() {
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		if (actionBar == null) { // ADDED: Hopefully, this will prevent some errors. TODOv2: Use a different method if null. 2014-05-23.
			// NOTE: If this happens, make sure that the ActionBar is enabled, preferably via style xml.
			CustomToast.show(MainActivity.this, "Sorry, this device is not supported yet, please email developer with your device type.");
			finish();
			return; // Just-in-case.
		}
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the
		// primary sections of the app.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(sectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(sectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		// Set tabs to last opened position.
		mViewPager.setCurrentItem(mHelperPrefs.getFragmentActivityMainSavedTabPosition());
	}
	


	/********************/
	/* ActionBar | Tabs */
	/********************/

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			// Can't use static references.
			// More info: http://stackoverflow.com/a/24002384/887894
			// More info: http://stackoverflow.com/a/24002141/887894

				case 0: return new DtmfRecordsListFragment();
				case 1: return new FragmentDtmf();
				case 2: return new FragmentTone();
				default: return new FragmentDtmf();
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.ENGLISH; // ADDED: For hopefully less errors. 2014-05-23
//			Locale l = Locale.getDefault();
			switch (position) {
				case 0: return getString(R.string.title_section0).toUpperCase(l);
				case 1: return getString(R.string.title_section1).toUpperCase(l);
				case 2: return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}
	
	
	
	/**************/
	/* Menu Stuff */
	/**************/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fragment_activity_main, menu);
	    MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) item.getActionProvider();
	    shareActionProvider.setShareIntent(getDefaultShareIntent());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_rate_app:
                mHelperCommon.launchAppInGooglePlay(this);
                return true;
            case R.id.menu_about:
                HelperPrompts.showPromptAbout(this);
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, PrefActivity.class));
                return true;
            case R.id.menu_donate:
                HelperCommon.openDonatePage(this);
                return true;
            case R.id.menu_help:
                mHelperCommon.showMenuHelpPrompt(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
	}

	private static Intent getDefaultShareIntent() {
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

	    // To share an image.
	    // For a file in shared storage. For data in private storage, use a ContentProvider..
//	    shareIntent.setType("image/*");
//	    Uri uri = Uri.fromFile(getFileStreamPath(pathToImage));
//	    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

	    // To share text.
	    shareIntent.setType("text/plain");
	    // Add data to the intent, the receiving app will decide what to do with it.
//	    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Weather Or Not Notifications");
	    shareIntent.putExtra(Intent.EXTRA_TEXT, "The best DTMF tone generator app! Looks and works great! Save and play any tone, check it out: http://goo.gl/81npHn"); // Twitter-able

	    return shareIntent;
	} 
	
}
