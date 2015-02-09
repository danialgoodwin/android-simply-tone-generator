/**
 * Created by Danial on 1/25/2015.
 */
package net.simplyadvanced.simplytonegenerator.ui;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Arrays;

/**
 *
 * When using this class, you should override the system back button to close the custom keyboard.
 * <code>
 *     @Override public void onBackPressed() {
 *         if (mCustomKeyboard.isVisible()) { mCustomKeyboard.hide(); } else { super.onBackPressed(); }
 *     }
 * </code>
 *
 */
public class BaseKeyboard {
    private static final String LOGCAT_TAG = "DEBUG: BaseKeyboard";
    private static final boolean IS_SHOW_DEBUG_LOGCAT = true;
    private static void log(String message) {
        if (IS_SHOW_DEBUG_LOGCAT) {
            Log.d(LOGCAT_TAG, message);
        }
    }

    //    public static final int KEYCODE_CANCEL = -3; // Keyboard.KEYCODE_CANCEL.
    public static final int KEYCODE_DONE = -4; // Keyboard.KEYCODE_DONE.
    public static final int KEYCODE_DELETE = -5; // Keyboard.KEYCODE_DELETE.

    private Activity mActivity;
    private InputMethodManager mInputMethodManager;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;

    /**
     * Creates a keyboard from the given xml key layout file. This class will setup everything
     * needed for the keyboard to work. Though, you likely should override the onBackPressed() to
     * get that to `hide()` this custom keyboard. Also, you should likely prevent any standard
     * keyboards from automatically showing when the page loads. This will handle other instances.
     * @param activity the application or service context
     * @param keyboardView the view for the custom keyboard
     * @param xmlLayoutResId the resource file that contains the keyboard layout and keys.
     */
    public BaseKeyboard(Activity activity, KeyboardView keyboardView, int xmlLayoutResId) {
        mActivity = activity;
        mInputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        mKeyboardView = keyboardView;
        mKeyboard = new Keyboard(activity, xmlLayoutResId);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
    }

    // Doesn't look good on Nexus 4 API 21, perhaps more devices. Seems API is buggy.
//    public CustomKeyboard(Activity activity, KeyboardView keyboardView, CharSequence characters) {
//        mActivity = activity;
//        mInputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        mKeyboardView = keyboardView;
//        mKeyboard = new Keyboard(activity, R.xml.keyboard_template, characters, 5, 0);
//        mKeyboardView.setKeyboard(mKeyboard);
//        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
//    }

    /** Hides the default standard keyboard. This does not affect the custom keyboard. */
    public static void hideStandardKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Sets up the EditText to work with the custom keyboard.
     * Note: The following EditText callbacks will be overridden here:
     * - setOnFocusChangeListener
     * - setOnClickListener
     * - setOnTouchListener
     * @param editText the EditText to setup with this custom keyboard
     */
    public void registerEditText(EditText editText) {
        editText.setSelectAllOnFocus(true);
        // Handle showing and hiding of keyboard when focus changes on the EditText.
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) { show(v); } else { hide(); }
            }
        });
        // Allow clicking on EditText to open the keyboard. EditText may have focus with the
        // keyboard hidden and user may want to show keyboard without losing and gaining focus.
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });
        // Workaround for making sure regular keyboard doesn't show.
//        editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                EditText edittext = (EditText) v;
//                int inType = edittext.getInputType(); // Backup the input type.
//                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard.
//                edittext.onTouchEvent(event); // Call native handler.
//                edittext.setInputType(inType); // Restore input type.
//                return true; // Consumes touch event.
//            }
//        });

        // Disable spell check (hex strings look like words to Android)
//        editText.setInputType(editText.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
    }

    public void hide() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }

    public void show(View v) {
        // The same KeyboardView may be used for different Keyboard, so make sure the proper
        // Keyboard is set.
        mKeyboardView.setKeyboard(mKeyboard);

        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        // Hide regular keyboard.
        if (v != null) { mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0); }
    }

    public boolean isVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /**
     * Enables or disables the key feedback popup. This is a popup that shows a magnified
     * version of the depressed key. By default the preview is enabled.
     * @param previewEnabled whether or not to enable the key feedback popup
     * @see android.inputmethodservice.KeyboardView#isPreviewEnabled()
     */
    public void setPreviewEnabled(boolean previewEnabled) {
        mKeyboardView.setPreviewEnabled(previewEnabled);
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onPress(int primaryCode) {}
        @Override public void onRelease(int primaryCode) {}

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            log("onKey(" + primaryCode + ", " + Arrays.toString(keyCodes) + "), start");
            // Get the EditText and its Editable.
            View focusCurrent = mActivity.getWindow().getCurrentFocus();
            log("onKey(), focusCurrent: " + focusCurrent);
            if (focusCurrent == null || !(focusCurrent instanceof EditText)) { return; }
            // This wouldn't work for subclasses of EditText.
//            if (focusCurrent == null || focusCurrent.getClass() != EditText.class ) { return; }
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int selectionStart = edittext.getSelectionStart();
            int selectionEnd = edittext.getSelectionEnd();
            // Handle key
            if (primaryCode == BaseKeyboard.KEYCODE_DELETE) {
                log("onKey(), You just pressed delete button");
                if (editable != null) {
                    if (selectionStart == selectionEnd) {
                        // Delete previous character only if selectionStart isn't at 0.
                        if (selectionStart != 0) {
                            editable.delete(selectionStart - 1, selectionStart);
                        }
                    } else {
                        // Delete selection.
                        editable.replace(selectionStart, selectionEnd, "");
                    }
                }
            } else if (primaryCode == BaseKeyboard.KEYCODE_DONE) {
                log("onKey(), You just pressed done button");
                hide();
            } else {
                log("onKey(), You just pressed button: " + String.valueOf((char) primaryCode) + ", " + primaryCode);
                if (editable != null) {
                    editable.replace(selectionStart, selectionEnd, String.valueOf((char) primaryCode));
                    edittext.setSelection(selectionStart + 1);
                }
            }
        }

        @Override public void onText(CharSequence text) {}
        @Override public void swipeLeft() {}
        @Override public void swipeRight() {}
        @Override public void swipeDown() {}
        @Override public void swipeUp() {}
    };

}
