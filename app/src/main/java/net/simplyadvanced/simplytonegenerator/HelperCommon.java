package net.simplyadvanced.simplytonegenerator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/** Provides convenience function common to all apps. */
public class HelperCommon {
	private static final String LOG_TAG = "DEBUG: HelperCommon";
	private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
	private static void log(String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (IS_SHOW_DEBUG_LOGCAT && AppConfig.isShowDebugLogcat()) {
			Log.d(LOG_TAG, message);
		}
	}



	public static final String RECENT_UPDATES_PROMPT_TITLE = "Recent Updates - v" + AppVersion.getName();
	public static final String RECENT_UPDATES_PROMPT_MESSAGE = "" +
//            "Version 1.4\n" +
//            "=========\n" +
//            "\n" +
//            "\n" +
//            "\n" +
			"Version 1.8\n" +
			"=========\n" +
			"- New setting: Ability to keep screen on\n" +
			"\n" +
			"\n" +
			"Version 1.7\n" +
			"=========\n" +
			"- New feature: Ability to import entries from contacts list!\n" +
			"\n" +
			"\n" +
			"Version 1.6\n" +
			"=========\n" +
			"- New Feature: Ability to record unlimited tones!\n" +
			"- New Feature: Ability to create titles for saved tones!\n" +
			"- New Feature: Ability to edit saved records\n" +
            "- Fix: Backspace button is now easier to see\n" +
            "\n" +
            "Next planned update:\n" +
            "- Integration with contacts\n" +
            "\n" +
            "If you find this free app useful, then please consider donating for the time and effort put in to create this app. Thank you.\n" +
            "\n" +
            "\n" +
			"\n" +
			"Version 1.4\n" +
			"=========\n" +
			"1. Updated input area!\n" +
			"2. Full edit capabilities: copy, paste, select, and cut\n" +
			"3. Backspace feature\n" +
			"4. Pause character input\n" +
			"5. Two more settings options\n" +
			"6. Ability to lock portrait orientation\n" +
			"\n" +
			"\n" +
			"Version 1.3.4\n" +
			"==========\n" +
			"1. Bug fixes for specific devices.\n" +
			"    - Special thanks to Voytek for the bug fixes! ;)\n" +
			"2. Slight performance increase.\n" +
			"\n" +
			"\n" +
			"Version 1.3\n" +
			"=========\n" +
			"1. [New Feature] Alphabet on DTMF numbers.\n" +
			"2. [New Feature] Settings option to show those letters.\n" +
			"    - Special thanks to Andrew R. for the feature suggestions!\n" +
			"3. [Change] DTMF playback now stops when pressed while playing. Please email me if you needed that old feature.\n" +
			"\n" +
			"\n" +
			"Version 1.2\n" +
			"=========\n" +
			"1. [New Feature] In Settings, you can now change DTMF playback rates for tone and pause.\n" +
			"2. Bug fix: Couldn't record DTMF when using continuous tone Setting.\n" +
			"    - Thanks to Paul D. for spotting the bug!\n" +
			"\n" +
			"\n" +
			"Version 1.1\n" +
			"=========\n" +
			"1. DTMF tones now play continuous when holding, by default. Use Settings to change\n" +
			"2. Limited number of lines shown for DTMF records\n" +
			"3. In Tone page, added ability to define minimum frequency\n" +
			"4. Better operation for creating custom tones\n" +
			"5. Bug fixes\n" +
			"\n" +
			"\n" +
			"Version 1.0\n" +
			"=========\n" +
			"1. DTMF tones \n" +
			"2. Ability to play any tone\n" +
			"3. Ability to save up to 4 DTMF tones\n" +
			"4. Easy share if you like it!\n" +
			"\n" +
			"\n" +
			"This was a weekend side-project. I welcome all feedback!\n"
	;
	
    public static final String HELP_PROMPT_TITLE = "Help - v" + AppVersion.getName();
    public static final String HELP_PROMPT_MESSAGE =
    		"Directions\n" +
	        "=========\n" +
	        "- Play a tone, any tone.\n" +
	        "- Record unlimited DTMF tones from the DTMF page. Just press RECORD.\n" +
		    "\n" +
		    "\n" +
		    "Key Terms\n" +
		    "=========\n" +
		    "DTMF tone for key 0: 1336Hz, 941Hz, continuous\n" +
		    "DTMF tone for key 1: 1209Hz, 697Hz, continuous\n" +
		    "DTMF tone for key 2: 1336Hz, 697Hz, continuous\n" +
		    "DTMF tone for key 3: 1477Hz, 697Hz, continuous\n" +
		    "DTMF tone for key 4: 1209Hz, 770Hz, continuous\n" +
		    "DTMF tone for key 5: 1336Hz, 770Hz, continuous\n" +
		    "DTMF tone for key 6: 1477Hz, 770Hz, continuous\n" +
		    "DTMF tone for key 7: 1209Hz, 852Hz, continuous\n" +
		    "DTMF tone for key 8: 1336Hz, 852Hz, continuous\n" +
		    "DTMF tone for key 9: 1477Hz, 852Hz, continuous\n" +
		    "DTMF tone for key A: 1633Hz, 697Hz, continuous\n" +
		    "DTMF tone for key B: 1633Hz, 770Hz, continuous\n" +
		    "DTMF tone for key C: 1633Hz, 852Hz, continuous\n" +
		    "DTMF tone for key D: 1633Hz, 941Hz, continuous\n" +
		    "DTMF tone for key #: 1477Hz, 941Hz, continuous\n" +
		    "DTMF tone for key *: 1209Hz, 941Hz, continuous\n" +
		    "\n" +
		    "\n" +
		    "FAQs\n" +
		    "====\n" +
		    "Q: What are all the pause and play times?\n" +
		    "A: By default, recorded playback transmits signaling rate of 93 ms per tone with 40 ms between tones. Each pause is 1 second.\n" + /* And, basic clicks on DTMF tones are 537 ms.*/
		    "\n" +
		    "Q: Q: What does DTMF stand for?\n" +
		    "A: Dual-tone multi-frequency signaling.\n" +
		    "\n" +
		    "Q: What is DTMF used for?\n" +
		    "A: Telecommunication signalling over analog telephone lines, aka tone dialing.\n" +
		    "\n" +
		    "\n" +
		    "Support\n" +
		    "=======\n" +
		    "Please email me if you have any questions, comments, or suggestions for this or future releases.\n" +
		    "\n" +
			"\n" +
			"Disclaimer\n" +
			"=========\n" +
			"Depending on the device and audio hardware, tones may not sound exact.\n"
	;
    
