package net.simplyadvanced.simplytonegenerator.mainpage.dtmf;

import java.util.HashSet;

import net.simplyadvanced.simplytonegenerator.R;
import net.simplyadvanced.simplytonegenerator.mainpage.dtmfrecord.db.model.DtmfRecord;

import android.graphics.PorterDuff; // Eclipse doesn't give this as an option
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

// Inspired from: https://android.googlesource.com/platform/packages/apps/Dialer/+/master/src/com/android/dialer/dialpad/DialpadFragment.java
/** This represents the view for DTMF dialpad and box to edit in, with backspace button. */
public class DialpadFragment extends Fragment implements View.OnClickListener,
        View.OnLongClickListener, View.OnTouchListener {
	
	
	
	private OnInputChangedListener mOnInputChangedListener;

    public interface OnInputChangedListener {
		void onInputChanged(Editable editable);
	}
	
	

    private static final int TONE_LENGTH_INFINITE = -1;
    
    private View mDigitsContainer, mTitleInputContainer;
	private EditText mDigits, mTitleInput;
	private View mBackspaceButton;
	private View mPauseButton;
    
    /** Set of dialpad keys that are currently being pressed */
    @SuppressWarnings("Convert2Diamond")
    private final HashSet<View> mPressedDialpadKeys = new HashSet<View>(16);
    
    private static final InputFilter[] mAllCapsFilter = { new InputFilter.AllCaps() };

    /** A singleton instance of DtmfUtils. */
	private static DtmfUtils mDtmfUtils;
	
	final int[] mButtonIds = new int[] {
			R.id.button0, R.id.button1, R.id.button2, R.id.button3,
			R.id.button4, R.id.button5, R.id.button6, R.id.button7,
			R.id.button8, R.id.button9, R.id.buttonA, R.id.buttonB,
			R.id.buttonC, R.id.buttonD, R.id.buttonP, R.id.buttonS};

	private Button button0, button1, button2, button3;
	private Button button4, button5, button6, button7;
	private Button button8, button9, buttonP, buttonS;
	private Button buttonA, buttonB, buttonC, buttonD;
	
	
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	final View fragmentView = inflater.inflate(R.layout.dialpad_fragment, container,
                false);
//    	fragmentView.buildLayer();

    	mDigitsContainer = fragmentView.findViewById(R.id.digitsContainer);
    	mTitleInputContainer = fragmentView.findViewById(R.id.titleInputContainer);
    	mDigits = (EditText) fragmentView.findViewById(R.id.editTextRecord);
    	mTitleInput = (EditText) fragmentView.findViewById(R.id.editTextTitle);
//		mDigits.setRawInputType(InputType.TYPE_CLASS_TEXT);
//		editTextRecord.setKeyListener(null); // Doesn't seem to do anything.

    	mBackspaceButton = fragmentView.findViewById(R.id.backspaceButton);
    	mPauseButton = fragmentView.findViewById(R.id.pauseButton);

    	mDigits.setOnClickListener(this);
    	mDigits.setOnLongClickListener(this);
    	mBackspaceButton.setOnClickListener(this);
    	mBackspaceButton.setOnLongClickListener(this);
    	mPauseButton.setOnClickListener(this);
        
    	mDigits.setFilters(mAllCapsFilter);
    	mDigits.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable editable) {
//				editable.setFilters(mAllCapsFilter);
				
			    if (mOnInputChangedListener != null) {
			        mOnInputChangedListener.onInputChanged(editable /*mDigits.getText()*/);
			    }
			}
		});
    	
    	setupKeypad(fragmentView);

    	return fragmentView;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
   	    // Sanity-check, just in case. There was an edge-case crash.
    	mDtmfUtils = DtmfUtils.getInstance();
    }
    
    @Override
    public void onStop() {
    	mDtmfUtils.stopAll();
    	super.onStop();
    }
    
    @Override
    public void onDestroy() {
//    	if (mDtmfUtils != null) {
        	// These two could possibly be synchronized.
//	    	mDtmfUtils.destroy();
//	    	mDtmfUtils = null;
//    	}
    	super.onDestroy();
    }

    

    // Used for dialpad keys. Be careful when adding any other views here. You
    // will need to do some checks here or in the called functions.
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		v.setFocusable(false);
		v.onWindowFocusChanged(false);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			inputToneToEditText(v);
