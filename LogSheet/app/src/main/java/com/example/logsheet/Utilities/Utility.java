package com.example.logsheet.Utilities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

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

    public static String getTextInputEditTextValue(TextInputEditText field) {
        return field.getText().toString();
    }

    public static void emptyTextInputEditTexts(ArrayList<TextInputEditText> fields) {
        for (TextInputEditText field : fields) {
            field.setText("");
        }
    }
    public static void setLoginStatus(Context context, boolean status, int userId) {
        // Store login state in SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loggedIn", status);
        editor.putInt("id", userId);
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("loggedIn", false); // Default is false if key is not found
    }

    public static int getUserId (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", 0); // Default is false if key is not found
    }

    public static void logOutUser(Context context) {
        // Access SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Remove user session data
        editor.remove("id");  // Remove user ID
        editor.putBoolean("loggedIn", false);  // Set loggedIn to false
        editor.apply();
    }

    public static void scaleView(View view, float scaleX, float scaleY) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", scaleX);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", scaleY);

        scaleXAnimator.setDuration(100); // Duration of animation in milliseconds
        scaleYAnimator.setDuration(100);

        scaleXAnimator.start();
        scaleYAnimator.start();
    }

    public static boolean allTextInputEditTextAreFilled(ArrayList<TextInputEditText> fields) {
        for (TextInputEditText field: fields) {
            if (field.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    public static String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        if (month >= 1 && month <= 12) {
            return months[month - 1]; // Convert 1-based month to 0-based array index
        }
        return "Unknown"; // Handle invalid cases
    }
    public static boolean stringsAreNotBlack(ArrayList<String> strings) {
        for (String str: strings) {
            if (str.isBlank()) {
                return false;
            }
        }
        return true;
    }

}
