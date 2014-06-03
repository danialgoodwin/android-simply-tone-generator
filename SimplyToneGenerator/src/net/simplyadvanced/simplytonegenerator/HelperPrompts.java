package net.simplyadvanced.simplytonegenerator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

/**  */
public class HelperPrompts {
	private HelperPrompts() {}
	
	
	
	private static final String HELP_PROMPT_MODE_GENERAL_MESSAGE = "\n\n(If these directions weren't clear or could be made clearer, please email us.)";
	
	public static final String EMAIL_SUBJECT = "Android: Simply Tone Generator App Feedback - ";
	
	
	
	/***************/
	/* Menu->About */
	/***************/

	public static final String ABOUT_TITLE = "About - v" + HelperCommon.APP_VERSION;
	public static final String ABOUT_MESSAGE = "\"The function of good software is to make the complex appear to be simple.\" - Grady Booch\n\n" +
			"Developer's message:\nHi, my name is Danial Goodwin. I hope you are enjoying this app. This started off as just a weekend side-project, but is growing much more than I expected. " +
			"If you find this app useful, then please consider contributing to support my development costs. Any amount contributed would mean a lot to me. Thank you.\n\n" +
			"If you have any suggestions, comments, or questions, then please feel free to email me.\n";

	/** Displays an AlertDialog showing the About popup.
	 * @param context Must be an Activity Context to work. */
	public static void showPromptAbout(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle(ABOUT_TITLE)
				.setMessage(ABOUT_MESSAGE)
				.setPositiveButton("Donate", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// NOTE: open a new WebView page instead? It could look better
		            	Intent i = new Intent(Intent.ACTION_VIEW);
		            	i.setData(Uri.parse("http://danialgoodwin.com/app/simplytonegenerator/donate/"));
		            	context.startActivity(i);
					}
				})
				.setNeutralButton("Feedback", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HelperCommon.getInstance(context).sendEmail(context, ABOUT_TITLE);
					}
				})
				.setNegativeButton(android.R.string.cancel, null)
				.show();
	}
	
	
	
	/**********/
	/* Extras */
	/**********/

//	private static final String HELP_PROMPT_MYCARDVIEW_PHONE_TITLE = "More Phone Info";
//	private static final String HELP_PROMPT_MYCARDVIEW_PHONE_MESSAGE = UtilBuild.getAllBuildInfo() + "\n";
	
	private static final String HELP_PROMPT_GENERAL_MESSAGE = "\n(If you have any further questions, please let us know.)";

	/** Displays an AlertDialog showing help for MyLocationView. */
	public static void showHelpPromptLocationView(final Context context) {
//		showHelpPromptGeneric(context, HELP_PROMPT_LOCATION_VIEW_TITLE, HELP_PROMPT_LOCATION_VIEW_MESSAGE + HELP_PROMPT_GENERAL_MESSAGE);
	}

	/** Displays an AlertDialog showing help for MyCardView.Phone. */
//	public static void showHelpPromptMyCardViewPhone(final Context context) {
//		showHelpPromptGeneric(context, HELP_PROMPT_MYCARDVIEW_PHONE_TITLE, HELP_PROMPT_MYCARDVIEW_PHONE_MESSAGE + HELP_PROMPT_GENERAL_MESSAGE);
//	}
	
	
	/** Displays an AlertDialog showing help for the Spinner options.
	 * @param context Must be an Activity Context to work. */
	private static void showHelpPromptGeneric(final Context context, final String title, final String message) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(R.string.phrase_email, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HelperCommon.getInstance(context).sendEmail(context, title);
					}
				})
				.show();
	}
	


	/***************************************************************************/
	/* Displays an AlertDialog showing help for SPINNER_OPTION_0_GPS_MODE_INT. */
	/***************************************************************************/

//	public static void showHelpPromptCycleOnceMode(final Context context) {
//		showHelpPromptSpinner(context, HELP_PROMPT_CYCLE_ONCE_MODE_TITLE, HELP_PROMPT_CYCLE_ONCE_MODE_MESSAGE + HELP_PROMPT_MODE_GENERAL_MESSAGE);
//	}
//
//	/** Displays an AlertDialog showing help for SPINNER_OPTION_1_AUTOREPEAT_MODE_INT. */
//	public static void showHelpPromptAutorepeatMode(final Context context) {
//		showHelpPromptSpinner(context, HELP_PROMPT_AUTOREPEAT_MODE_TITLE, HELP_PROMPT_AUTOREPEAT_MODE_MESSAGE + HELP_PROMPT_MODE_GENERAL_MESSAGE);
//	}
//
//	/** Displays an AlertDialog showing help for SPINNER_OPTION_2_PASSIVE_MODE_INT. */
//	public static void showHelpPromptPassiveMode(final Context context) {
//		showHelpPromptSpinner(context, HELP_PROMPT_PASSIVE_MODE_TITLE, HELP_PROMPT_PASSIVE_MODE_MESSAGE + HELP_PROMPT_MODE_GENERAL_MESSAGE);
//	}
//
//	/** Displays an AlertDialog showing help for SPINNER_OPTION_3_LTE_MODE_INT. */
//	public static void showHelpPromptLteMode(final Context context) {
//		showHelpPromptSpinner(context, HELP_PROMPT_LTE_MODE_TITLE, HELP_PROMPT_LTE_MODE_MESSAGE + HELP_PROMPT_MODE_GENERAL_MESSAGE);
//	}
//
//	/** Displays an AlertDialog showing help for the Spinner options. */
//	private static void showHelpPromptSpinner(final Context context, final String title, final String message) {
//		new AlertDialog.Builder(context)
//				.setTitle(title)
//				.setMessage(message)
//				.setPositiveButton(android.R.string.ok, null)
//				.setNegativeButton(R.string.phrase_email, new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						sendEmail(context, title);
//					}
//				})
//				.show();
//	}
//
//	/** Launches an Intent to send email. */
//	private static void sendEmail(final Context context, final String title) {
//		try {
//			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//			emailIntent.setType("vnd.android.cursor.dir/email");
//			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"feedback@simplyadvanced.net"});
//			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, EMAIL_SUBJECT + title);
//			//emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "If reporting a bug, please include Android version, phone type, and steps on how to recreate the problem.");
//			context.startActivity(emailIntent); // Has option to permanently set option.
//		} catch (ActivityNotFoundException e) {
//    		Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show();
//		}
//	}
	
}