//			playIndefiniteDtmfTone(v);
			onPressed(v, true);
    		v.getBackground().setColorFilter(0xFF33B5E5, PorterDuff.Mode.MULTIPLY); // 0xFF00FF00
//			v.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF0055E5));
//			v.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF0099CC));
    		return false;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
//			stopIndefiniteDtmfToneNow(v);
			onPressed(v, false);
    		v.getBackground().clearColorFilter();
//			v.setPressed(false); // Works, clears button focus.
			return true; // Has consumed event. // Don't want to do anything else from this gesture.
		case MotionEvent.ACTION_MOVE:
			// Do nothing.
			return false;
		}
		return false;
	}

	@Override
	public void onClick(View view) {
        switch (view.getId()) {
		    case R.id.backspaceButton: { keyPressed(KeyEvent.KEYCODE_DEL); break; }
		    case R.id.pauseButton: { keyPressed(KeyEvent.KEYCODE_COMMA); break; }
            case R.id.editTextRecord: { mDigits.setCursorVisible(true); break; }
        }
	}
	
	@Override
	public boolean onLongClick(View view) {
        final Editable digits = mDigits.getText();
        switch (view.getId()) {
		    case R.id.backspaceButton: {
		    	digits.clear();
		    	
		    	// May not be needed, but this was included in the Android source
		    	// because there may be a bug with a few devices causing not to
		    	// leave pressed state.
		    	mBackspaceButton.setPressed(false);
                return true;
		    }
            case R.id.editTextRecord: {
            	// Right now EditText does not show the "paste" option when cursor is not visible.
                // To show that, make the cursor visible, and return false, letting the EditText
                // show the option by itself.
                mDigits.setCursorVisible(true);
                return false;
            }
        }
        return false;
	}


	
	private void setupKeypad(View fragmentView) {
        for (int buttonId : mButtonIds) {
            fragmentView.findViewById(buttonId).setOnTouchListener(this);
        }

		button0 = (Button) fragmentView.findViewById(R.id.button0);
		button1 = (Button) fragmentView.findViewById(R.id.button1);
		button2 = (Button) fragmentView.findViewById(R.id.button2); // ABC
		button3 = (Button) fragmentView.findViewById(R.id.button3); // DEF
		button4 = (Button) fragmentView.findViewById(R.id.button4); // GHI
		button5 = (Button) fragmentView.findViewById(R.id.button5); // JKL
		button6 = (Button) fragmentView.findViewById(R.id.button6); // MNO
		button7 = (Button) fragmentView.findViewById(R.id.button7); // PQRS
		button8 = (Button) fragmentView.findViewById(R.id.button8); // TUV
		button9 = (Button) fragmentView.findViewById(R.id.button9); // WXYZ
		buttonA = (Button) fragmentView.findViewById(R.id.buttonA);
		buttonB = (Button) fragmentView.findViewById(R.id.buttonB);
		buttonC = (Button) fragmentView.findViewById(R.id.buttonC);
		buttonD = (Button) fragmentView.findViewById(R.id.buttonD);
		buttonP = (Button) fragmentView.findViewById(R.id.buttonP);
		buttonS = (Button) fragmentView.findViewById(R.id.buttonS);
	}
	
	/**
     * When a key is pressed, we start playing DTMF tone, and enter the digit
     * immediately. When a key is released, we stop the tone.
     */
	private void onPressed(View view, boolean pressed) {
        if (pressed) {
            switch (view.getId()) {
	            case R.id.button0: {
	                keyPressed(KeyEvent.KEYCODE_0);
	                break;
	            }
                case R.id.button1: {
                    keyPressed(KeyEvent.KEYCODE_1);
                    break;
                }
                case R.id.button2: {
                    keyPressed(KeyEvent.KEYCODE_2);
                    break;
                }
                case R.id.button3: {
                    keyPressed(KeyEvent.KEYCODE_3);
                    break;
                }
                case R.id.button4: {
                    keyPressed(KeyEvent.KEYCODE_4);
                    break;
                }
                case R.id.button5: {
                    keyPressed(KeyEvent.KEYCODE_5);
                    break;
                }
                case R.id.button6: {
                    keyPressed(KeyEvent.KEYCODE_6);
                    break;
                }
                case R.id.button7: {
                    keyPressed(KeyEvent.KEYCODE_7);
                    break;
                }
                case R.id.button8: {
                    keyPressed(KeyEvent.KEYCODE_8);
                    break;
                }
                case R.id.button9: {
                    keyPressed(KeyEvent.KEYCODE_9);
                    break;
                }
                case R.id.buttonA: {
                    keyPressed(KeyEvent.KEYCODE_A);
                    break;
                }
                case R.id.buttonB: {
                    keyPressed(KeyEvent.KEYCODE_B);
                    break;
                }
                case R.id.buttonC: {
                    keyPressed(KeyEvent.KEYCODE_C);
                    break;
                }
                case R.id.buttonD: {
                    keyPressed(KeyEvent.KEYCODE_D);
                    break;
                }
                case R.id.buttonP: {
                    keyPressed(KeyEvent.KEYCODE_POUND);
                    break;
                }
                case R.id.buttonS: {
                    keyPressed(KeyEvent.KEYCODE_STAR);
                    break;
                }
                default: {
//                    Log.wtf(TAG, "Unexpected onTouch(ACTION_DOWN) event from: " + view);
                    break;
                }
            }
            mPressedDialpadKeys.add(view);
        } else {
//            view.jumpDrawablesToCurrentState();
            mPressedDialpadKeys.remove(view);
            if (mPressedDialpadKeys.isEmpty()) {
            	if (mDtmfUtils != null) {
            		mDtmfUtils.stopTone();
            	}
            }
        }
	}
    
	/** Plays corresponding tone and inputs value to input box. */
	private void keyPressed(int keyCode) {
		
		int toneLength = TONE_LENGTH_INFINITE;
				
//		if (mDtmfUtils != null) {
			mDtmfUtils.stopTone();
	        switch (keyCode) {
		        case KeyEvent.KEYCODE_0:
		            mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_0, toneLength);
		            break;
	            case KeyEvent.KEYCODE_1:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_1, toneLength);
	                break;
	            case KeyEvent.KEYCODE_2:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_2, toneLength);
	                break;
	            case KeyEvent.KEYCODE_3:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_3, toneLength);
	                break;
	            case KeyEvent.KEYCODE_4:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_4, toneLength);
	                break;
	            case KeyEvent.KEYCODE_5:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_5, toneLength);
	                break;
	            case KeyEvent.KEYCODE_6:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_6, toneLength);
	                break;
	            case KeyEvent.KEYCODE_7:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_7, toneLength);
	                break;
	            case KeyEvent.KEYCODE_8:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_8, toneLength);
	                break;
	            case KeyEvent.KEYCODE_9:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_9, toneLength);
	                break;
	            case KeyEvent.KEYCODE_A:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_A, toneLength);
	                break;
	            case KeyEvent.KEYCODE_B:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_B, toneLength);
	                break;
	            case KeyEvent.KEYCODE_C:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_C, toneLength);
	                break;
	            case KeyEvent.KEYCODE_D:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_D, toneLength);
	                break;
	            case KeyEvent.KEYCODE_POUND:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_P, toneLength);
	                break;
	            case KeyEvent.KEYCODE_STAR:
	                mDtmfUtils.playTone(ToneGenerator.TONE_DTMF_S, toneLength);
	                break;
	            default:
	                break;
	        }
