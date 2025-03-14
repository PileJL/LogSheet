package com.example.logsheet;

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

import com.example.logsheet.Models.User;
import com.example.logsheet.Utilities.UserDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    Class<?> backActivity;
    User loggedInUser;
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
                goBackToPreviousPage();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        // initializations
        loggedInUser = LoginActivity.loggedInUser;

        // set user data
        binding.headerUsername.setText(loggedInUser.getUsername());
        binding.username.setText(loggedInUser.getUsername());
        binding.gender.setText(loggedInUser.getGender());
        binding.age.setText(String.valueOf(loggedInUser.getAge()));
        binding.height.setText(String.valueOf(loggedInUser.getHeight()));
        binding.weight.setText(String.valueOf(loggedInUser.getWeight()));
        binding.bmiInterpretation.setText(Utility.calculateBMI(loggedInUser.getAge(), loggedInUser.getHeight(), loggedInUser.getWeight()));


        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goBackToPreviousPage();
        });

        Intent intent = getIntent();
        try {
            backActivity = Class.forName(intent.getStringExtra("backActivity"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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

    private void goBackToPreviousPage() {
        Utility.navigateToActivity(this, new Intent(this, backActivity));
        finish();
    }

    private void saveProfile() {
        Log.d("ProfileActivity", "saveProfile() called");
        UserDBHelper dbHelper = new UserDBHelper(this);
        boolean isUpdated = dbHelper.updateUserById(loggedInUser.getId(), binding.username.getText().toString()
                , binding.gender.getText().toString(), Integer.parseInt(binding.age.getText().toString()), 
                Float.parseFloat(binding.height.getText().toString()), Float.parseFloat(binding.weight.getText().toString()));
        
        if (isUpdated) {
            Toast.makeText(this, "User updated successfully.", Toast.LENGTH_SHORT).show();
            LoginActivity.loggedInUser = dbHelper.getUserById(loggedInUser.getId());
            recreate();
        } else {
            Toast.makeText(this, "User update failed.", Toast.LENGTH_SHORT).show();
        }

        
        binding.saveButton.setVisibility(View.GONE);
        Utility.disableTextInputEditTexts(new ArrayList<>(Arrays.asList(binding.username, binding.gender, binding.age, binding.height, binding.weight)));
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}