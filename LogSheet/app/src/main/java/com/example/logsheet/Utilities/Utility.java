package com.example.logsheet.Utilities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.logsheet.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static void setIntensityColor(Context context, TextView textView, String intensity) {
        if (intensity.equalsIgnoreCase("Light")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else if (intensity.equalsIgnoreCase("Moderate")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }
        else if (intensity.equalsIgnoreCase("Vigorous")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    public static String calculateBMI(int age, double heightCm, double weightKg) {
        // Convert height from cm to meters
        double heightM = heightCm / 100.0;

        // Calculate BMI
        double bmi = weightKg / (heightM * heightM);

        // Determine BMI Category
        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            category = "Normal";
        } else if (bmi >= 25 && bmi < 29.9) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        // Format result (1 decimal place)
        return String.format("%.1f (%s)", bmi, category);
    }

    public static void setActivenessColor(Context context, TextView textView, String activeness) {
        if (activeness.equalsIgnoreCase("Highly Active")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else if (activeness.equalsIgnoreCase("Inactive")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else if (activeness.equalsIgnoreCase("Low Activity")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else if (activeness.equalsIgnoreCase("Moderate Activity")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }
    }

    public static ArrayList<String> getRandomExercises() {
        ArrayList<String> exercises = new ArrayList<>(Arrays.asList(
                "Push-ups", "Sit-ups", "Squats", "Lunges", "Burpees", "Plank",
                "Mountain Climbers", "Jump Rope", "High Knees", "Bicycle Crunches", "Plank Hold"
        ));
        Collections.shuffle(exercises); // Shuffle the list to randomize
        return new ArrayList<>(Arrays.asList(exercises.get(0), exercises.get(1)));
    }

    public static String getRandomTrivia() {
        ArrayList<String> trivias = new ArrayList<>(Arrays.asList(
                "Muscles grow when they repair microscopic tears caused by exercise.",
                "Lifting weights can increase your metabolism even after your workout is done.",
                "Running burns about 50% more calories per mile than cycling.",
                "A pound of muscle burns about three times more calories than a pound of fat.",
                "Strength training can improve bone density and reduce the risk of osteoporosis.",
                "Your body burns more calories digesting protein than it does digesting fats or carbohydrates.",
                "Stretching can improve your flexibility and prevent injuries.",
                "The more muscle mass you have, the more calories you burn while resting.",
                "Drinking cold water can slightly boost your metabolism as your body works to heat it up.",
                "Regular exercise can help improve your mood and reduce stress by releasing endorphins.",
                "The gluteus maximus is the largest muscle in the body, located in the buttocks and responsible for hip extension."
        ));
        Collections.shuffle(trivias); // Shuffle the list to randomize
        return trivias.get(0);
    }

}
