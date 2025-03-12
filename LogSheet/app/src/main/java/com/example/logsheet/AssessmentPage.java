package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityAssessmentPageBinding;

public class AssessmentPage extends AppCompatActivity {

    ActivityAssessmentPageBinding binding;

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

        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goBackToLogsPage();
        });
    }

    private void goBackToLogsPage() {
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}