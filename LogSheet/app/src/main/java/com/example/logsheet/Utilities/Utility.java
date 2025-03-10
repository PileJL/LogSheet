package com.example.logsheet.Utilities;

import android.app.Activity;
import android.content.Intent;

import com.example.logsheet.R;
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

    public static void navigateToActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
