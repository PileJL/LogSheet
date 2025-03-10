package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    ArrayList<ConstraintLayout> activityContainers;
    ArrayList<TextView> activityTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // initializations
        activityContainers = new ArrayList<>(Arrays.asList(binding.activity1, binding.activity2, binding.activity3));
        activityTitles = new ArrayList<>(Arrays.asList(binding.activityTitle1, binding.activityTitle2, binding.activityTitle3));

        // searchbar ontextchanged listener
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim().toLowerCase(); // user input
                filterActivities(query, activityContainers, activityTitles);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // overlay onclick
        binding.overlay.setOnClickListener(v -> binding.overlay.setVisibility(View.GONE));

        // hamburger icon onclick
        binding.hamburgerIcon.setOnClickListener(v -> binding.overlay.setVisibility(View.VISIBLE));

        // sidenav onclick
        binding.sideNav.setOnClickListener(v -> {});

        // profile onclick
        binding.profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("backActivity", HomeActivity.class.getName());
            Utility.navigateToActivity(this, intent);
            finish();
        });

        // logs onclick
        binding.logs.setOnClickListener(v -> {
            Utility.navigateToActivity(this, new Intent(this, LogsPage.class));
            finish();
        });
    }



    private void filterActivities(String query, ArrayList<ConstraintLayout> activityContainers, ArrayList<TextView> activityTitles) {
        for (int i = 0; i < activityContainers.size(); i++) {
            ConstraintLayout activityContainer = activityContainers.get(i);
            TextView activityTitle = activityTitles.get(i);
            activityContainer.setVisibility(activityTitle.getText().toString().toLowerCase().contains(query) ? View.VISIBLE : View.GONE);
        }
    }
}