//		}
        
        if (isInputAreaShowing()) {
	        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
	        mDigits.onKeyDown(keyCode, event);
        }
	}
	
	/** Returns true if input is A, B, C, or D. Otherwise, returns false. */
//	private static boolean isKeyCodeALetter(int keyCode) {
//		switch (keyCode) {
//	        case KeyEvent.KEYCODE_A: // Fall-through.
//	        case KeyEvent.KEYCODE_B: // Fall-through.
//	        case KeyEvent.KEYCODE_C: // Fall-through.
//	        case KeyEvent.KEYCODE_D:
//	        	return true;
//        	default:
//        		return false;	
//		}
//	}
	
	/**
     * Remove the digit just before the current position. This can be used if we want to replace
     * the previous digit or cancel previously entered character.
     * 
     * May use this in the future when overriding what long-click dialpad key pressed do.
     */
    @SuppressWarnings("unused")
	private void removePreviousDigitIfPossible() {
        final int currentPosition = mDigits.getSelectionStart();
        if (currentPosition > 0) {
            mDigits.setSelection(currentPosition);
            mDigits.getText().delete(currentPosition - 1, currentPosition);
        }
    }
    
    public void clearInputArea() {
    	if (mDigits != null) {
    		mDigits.getText().clear();
    	}
    	if (mTitleInput != null) {
    		mTitleInput.getText().clear();
    	}
    }
    
    /** Returns the current String from input area. If not available, then
     * returns empty String. */
    public String getToneInput() {
    	if (mDigits != null) {
    		return mDigits.getText().toString();
    	}
    	return "";
    }
    
    /** Returns the current String from input title area. If not available, then
     * returns empty String. */
    public String getTitleInput() {
    	if (mTitleInput != null) {
    		return mTitleInput.getText().toString();
    	}
    	return "";
    }
    
    /** Show or hide input area.
     * @param isShow set true to show input area, set false to hide input area */
    public void showInputArea(boolean isShow) {
    	if (isShow) {
    		mTitleInputContainer.setVisibility(View.VISIBLE);
    		mDigitsContainer.setVisibility(View.VISIBLE);
    	} else {
    		mDigitsContainer.setVisibility(View.GONE);
    		mTitleInputContainer.setVisibility(View.GONE);
    	}
    }

    /** Show or hide input area and put the record in the input area.
     * @param isShow set true to show input area, set false to hide input area */
    public void showInputArea(DtmfRecord record, boolean isShow) {
        showInputArea(isShow);
        mTitleInput.setText(record.getTitle());
        mDigits.setText(record.getTone());
    }
    
    /** Returns true if input area is showing, otherwise false. */
    private boolean isInputAreaShowing() {
    	return mDigitsContainer.getVisibility() == View.VISIBLE;
    }
    
    /** Show/hide alphabet with true/false. */
    public void showPhoneLettersOnNumbers(boolean show) {
    	if (show) {
    		button1.setText(setStyledText("1\n   ")); // Allows number to align with others.
			button2.setText(setStyledText("2\nABC"));
			button3.setText(setStyledText("3\nDEF"));
			button4.setText(setStyledText("4\nGHI"));
			button5.setText(setStyledText("5\nJKL"));
			button6.setText(setStyledText("6\nMNO"));
			button7.setText(setStyledText("7\nPQRS"));
			button8.setText(setStyledText("8\nTUV"));
			button9.setText(setStyledText("9\nWXYZ"));
		} else {
			button1.setText("1");
			button2.setText("2");
			button3.setText("3");
			button4.setText("4");
			button5.setText("5");
			button6.setText("6");
			button7.setText("7");
			button8.setText("8");
			button9.setText("9");
    	}
    }

	/** Returns the input with all but first two characters a little smaller. */
	private SpannableString setStyledText(String value) {
		 SpannableString ss = new SpannableString(value);
		 ss.setSpan(new RelativeSizeSpan(0.618f), 2, value.length(), 0); // set size
//		 ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
		 return ss;
	}
    
    /** Returns true if input area is empty, otherwise false. */
