package com.example.logsheet.Logs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.logsheet.AssessmentPage;
import com.example.logsheet.DayLogs.DayLogsPage;
import com.example.logsheet.HomeActivity;
import com.example.logsheet.LoginActivity;
import com.example.logsheet.ProfileActivity;
import com.example.logsheet.R;
import com.example.logsheet.Utilities.LogDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.WeekLogs;
import com.example.logsheet.databinding.ActivityLogsPageBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogsPage extends AppCompatActivity implements LogsSelectListener{

    ActivityLogsPageBinding binding;
    public static String feeling, activityDesc, intensity;
    public static int hourDuration, minuteDuration;
    int userId;
    LogDBHelper dbHelper;
    List<LogsItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLogsPageBinding.inflate(getLayoutInflater());
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
                goToHomePage();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        // initializations
        userId = Utility.getUserId(getApplicationContext());
        dbHelper = new LogDBHelper(this);

        // sideNavOverlay onclick
        binding.sideNavOverlay.setOnClickListener(v -> binding.sideNavOverlay.setVisibility(View.GONE));

        // hamburger icon onclick
        binding.hamburgerIcon.setOnClickListener(v -> binding.sideNavOverlay.setVisibility(View.VISIBLE));

        // sidenav onclick
        binding.sideNav.setOnClickListener(v -> {});

        // home onclick
        binding.home.setOnClickListener(v -> {
            Utility.navigateToActivity(this, new Intent(this, HomeActivity.class));
            finish();
        });

        // profile onclick
        binding.profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("backActivity", LogsPage.class.getName());
            Utility.navigateToActivity(this, intent);
            finish();
        });

        // emoticonNextButton onclick
        binding.formButton.setOnClickListener(v -> formButtonOnClick());

        setUpSpinner();

        // addLog button onclick
        binding.addLogButton.setOnClickListener(v -> addLog());

        // overlayBG onclick
        binding.addLogOverlay.setOnClickListener(v -> binding.addLogOverlay.setVisibility(View.GONE));

        // addLog popup onclick
        binding.addLogContainer.setOnClickListener(v -> {});

        // addLog backButton onclick
        binding.addLogBackButton.setOnClickListener(v -> displayAddLogPage1());

        // logOut button onclick
        binding.logout.setOnClickListener(v -> logOut());

        // emoticons onClick
        binding.sad.setOnClickListener(this::emoticonsOnClick);
        binding.happy.setOnClickListener(this::emoticonsOnClick);
        binding.tired.setOnClickListener(this::emoticonsOnClick);
        binding.loved.setOnClickListener(this::emoticonsOnClick);
        binding.sleepy.setOnClickListener(this::emoticonsOnClick);
        binding.awkward.setOnClickListener(this::emoticonsOnClick);
        binding.strong.setOnClickListener(this::emoticonsOnClick);
        binding.angry.setOnClickListener(this::emoticonsOnClick);


        // Get all unique dates
        ArrayList<HashMap<String, String>> uniqueDates = dbHelper.getUniqueDates(Utility.getUserId(getApplicationContext()));

        // add all unique dates to recyclerview items
        items = new ArrayList<>();
        // Loop through each HashMap in the ArrayList
        if (!uniqueDates.isEmpty()) {
            binding.noLogsText.setVisibility(View.GONE);
            for (HashMap<String, String> date : uniqueDates) {
                int year = Integer.parseInt(date.get("year"));
                int month = Integer.parseInt(date.get("month"));
                int week = Integer.parseInt(date.get("week"));
                items.add(new LogsItem(dbHelper.getActivityLevel(userId, year, month, week),
                        year, month, week));
            }
        }
        else {
            binding.noLogsText.setVisibility(View.VISIBLE);
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LogsAdapter(this, items, this));

    }

    private void logOut() {
        Utility.logOutUser(getApplicationContext()); // change data in shared preferences
        Utility.navigateToActivity(this, new Intent(this, LoginActivity.class));
        finish();
    }

    private void addLog() {
        binding.addLogOverlay.setVisibility(View.VISIBLE);
        displayAddLogPage1();
    }

    private void goToHomePage() {
        Utility.navigateToActivity(this, new Intent(this, HomeActivity.class));
        finish();
    }

    private void setUpSpinner() {
        // Define the items
        String[] items = {"Light", "Moderate", "Vigorous"};

        // Get the custom font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.inter_regular);

        // Create a custom adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(10);  // Set text size
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));  // Set text color
                textView.setTypeface(typeface); // Apply the font
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextSize(10);
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                textView.setTypeface(typeface); // Apply the font
                return textView;
            }
        };

        // Set the adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(LogsItem item) {
        Utility.navigateToActivity(this, new Intent(this, WeekLogs.class));
    }

    @Override
    public void onDeleteClicked(LogsItem item, Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete these logs? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the logs from the database
                    boolean isDeleted = dbHelper.deleteLogs(userId, item.getYear(), item.getMonth(), item.getWeek());
                    if (isDeleted) {
                        Toast.makeText(context, "Logs deleted successfully!", Toast.LENGTH_SHORT).show();
                        recreate();
                    } else {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void displayAddLogPage1() {
        binding.formButton.setVisibility(View.GONE);
        binding.addLogBackButton.setVisibility(View.GONE); // hide backButton
        binding.formTitle.setText("How are you feeling\ntoday?"); // change form title
        binding.emotionContainer.setVisibility(View.VISIBLE); // display emoticons
        // change navigation lines colors
        binding.line1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        binding.line2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        // hide physical activity form
        binding.activityFormContainer.setVisibility(View.GONE);
    }

    private void emoticonsOnClick(View v) {
            binding.addLogBackButton.setVisibility(View.VISIBLE); // display backButton
            binding.formButton.setVisibility(View.VISIBLE); // display formButton
            binding.formTitle.setText("Add Log"); // change form title
            binding.emotionContainer.setVisibility(View.GONE); // hide emoticons
            // change navigation lines colors
            binding.line1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.line2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
            // hide physical activity form
            binding.activityFormContainer.setVisibility(View.VISIBLE);
            // set feeling
            feeling = getResources().getResourceEntryName(v.getId());;
    }
    private void formButtonOnClick() {
        activityDesc = binding.actDesc.getText().toString();
        hourDuration = Integer.parseInt(binding.hour.getText().toString());
        minuteDuration = Integer.parseInt(binding.minute.getText().toString());
        intensity = binding.spinner.getSelectedItem().toString();
        // check if all fields are field
        if (activityDesc.isEmpty() || hourDuration < 0 || minuteDuration < 0 || minuteDuration > 59) {
            Toast.makeText(this, "Please enter a valid information.", Toast.LENGTH_SHORT).show();
            return;
        }

        Utility.navigateToActivity(this, new Intent(this, AssessmentPage.class));
        finish();
    }
}