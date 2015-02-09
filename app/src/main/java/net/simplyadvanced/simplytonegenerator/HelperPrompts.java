package net.simplyadvanced.simplytonegenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

/** Static helper method for showing common prompts. */
public class HelperPrompts {

    private static final String HELP_PROMPT_GENERAL_MESSAGE = "\n(If you have any further questions, please let us know.)";

    private static final String HELP_PROMPT_MODE_GENERAL_MESSAGE = "\n\n(If these directions weren't clear or could be made clearer, please email us.)";

    public static final String EMAIL_SUBJECT = "Android: Simply DTMF Tone Generator App Feedback - ";

    public static final String ABOUT_TITLE = "About - v" + AppVersion.getName();
    public static final String ABOUT_MESSAGE = "\"The function of good software is to make the complex appear to be simple.\" - Grady Booch\n\n" +
            "Developer's message:\nHi, my name is Danial Goodwin. I hope you are enjoying this app. This started off as just a weekend side-project, but is growing much more than I expected. " +
            "If you find this app useful, then please consider contributing to support my development time and costs. Any amount contributed would mean a lot to me. Thank you.\n\n" +
            "If you have any questions, comments, or suggestions, then please feel free to email me anytime.\n";



    /** No need to instantiate this class. */
	private HelperPrompts() {}

	/** Displays an AlertDialog showing the About popup. */
	public static void showPromptAbout(final Activity activity) {
		new AlertDialog.Builder(activity)
				.setTitle(ABOUT_TITLE)
				.setMessage(ABOUT_MESSAGE)
				.setPositiveButton(activity.getString(R.string.about_prompt_donate_label), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
                        HelperCommon.openDonatePage(activity);
					}
				})
				.setNeutralButton(activity.getString(R.string.about_prompt_feedback_label), new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HelperCommon.getInstance(activity).sendEmail(activity, ABOUT_TITLE);
                    }
                })
				.setNegativeButton(android.R.string.cancel, null)
				.show();
	}

	
	/** Displays an AlertDialog showing help for the Spinner options. */
//	private static void showHelpPromptGeneric(final Activity activity, final String title, final String message) {
//		new AlertDialog.Builder(activity)
//				.setTitle(title)
//				.setMessage(message)
//				.setPositiveButton(android.R.string.ok, null)
//				.setNegativeButton(R.string.phrase_email, new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						HelperCommon.getInstance(activity).sendEmail(activity, title);
//					}
//				})
//				.show();
//	}
	
}
