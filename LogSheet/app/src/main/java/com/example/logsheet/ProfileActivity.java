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

import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
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
                goToLoginPage();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goToLoginPage();
        });

        //edit icon onclick
        binding.editIcon.setOnClickListener(v -> {
            binding.saveButton.setVisibility(View.VISIBLE);// display save button
            // enable fields
            Utility.enableTextInputEditTexts(new ArrayList<>(Arrays.asList(binding.username, binding.gender, binding.age, binding.height, binding.weight)));
            // set focus to first field
            binding.username.requestFocus();
            binding.username.setSelection(binding.username.getText().length());
        });

        // saveButton onclick
        binding.saveButton.setOnClickListener(v -> saveProfile());

    }

    private void goToLoginPage() {
        finish();
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
    }

    private void saveProfile() {
        binding.saveButton.setVisibility(View.GONE);
        Utility.disableTextInputEditTexts(new ArrayList<>(Arrays.asList(binding.username, binding.gender, binding.age, binding.height, binding.weight)));
    }
}