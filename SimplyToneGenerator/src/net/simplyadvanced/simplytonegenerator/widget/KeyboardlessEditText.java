package net.simplyadvanced.simplytonegenerator.widget;

import java.lang.reflect.Method;

import net.simplyadvanced.utils.ReflectionUtils;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/** EditText which suppresses IME show up.
 * 
 * This is the same as an native EditText, except that no soft keyboard
 * will appear when user clicks on widget. This is modeled after the keyboard
 * in the default Android KitKat dialer app.
 * More info: https://android.googlesource.com/platform/packages/apps/Dialer/+/master/src/com/android/dialer/dialpad/DigitsEditText.java
 * Helpful: https://code.google.com/p/csipsimple/source/browse/trunk/CSipSimple/src/com/csipsimple/ui/dialpad/DigitsEditText.java?r=2014 */
public class KeyboardlessEditText extends EditText {

    private Method showSoftInputOnFocus = null;
    
    

	public KeyboardlessEditText(Context context) {
		super(context);
		initialize();
	}

	public KeyboardlessEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	
	public KeyboardlessEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}
	
	private void initialize() {
        setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

//      setShowSoftInputOnFocus(false); // This is a hidden method in TextView.
        reflexSetShowSoftInputOnFocus(false); // Workaround.
	}
	
	
	
	@Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        hideKeyboard();
    }
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean ret = super.onTouchEvent(event);
        // Must be done after super.onTouchEvent()
        hideKeyboard();
        return ret;
    }
    
    
    
    private void hideKeyboard() {
        final InputMethodManager imm = ((InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }
    
    private void reflexSetShowSoftInputOnFocus(boolean show) {
    	if (showSoftInputOnFocus == null) {
        	showSoftInputOnFocus = ReflectionUtils.safelyGetSuperclassMethod(getClass(),
        			"setShowSoftInputOnFocus", boolean.class); 
    	}
    
    	if (showSoftInputOnFocus != null) {
        	ReflectionUtils.safelyInvokeMethod(showSoftInputOnFocus, this, show);
    	} else {
    		// Use fallback method. Not tested.
            hideKeyboard();
    	}
    }
	
}
