package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.Utilities.LogDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityAssessmentPageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AssessmentPage extends AppCompatActivity {

    ActivityAssessmentPageBinding binding;
    LogDBHelper logDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAssessmentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // This callback will intercept the back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToLogsPage();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        // initializations
        logDBHelper = new LogDBHelper(this);

        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goBackToLogsPage();
        });

        // submitButton onClick
        binding.submitButton.setOnClickListener(v -> addLog());
    }

    private void addLog() {
        // check if all fields are filled
        if (!Utility.allTextInputEditTextAreFilled(new ArrayList<>(Arrays.asList(binding.field1, binding.field2, binding.field3, 
                binding.field4, binding.field5, binding.field6, binding.field7)))) {
            Toast.makeText(this, "Please answer all the questions.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Get current month as an integer (1 = January, 2 = February, ..., 12 = December)
        int month = calendar.get(Calendar.MONTH) + 1;

        // Get current year (e.g., "2025")
        int year = calendar.get(Calendar.YEAR);

        // Get week of the month (1, 2, 3, etc.)
        int monthWeek = Math.min(calendar.get(Calendar.WEEK_OF_MONTH), 3); // Limit to 1, 2, or 3

        // Get day of the week (Monday = 1, Sunday = 7)
        int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7;

        if (dayOfWeek == 0) {
            dayOfWeek = 7; // Sunday should be 7
        }

        // Get the current date in "yyyy-MM-dd" format
        String dateTimeAdded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.getTime());

        // Insert log into database
        long result = logDBHelper.insertLog( Utility.getUserId(getApplicationContext()),
                LogsPage.feeling, LogsPage.activityDesc, LogsPage.hourDuration, LogsPage.minuteDuration, LogsPage.intensity,
                binding.field1.getText().toString(), binding.field2.getText().toString(), binding.field3.getText().toString(),
                binding.field4.getText().toString(), binding.field5.getText().toString(), binding.field6.getText().toString(),
                binding.field7.getText().toString(),
                month, year, monthWeek, dayOfWeek, dateTimeAdded
        );
//        long result = logDBHelper.insertLog( "feeling", "activityDesc", 1, 30, "intensity",
//                "yes", "no", "yes", "no", "yes", "no" ,"yes",
//                1, 2026, 1, 1, "dateAdded"
//
//        );
        // check if log is added successfully
        if (result != -1) {
            Toast.makeText(this, "Log added successfully.", Toast.LENGTH_SHORT).show();
            // go back to logs page
            Utility.navigateToActivity(this, new Intent(this, LogsPage.class));
            finish();
        }
        else {
            Toast.makeText(this, "Failed to add log.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goBackToLogsPage() {
        Utility.navigateToActivity(this, new Intent(this, LogsPage.class));
        finish();
    }
}