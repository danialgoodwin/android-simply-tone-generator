package net.simplyadvanced.simplytonegenerator;

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

    public static final String ABOUT_TITLE = "About - v" + HelperCommon.APP_VERSION;
    public static final String ABOUT_MESSAGE = "\"The function of good software is to make the complex appear to be simple.\" - Grady Booch\n\n" +
            "Developer's message:\nHi, my name is Danial Goodwin. I hope you are enjoying this app. This started off as just a weekend side-project, but is growing much more than I expected. " +
            "If you find this app useful, then please consider contributing to support my development costs. Any amount contributed would mean a lot to me. Thank you.\n\n" +
            "If you have any suggestions, comments, or questions, then please feel free to email me.\n";



    /** No need to instantiate this class. */
	private HelperPrompts() {}

	/** Displays an AlertDialog showing the About popup.
	 * @param context Must be an Activity Context to work. */
	public static void showPromptAbout(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle(ABOUT_TITLE)
				.setMessage(ABOUT_MESSAGE)
				.setPositiveButton(context.getString(R.string.about_prompt_donate_label), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// NOTE: open a new WebView page instead? It could look better
		            	Intent i = new Intent(Intent.ACTION_VIEW);
		            	i.setData(Uri.parse("http://danialgoodwin.com/app/simplytonegenerator/donate/"));
		            	context.startActivity(i);
					}
				})
				.setNeutralButton(context.getString(R.string.about_prompt_feedback_label), new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HelperCommon.getInstance(context).sendEmail(context, ABOUT_TITLE);
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