	public static final String EMAIL_SUBJECT = "Android: Simply DTMF App Feedback";
	
	
	
	/******************/
	/* Initialization */
	/******************/
	
	/** The application context. */
	private Context mAppContext;

    /** A singleton instance of HelperCommon. */
    private static HelperCommon sHelper;
    
    /** Returns a singleton instance of this class. */
    public static HelperCommon getInstance(Context context) {
        if (sHelper == null) {
            // Always pass in the Application Context
            sHelper = new HelperCommon(context.getApplicationContext());
        }
        return sHelper;
    }

    private HelperCommon(Context appContext) {
        mAppContext = appContext;
    }
	


	/** Displays an AlertDialog asking user to please support developers by rating it 5 stars. */
	public void askUserToRateApp(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle("Please Rate Me")
				.setMessage("If you find this app useful, please support the app by rating it 5 stars on Google Play!")
				.setPositiveButton("Rate Now", new OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						launchAppInGooglePlay(context);
					}
				}).setNegativeButton("Not now", null).show();
	}
	
	
    /** Brings up this app in Google Play, if available. */
	public void launchAppInGooglePlay(Context context) {
		Uri uri = Uri.parse("market://details?id=" + context.getPackageName()); // Opens app page in Google Play // Good for allowing users to rate app
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			context.startActivity(myAppLinkToMarket); // This must be an Activity Context, NOT Application Context.
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mAppContext, "Unable to find app", Toast.LENGTH_SHORT).show();
		}
	}


	/** Launches an Intent to send email. Uses default values for message. */
	public void sendEmail(Context context, String title) {
		sendEmail(context, title, "");
	}
	
    /** This opens an Intent to send an email to "feedback@simplyadvanced.net?subject=Android: Simply Advanced Unit Converter". */
    public void sendEmail(Context context, String title, String message) {
    	try {
    		Intent emailIntent = new Intent(Intent.ACTION_SEND);
    		emailIntent.setType("vnd.android.cursor.dir/email"); // Or "text/plain" "text/html" "video/mp4"
    		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"feedback@simplyadvanced.net"});
    		emailIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT + " - " + title);
    		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
    		context.startActivity(emailIntent); // Has option to permanently set option.
    		//startActivity(Intent.createChooser(emailIntent, "Send email:")); // Use if you want options every time.
    	} catch (ActivityNotFoundException e) {
//    		Log.e("Emailing contact", "Email failed", e);
    		Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show();
    	}
    }
	
    // Open SA UC to see missing values.
    /** Show a pop up dialog with this app's description. */
//    public void showAbout(final Context context) {
//    	// Linkify the message
//        final SpannableString ss = new SpannableString(
//    			context.getString(R.string.about_description) + "\n" +
//				"\n" +
//				"Version: " + HelperCommon.APP_VERSION + "\n" +
//				"\n" +
//				context.getString(R.string.about_createby) + "\n" +
//				context.getString(R.string.sa_email) + "\n" +
//				"\n" +
//				context.getResources().getText(R.string.about_feedback));
//
//        Linkify.addLinks(ss, Linkify.ALL);
//    	    	
//        final AlertDialog d = new AlertDialog.Builder(context)
//        	.setTitle(R.string.long_app_name)
//        	.setPositiveButton("Email developer", new DialogInterface.OnClickListener() {
//    			public void onClick(DialogInterface dialog, int whichButton) {
//    				sendEmail(context);
//    			}
//    		})
//            .setNegativeButton(android.R.string.ok, null)
//            .setIcon(R.drawable.ic_launcher)
//            .setMessage(ss)
//            .create();
//
//        d.show();
//
//        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // Makes the text clickable.
//    }

	/** Displays an AlertDialog showing help. */
	public void showMenuHelpPrompt(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle(HELP_PROMPT_TITLE)
				.setMessage(HELP_PROMPT_MESSAGE)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(R.string.prompt_button_feedback_label, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendEmail(context, HELP_PROMPT_TITLE);
					}
				})
				.show();
	}

	/** Displays an AlertDialog showing recent updates. */
	public void showRecentAppUpdates(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle(RECENT_UPDATES_PROMPT_TITLE)
				.setMessage(RECENT_UPDATES_PROMPT_MESSAGE)
				.setPositiveButton(android.R.string.ok, null).show();
	}

    /** Opens a page where users can donate. Currently, the page is website via an Intent. */
    public static void openDonatePage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://danialgoodwin.com/app/simplydtmf/donate/"));
        activity.startActivity(intent);
    }

}
