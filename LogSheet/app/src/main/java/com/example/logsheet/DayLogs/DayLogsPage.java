package com.example.logsheet.DayLogs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.logsheet.LogDetailsPage;
import com.example.logsheet.R;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.WeekLogs;
import com.example.logsheet.databinding.ActivityDayLogsBinding;

import java.util.ArrayList;
import java.util.List;

public class DayLogsPage extends AppCompatActivity implements DayLogsSelectListener{

    ActivityDayLogsBinding binding;

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

        // backButton onclick
        binding.backButton.setOnClickListener(v -> goToWeekLogs());

        // get day
        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        binding.day.setText(day);

        List<DayLogsItem> items = new ArrayList<DayLogsItem>();
        items.add(new DayLogsItem("Jogging", "Light", "1h 30m"));
        items.add(new DayLogsItem("Running", "Moderate", "1h 30m"));
        items.add(new DayLogsItem("Boxing", "Vigorous", "1h 30m"));
        items.add(new DayLogsItem("Jogging", "Light", "1h 30m"));
        items.add(new DayLogsItem("Running", "Moderate", "1h 30m"));
        items.add(new DayLogsItem("Boxing", "Vigorous", "1h 30m"));
        items.add(new DayLogsItem("Jogging", "Light", "1h 30m"));
        items.add(new DayLogsItem("Running", "Moderate", "1h 30m"));
        items.add(new DayLogsItem("Boxing", "Vigorous", "1h 30m"));
        items.add(new DayLogsItem("Jogging", "Light", "1h 30m"));
        items.add(new DayLogsItem("Running", "Moderate", "1h 30m"));
        items.add(new DayLogsItem("Boxing", "Vigorous", "1h 30m"));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new DayLogsAdapter(this, items, this));


    }

    private void goToWeekLogs() {
        Utility.navigateToActivity(this, new Intent(this, WeekLogs.class));
        finish();
    }

    @Override
    public void onItemClicked(DayLogsItem item) {
        Utility.navigateToActivity(this, new Intent(this, LogDetailsPage.class));
    }
}