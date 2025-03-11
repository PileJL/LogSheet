package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.DayLogs.DayLogsPage;
import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityLoginBinding;
import com.example.logsheet.databinding.ActivityWeekLogsBinding;

public class WeekLogs extends AppCompatActivity {

    ActivityWeekLogsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWeekLogsBinding.inflate(getLayoutInflater());
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
                goToLogsPage();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // backButton onclick
        binding.backButton.setOnClickListener(v -> goToLogsPage());

        // day logs onclicks
        binding.day1.setOnClickListener(v -> viewDayLogs("Day 1"));
        binding.day2.setOnClickListener(v -> viewDayLogs("Day 2"));
        binding.day3.setOnClickListener(v -> viewDayLogs("Day 3"));
        binding.day4.setOnClickListener(v -> viewDayLogs("Day 4"));
        binding.day5.setOnClickListener(v -> viewDayLogs("Day 5"));
        binding.day6.setOnClickListener(v -> viewDayLogs("Day 6"));
        binding.day7.setOnClickListener(v -> viewDayLogs("Day 7"));

    }

    private void viewDayLogs(String day) {
        Intent intent = new Intent(this, DayLogsPage.class);
        intent.putExtra("day", day);
        Utility.navigateToActivity(this, intent);
        finish();
    }

    private void goToLogsPage() {
        Utility.navigateToActivity(this, new Intent(this, LogsPage.class));
        finish();
    }
}