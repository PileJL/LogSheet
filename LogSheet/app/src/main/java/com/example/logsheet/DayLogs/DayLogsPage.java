package com.example.logsheet.DayLogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.logsheet.LogDetailsPage;
import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.R;
import com.example.logsheet.Utilities.LogDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.WeekLogs;
import com.example.logsheet.databinding.ActivityDayLogsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayLogsPage extends AppCompatActivity implements DayLogsSelectListener{

    ActivityDayLogsBinding binding;
    String month, year, week, day;
    LogDBHelper dbHelper;
    List<DayLogsItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDayLogsBinding.inflate(getLayoutInflater());
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
                goToWeekLogs();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        // declarations
        dbHelper = new LogDBHelper(this);
        items = new ArrayList<>();
        // backButton onclick
        binding.backButton.setOnClickListener(v -> goToWeekLogs());
        // activity view close button onclick
        binding.closeActivityViewIcon.setOnClickListener(v -> binding.logViewOverlay.setVisibility(View.GONE));

        // get log details day
        month = LogsPage.month;
        year = LogsPage.year;
        week = LogsPage.weekOfMonth;
        day = WeekLogs.staticDay;

        // set page title
        binding.pageTitle.setText(Utility.getMonthName(Integer.parseInt(month)) + " " + year + " - Week " + week);
        binding.day.setText("Day " + day);

        // get day logs
        ArrayList<HashMap<String, String>> logs;
        logs = dbHelper.getActivityLogs(Utility.getUserId(getApplicationContext()), Integer.parseInt(year),
                Integer.parseInt(month), Integer.parseInt(week), Integer.parseInt(day));

        if (!logs.isEmpty()) {
            binding.noLogsText.setVisibility(View.GONE);
            // add day logs to recyclerview item
            for (HashMap<String, String> log : logs) {
                // Print to Logcat
                items.add(new DayLogsItem(log.get("id"), log.get("userId"), log.get("feeling"), log.get("activityDesc"), log.get("hourDuration"), log.get("minuteDuration"),
                        log.get("intensity"), log.get("questionAnswer1"), log.get("questionAnswer2"), log.get("questionAnswer3"), log.get("questionAnswer4"),
                        log.get("questionAnswer5"), log.get("questionAnswer6"), log.get("questionAnswer7")));
            }
        }
        else {
            binding.noLogsText.setVisibility(View.VISIBLE);
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new DayLogsAdapter(this, items, this));


    }

    private void goToWeekLogs() {
        Utility.navigateToActivity(this, new Intent(this, WeekLogs.class));
        finish();
    }

    @Override
    public void onItemClicked(DayLogsItem item) {
        // set log view overlay data
        binding.title.setText(item.getActivityDesc());
        binding.intensity.setText(item.getIntensity());
        binding.duration.setText(item.getDuration());
        binding.feeling.setText(item.getFeeling());
        binding.question1.setText(item.getQuestionAnswer1());
        binding.question2.setText(item.getQuestionAnswer2());
        binding.question3.setText(item.getQuestionAnswer3());
        binding.question4.setText(item.getQuestionAnswer4());
        binding.question5.setText(item.getQuestionAnswer5());
        binding.question6.setText(item.getQuestionAnswer6());
        binding.question7.setText(item.getQuestionAnswer7());
        // set intesity color
        Utility.setIntensityColor(this, binding.intensity, item.getIntensity());
        // show log view overlay
        binding.logViewOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDeleteClicked(DayLogsItem item, Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this log? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the log from the database
                    boolean isDeleted = dbHelper.deleteLogById(item.getId());
                    if (isDeleted) {
                        Toast.makeText(context, "Log deleted successfully.", Toast.LENGTH_SHORT).show();
                        recreate();
                    } else {
                        Toast.makeText(context, "Deletion unsuccessful.", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}