//    private boolean isDigitsEmpty() {
//        return mDigits.length() == 0;
//    }
    
	
	
	/******************************************/
	/* Old code, may use again in the future. */
	/******************************************/

//	private void playIndefiniteDtmfTone(final View v) {
//		if      (v.equals(button0)) { mDtmfUtils.start0(); }
//		else if (v.equals(button1)) { mDtmfUtils.start1(); }
//		else if (v.equals(button2)) { mDtmfUtils.start2(); }
//		else if (v.equals(button3)) { mDtmfUtils.start3(); }
//		else if (v.equals(button4)) { mDtmfUtils.start4(); }
//		else if (v.equals(button5)) { mDtmfUtils.start5(); }
//		else if (v.equals(button6)) { mDtmfUtils.start6(); }
//		else if (v.equals(button7)) { mDtmfUtils.start7(); }
//		else if (v.equals(button8)) { mDtmfUtils.start8(); }
//		else if (v.equals(button9)) { mDtmfUtils.start9(); }
//		else if (v.equals(buttonA)) { mDtmfUtils.startA(); }
//		else if (v.equals(buttonB)) { mDtmfUtils.startB(); }
//		else if (v.equals(buttonC)) { mDtmfUtils.startC(); }
//		else if (v.equals(buttonD)) { mDtmfUtils.startD(); }
//		else if (v.equals(buttonP)) { mDtmfUtils.startP(); }
//		else if (v.equals(buttonS)) { mDtmfUtils.startS(); }
//	}
//	
//	private void stopIndefiniteDtmfToneNow(final View v) {
//		if      (v.equals(button0)) { mDtmfUtils.stop0(); }
//		else if (v.equals(button1)) { mDtmfUtils.stop1(); }
//		else if (v.equals(button2)) { mDtmfUtils.stop2(); }
//		else if (v.equals(button3)) { mDtmfUtils.stop3(); }
//		else if (v.equals(button4)) { mDtmfUtils.stop4(); }
//		else if (v.equals(button5)) { mDtmfUtils.stop5(); }
//		else if (v.equals(button6)) { mDtmfUtils.stop6(); }
//		else if (v.equals(button7)) { mDtmfUtils.stop7(); }
//		else if (v.equals(button8)) { mDtmfUtils.stop8(); }
//		else if (v.equals(button9)) { mDtmfUtils.stop9(); }
//		else if (v.equals(buttonA)) { mDtmfUtils.stopA(); }
//		else if (v.equals(buttonB)) { mDtmfUtils.stopB(); }
//		else if (v.equals(buttonC)) { mDtmfUtils.stopC(); }
//		else if (v.equals(buttonD)) { mDtmfUtils.stopD(); }
//		else if (v.equals(buttonP)) { mDtmfUtils.stopP(); }
//		else if (v.equals(buttonS)) { mDtmfUtils.stopS(); }
//	}
//
//	private int getToneTypeFromView(final View v) {
//		if      (v.equals(button0)) { return ToneGenerator.TONE_DTMF_0; }
//		else if (v.equals(button1)) { return ToneGenerator.TONE_DTMF_1; }
//		else if (v.equals(button2)) { return ToneGenerator.TONE_DTMF_2; }
//		else if (v.equals(button3)) { return ToneGenerator.TONE_DTMF_3; }
//		else if (v.equals(button4)) { return ToneGenerator.TONE_DTMF_4; }
//		else if (v.equals(button5)) { return ToneGenerator.TONE_DTMF_5; }
//		else if (v.equals(button6)) { return ToneGenerator.TONE_DTMF_6; }
//		else if (v.equals(button7)) { return ToneGenerator.TONE_DTMF_7; }
//		else if (v.equals(button8)) { return ToneGenerator.TONE_DTMF_8; }
//		else if (v.equals(button9)) { return ToneGenerator.TONE_DTMF_9; }
//		else if (v.equals(buttonA)) { return ToneGenerator.TONE_DTMF_A; }
//		else if (v.equals(buttonB)) { return ToneGenerator.TONE_DTMF_B; }
//		else if (v.equals(buttonC)) { return ToneGenerator.TONE_DTMF_C; }
//		else if (v.equals(buttonD)) { return ToneGenerator.TONE_DTMF_D; }
//		else if (v.equals(buttonP)) { return ToneGenerator.TONE_DTMF_P; }
//		else if (v.equals(buttonS)) { return ToneGenerator.TONE_DTMF_S; }
//		return ToneGenerator.TONE_DTMF_0; // Error if code reaches here.
//	}

}
