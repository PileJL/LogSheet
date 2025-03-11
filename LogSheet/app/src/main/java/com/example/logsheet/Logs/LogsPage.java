package com.example.logsheet.Logs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.example.logsheet.DayLogs.DayLogsPage;
import com.example.logsheet.HomeActivity;
import com.example.logsheet.ProfileActivity;
import com.example.logsheet.R;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.WeekLogs;
import com.example.logsheet.databinding.ActivityLogsPageBinding;

import java.util.ArrayList;
import java.util.List;

public class LogsPage extends AppCompatActivity implements LogsSelectListener{

    ActivityLogsPageBinding binding;

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
        binding.emoticonNextButton.setOnClickListener(v -> displayAddLogForm());

        // addLog button onclick
        binding.addLogButton.setOnClickListener(v -> addLog());

        List<LogsItem> items = new ArrayList<LogsItem>();
        items.add(new LogsItem("February 2021 - Week 1", "Highly Active"));
        items.add(new LogsItem("February 2021 - Week 2", "Inactive"));
        items.add(new LogsItem("February 2021 - Week 1", "Low Activity"));
        items.add(new LogsItem("February 2021 - Week 2", "Moderate Activity"));
        items.add(new LogsItem("February 2021 - Week 1", "Highly Active"));
        items.add(new LogsItem("February 2021 - Week 2", "Inactive"));
        items.add(new LogsItem("February 2021 - Week 1", "Low Activity"));
        items.add(new LogsItem("February 2021 - Week 2", "Moderate Activity"));
        items.add(new LogsItem("February 2021 - Week 1", "Highly Active"));
        items.add(new LogsItem("February 2021 - Week 2", "Inactive"));
        items.add(new LogsItem("February 2021 - Week 1", "Low Activity"));
        items.add(new LogsItem("February 2021 - Week 2", "Moderate Activity"));
        items.add(new LogsItem("February 2021 - Week 1", "Highly Active"));
        items.add(new LogsItem("February 2021 - Week 2", "Inactive"));
        items.add(new LogsItem("February 2021 - Week 1", "Low Activity"));
        items.add(new LogsItem("February 2021 - Week 2", "Moderate Activity"));
        items.add(new LogsItem("February 2021 - Week 22", "Active"));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LogsAdapter(this, items, this));

        setUpSpinner();

    }

    private void addLog() {

    }

    private void displayAddLogForm() {
        binding.formTitle.setText("Add Log"); // change form title
        binding.emotionContainer.setVisibility(View.GONE); // hide emoticons
        // change navigation lines colors
        binding.line1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        binding.line2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.main_yellow));
        // display backbutton
        binding.addLogBackButton.setVisibility(View.VISIBLE);

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

}