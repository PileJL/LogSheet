package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Utilities.UserDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private UserDBHelper userDbHelper;
    String username, password, gender;
    int age, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
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
        // initializations
        userDbHelper = new UserDBHelper(this);

        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goToLoginPage();
        });

        // signUp button onclick
        binding.signUpButton.setOnClickListener(v -> signUpUser());

    }

    private void signUpUser() {
        if (validSignUpDetails()) {
            // check if username exists
            if (userDbHelper.isUsernameExists(username)) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
                return;
            }
            // else, insert user to database
            long result = userDbHelper.insertUser(username, password, gender, age, height, weight);
            if (result != -1) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                Utility.emptyTextInputEditTexts(new ArrayList<>(Arrays.asList(binding.username, binding.password,
                        binding.confirmPassword, binding.gender, binding.age, binding.height, binding.weight)));
            }
            else {
                Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLoginPage() {
        Utility.navigateToActivity(this, new Intent(this, LoginActivity.class));
        finish();
    }

    private boolean validSignUpDetails(){
        try {
            username = Utility.getTextInputEditTextValue(binding.username);
            password = Utility.getTextInputEditTextValue(binding.password);
            String confirmPassword = Utility.getTextInputEditTextValue(binding.confirmPassword);
            gender = Utility.getTextInputEditTextValue(binding.gender);
            age = Integer.parseInt(Utility.getTextInputEditTextValue(binding.age));
            height = Integer.parseInt(Utility.getTextInputEditTextValue(binding.height));
            weight = Integer.parseInt(Utility.getTextInputEditTextValue(binding.weight));

            return !username.isEmpty() && !password.isEmpty() && password.equals(confirmPassword) && !gender.isEmpty()
                    && age > 0 && height > 0 && weight > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
}