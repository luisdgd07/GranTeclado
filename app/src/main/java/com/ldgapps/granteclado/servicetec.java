package com.ldgapps.granteclado;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Toast;

/**
 * Created by Gonzalez Duerto on 1/12/2018.
 */

public class servicetec extends InputMethodService implements KeyboardView.OnKeyboardActionListener{
    private  KeyboardView kv;
    private Keyboard simbol,qwerty,nume,phone,numero;
    private int num=0;
    private Vibrator vibra;
    private boolean caps=false;
    @Override
    public View onCreateInputView() {
        kv= (KeyboardView) getLayoutInflater().inflate(R.layout.keyboardview,null);
        qwerty=new Keyboard(this,R.xml.qwerty);
        nume=new Keyboard(this,R.xml.numer);
        phone=new Keyboard(this,R.xml.phone);
        numero=new Keyboard(this,R.xml.numeros);
        simbol=new Keyboard(this,R.xml.simbolos);

        if(num==1){
            onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
            kv.setKeyboard(nume);
            kv.setOnKeyboardActionListener(this);
        }
        else if(num==0){
            onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
            kv.setKeyboard(qwerty);
            kv.setOnKeyboardActionListener(this);
        }
        else if(num==2){
            onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
            kv.setKeyboard(simbol);
            kv.setOnKeyboardActionListener(this);
        }
        kv.setLongClickable(true);

        return kv;
    }
    @Override
    public View onCreateCandidatesView() {
        return super.onCreateCandidatesView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.isLongPress()){
            InputConnection ic = getCurrentInputConnection();

            switch(keyCode){
                case -1:
                    vibra.vibrate(50);
                    kv.setShifted(true);
                    caps=true;
                    break;
                case 117:
                    vibra.vibrate(50);
                    ic.commitText("ú",1);
                    break;
                case 105:
                    vibra.vibrate(50);
                    ic.commitText("í",1);
                    break;
                case 111:
                    vibra.vibrate(50);
                    ic.commitText("ó",1);
                    break;
                case 97:
                    vibra.vibrate(50);
                    ic.commitText("á",1);
                    break;
                case 101:
                    vibra.vibrate(50);
                    ic.commitText("é",1);
                    break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        return super.onKeyLongPress(keyCode, event);
    }


    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {

        InputConnection ic = getCurrentInputConnection();
        onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
        if(info.inputType == InputType.TYPE_CLASS_PHONE){
            restarting=true;
            onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
            kv.setKeyboard(phone);
        }
        else if(info.inputType == InputType.TYPE_CLASS_NUMBER|| info.inputType==InputType.TYPE_CLASS_DATETIME){
            restarting=true;
            onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
            kv.setKeyboard(numero);
        }
        //524449   17   3  1   8289   589825    180225
/*        else if(info.inputType==524449||info.inputType==17){
            restarting=true;
            if(num==1){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
                kv.setKeyboard(nume);
            }
            else if(num==0){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0, 0, "", "", "", true, true));
                kv.setKeyboard(qwerty);
                String currentText = (String) ic.getExtractedText(new ExtractedTextRequest(), 0).text;
                if(currentText.isEmpty()){
                    kv.setShifted(true);
                }
                else{
                    kv.setShifted(false);
                }

            }
            else if(num==2){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0, 0, "", "", "", true, true));
                kv.setKeyboard(simbol);
            }
        }*/
        else {
            restarting=true;
            if(num==1){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
                kv.setKeyboard(nume);
            }
            else if(num==0){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0, 0, "", "", "", true, true));
                kv.setKeyboard(qwerty);
            }
            else if(num==2){
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0, 0, "", "", "", true, true));
                kv.setKeyboard(simbol);
            }

        }
        super.onStartInputView(info, restarting);
    }

