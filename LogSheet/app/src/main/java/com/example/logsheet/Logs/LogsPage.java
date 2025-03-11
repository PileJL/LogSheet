package com.example.logsheet.Logs;

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

        // overlay onclick
        binding.overlay.setOnClickListener(v -> binding.overlay.setVisibility(View.GONE));

        // hamburger icon onclick
        binding.hamburgerIcon.setOnClickListener(v -> binding.overlay.setVisibility(View.VISIBLE));

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

    }

    private void goToHomePage() {
        Utility.navigateToActivity(this, new Intent(this, HomeActivity.class));
        finish();
    }


    @Override
    public void onItemClicked(LogsItem item) {
        Utility.navigateToActivity(this, new Intent(this, WeekLogs.class));
    }
}