package com.harshbhanderi.hbkeyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class hbkeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean cap=true;

    @Override
    public View onCreateInputView() {
        kv=(KeyboardView) getLayoutInflater().inflate(R.layout.main_xml,null);
        keyboard=new Keyboard(this,R.xml.keyboard_ui);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);

        return kv;
    }

    @Override
    public void onPress(int i) {
        InputConnection ic=getCurrentInputConnection();
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                cap=!cap;
                keyboard.setShifted(cap);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char ch=(char)i;
                if (Character.isLetter(ch) && !cap)
                {
                    ch=Character.toLowerCase(ch);
                    ic.commitText(String.valueOf(ch),1);
                }
                else
                {
                    ch=Character.toUpperCase(ch);
                    ic.commitText(String.valueOf(ch),1);
                }
        }
        playclick(i);


    }

    private void playclick(int i) {
        AudioManager am=(AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);


        }

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}