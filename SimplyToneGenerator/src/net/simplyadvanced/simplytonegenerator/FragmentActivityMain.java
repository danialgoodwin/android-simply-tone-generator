package net.simplyadvanced.simplytonegenerator;

import java.util.Locale;

import net.simplyadvanced.simplytonegenerator.ui.CustomToast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

/** This is the main Activity that holds each of the Fragment tabs. */
public class FragmentActivityMain extends Activity implements ActionBar.TabListener {
	private static final String LOG_TAG = "DEBUG: FragmentActivityMain";
	private static final boolean IS_DEBUG = true;
	@SuppressWarnings("unused")
	private static void log(final String message) {
		if (HelperCommon.IS_DEBUG_MODE && IS_DEBUG) {
			Log.d(LOG_TAG, message);
		}
	}

	/* Fragments called here so that they are only initialized once. */
	private static FragmentDtmf mFragmentDtmf = new FragmentDtmf();
	private static FragmentTone mFragmentTone = new FragmentTone();

	
	
	/**************/
	/* My Classes */
	/**************/

    /** A singleton instance of HelperPrefs. */
	private static HelperPrefs mHelperPrefs;

	/** A singleton instance of HelperCommon. */
	private static HelperCommon mHelperCommon;
	 
	
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private static SectionsPagerAdapter mSectionsPagerAdapter;

	/** The {@link ViewPager} that will host the section contents. */
	private static ViewPager mViewPager;
	

	
	/******************/
	/* Initialization */
	/******************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// The Action Bar is a window feature. The feature must be requested
	    // before setting a content view. Normally this is set automatically
	    // by your Activity's theme in your manifest. The provided system
	    // theme Theme.WithActionBar enables this for you. Use it as you would
	    // use Theme.NoTitleBar. You can add an Action Bar to your own themes
	    // by adding the element <item name="android:windowActionBar">true</item>
	    // to your style definition.
//	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Goes before setContextView(). // Set in style theme now.
	    
		setContentView(R.layout.fragment_activity_main);

	 	mHelperCommon = HelperCommon.getInstance(this);
    	mHelperPrefs = HelperPrefs.getInstance(this);
    	
		setupSettings();
		setupTabs();
	}
	
	private void setupSettings() {
		int numTimesOpenedApp = mHelperPrefs.getNumberOfTimesOpenedApp();
		int numTimesOpenedThisVersion = mHelperPrefs.getNumberOfTimesOpenedThisVersion();
		
		if (numTimesOpenedThisVersion == 0) {
			mHelperCommon.showRecentAppUpdates(this);
		} else if (numTimesOpenedApp == 7) {
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
			// NOTE: If this happens, make sure that the ActionBar is enabled, preferrably via style xml.
			CustomToast.show(FragmentActivityMain.this, "Sorry, this device is not supported yet, please email developer with your device type.");
			finish();
			return; // Just-in-case.
		}
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

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
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		// Set tabs to last opened position.
		mViewPager.setCurrentItem(mHelperPrefs.getFragmentActivityMainSavedTabPosition());
	}
	
	

	/*************/
	/* Lifecycle */
	/*************/

	@Override
	public void onPause() {
		mHelperPrefs.saveFragmentActivityMainSavedTabPosition(mViewPager.getCurrentItem());
		super.onPause();
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
			// getItem is called to instantiate the fragment for the given page.

			switch (position) {
			case 0: // Dtmf
				return mFragmentDtmf;
			case 1: // Tone
				return mFragmentTone;
			case 2: // Maptacular // If Google Maps is available. If not, then use a different map? TODOv2.
			default:
				return mFragmentDtmf;
			}

//			// Return a DummySectionFragment (defined as a static inner class
//			// below) with the page number as its lone argument.
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.ENGLISH; // ADDED: For hopefully less errors. 2014-05-23
//			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	
	
	
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_fragment_activity_main_dummy, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	
	
	/**************/
	/* Menu Stuff */
	/**************/

	@SuppressWarnings("unused")
	private Menu mMenu;

    private ShareActionProvider mShareActionProvider;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    mMenu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fragment_activity_main, menu);

	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();

	    // Set the default share intent
	    mShareActionProvider.setShareIntent(getDefaultShareIntent());
		
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    mMenu = menu;
//		updateMenu(); // This is where settings options should be hidden and shown.
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
        	log("menu_rate_app");
        	mHelperCommon.launchAppInGooglePlay(this);
            return true;
        case R.id.menu_about:
        	log("menu_about");
        	HelperPrompts.showPromptAbout(this);
            return true;
        case R.id.menu_settings:
        	log("menu_settings");
        	startActivity(new Intent(this, ActivityPreferences.class));
            return true;
        case R.id.menu_help:
        	log("menu_help");
        	mHelperCommon.showMenuHelpPrompt(this);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
//	private void hideMenuOption(final int id) {
//		if (mMenu != null) {
//			mMenu.findItem(id).setVisible(false);
//		}
//	}
//	
//	private void showMenuOption(final int id) {
//		if (mMenu != null) {
//		    mMenu.findItem(id).setVisible(true);
//		}
//	}

	private Intent getDefaultShareIntent() {
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
//	    shareIntent.putExtra(Intent.EXTRA_TEXT, "An awesome new Android-exclusive app called \"Weather Or Not Notifications\" was just released. It's the first app that you are supposed to forget about! Check it out: https://play.google.com/store/apps/details?id=net.simplyadvanced.weatherornotnotifications");
	    shareIntent.putExtra(Intent.EXTRA_TEXT, "The easiest DTMF tone generator app. Looks and works great! Save and play any tone, check it out: http://goo.gl/81npHn"); // Twitter-able
	    
	    return shareIntent;
	} 
	
}