/*    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        if (mComposing.length() > 0 ) {
            mComposing.setLength(0);
            //updateCandidates();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }*/
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        EditorInfo info=getCurrentInputEditorInfo();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                try {
                        String currentText = (String) ic.getExtractedText(new ExtractedTextRequest(), 0).text;
                        CharSequence seleted = ic.getSelectedText(0);
                        if (TextUtils.isEmpty(seleted)) {
                            ic.deleteSurroundingText(1, 0);
                            currentText = (String) ic.getExtractedText(new ExtractedTextRequest(), 0).text;
                            if (currentText.isEmpty()) {
                                kv.setShifted(true);
                            }
                        } else {
                            getCurrentInputConnection().commitText("", 1);
                            if (currentText.isEmpty()) {
                                kv.setShifted(true);
                            }
                        }

                }catch(Exception e){
                    ic.deleteSurroundingText(1, 0);
                }
                break;
            case Keyboard.KEYCODE_SHIFT:
                if(kv.isShifted()){
                    caps=false;
                    kv.setShifted(false);
                }
                else{
                    kv.setShifted(true);
                }
                break;
            case Keyboard.KEYCODE_DONE:
/*                if(info.inputType==524449||info.inputType==17){
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_INSERT));
                }
                else{*/
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                //}
                break;
            case -100:
                if(num==0){
                    num=1;
                }
                else{
                    num=0;
                }
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
                if(num==1){
                    kv.setKeyboard(nume);
                    kv.setOnKeyboardActionListener(this);
                }
                else if(num==0){
                    kv.setKeyboard(qwerty);
                    kv.setOnKeyboardActionListener(this);
                }
                break;
            case Keyboard.EDGE_LEFT:
                ic.commitText("",-1);
                break;
            case Keyboard.EDGE_RIGHT:
                ic.commitText("",2);
                break;
            case -2:
                ic.commitText("?",1);
                break;
            case -151:
                ic.commitText(".",1);
                break;
            case -152:
                ic.commitText(",",1);
                break;
            case -153:
                ic.commitText("¿",1);
                break;
            case -150:
                ic.commitText("ñ",1);
                break;
            case -99:
                onCurrentInputMethodSubtypeChanged(new InputMethodSubtype(0,0,"","","",true,true));
                if(num==1){
                    num=2;
                }
                else{
                    num=1;
                }
                if(num==1){
                    kv.setKeyboard(nume);
                    kv.setOnKeyboardActionListener(this);
                }
                else if(num==2){
                    kv.setKeyboard(simbol);
                    kv.setOnKeyboardActionListener(this);
                }
                break;
            /*case 117:
                if(onLongClick(kv)){
                vibra.vibrate(50);
                ic.commitText("ú",1);}
                else{
                    ic.commitText("u",1);
                }
                break;
            case 105:
                if(onLongClick(kv)){
                    vibra.vibrate(50);
                ic.commitText("í",1);}
                else{
                    ic.commitText("i",1);
                }
                break;
            case 111:
                if(onLongClick(kv)){
                vibra.vibrate(50);
                ic.commitText("ó",1);}
                else{
                    ic.commitText("o",1);
                }
                break;
            case 97:
                kv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        InputConnection ic = getCurrentInputConnection();
                        ic.commitText("á",1);
                        return false;
                    }
                });
                kv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InputConnection ic = getCurrentInputConnection();
                        ic.commitText("a",1);
                    }
                });
                if(kv.setOnClickListener(new)){
                vibra.vibrate(50);
                ic.commitText("á",1);}
                else{
                    ic.commitText("a",1);
                }
                break;
           case 101:
                if(onLongClick(kv)){
                    vibra.vibrate(50);
                    ic.commitText("é",1);
                }
                else{
                    ic.commitText("e",1);
                }
                break;*/
            default:

                char code = (char)primaryCode;
                onKeyDown(code,new KeyEvent(KeyEvent.ACTION_MULTIPLE,KeyEvent.KEYCODE_INSERT));
                if(Character.isLetter(code) && kv.isShifted()){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
                if(kv.isShifted()){
                    kv.setShifted(false);
                }
        }
    }
/*    private void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
    }*/


    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
        //Toast.makeText(getApplicationContext(),"abajo",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeLeft() {
        //Toast.makeText(getApplicationContext(),"izquierda",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeRight() {
        //Toast.makeText(getApplicationContext(),"derecha",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeUp() {
        EditorInfo e=getCurrentInputEditorInfo();
        Toast.makeText(getApplicationContext(), e.inputType+"  " ,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"arriba",Toast.LENGTH_SHORT).show();
    }

}
