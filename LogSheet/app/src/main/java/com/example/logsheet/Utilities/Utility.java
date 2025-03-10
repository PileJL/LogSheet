package com.example.logsheet.Utilities;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Utility {

    public static void disableTextInputEditTexts(ArrayList<TextInputEditText> textInputEditTexts) {
        for ( TextInputEditText textInputEditText : textInputEditTexts) {
            textInputEditText.setEnabled(false);
        }
    }

    public static void enableTextInputEditTexts(ArrayList<TextInputEditText> textInputEditTexts) {
        for ( TextInputEditText textInputEditText : textInputEditTexts) {
            textInputEditText.setEnabled(true);
        }
    }
}
