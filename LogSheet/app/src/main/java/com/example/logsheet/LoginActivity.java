package com.example.logsheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Models.User;
import com.example.logsheet.Utilities.UserDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    public static User loggedInUser;
    private UserDBHelper userDbHelper;
    SharedPreferences sharedPreferences;
    public static boolean popupsDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // initializations
        userDbHelper = new UserDBHelper(this);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        popupsDone = false;

        // check if user if logged
        if (Utility.isUserLoggedIn(this)) {
            // set loggedInUser details
            loggedInUser = userDbHelper.getUserById(sharedPreferences.getInt("id", -1));
            Utility.navigateToActivity(this, new Intent(this, HomeActivity.class));
            finish();
        }

        // signup text onclick
        binding.signupText.setOnClickListener(v -> {
            Utility.navigateToActivity(this, new Intent(this, SignupActivity.class));
            finish();
        });

        // login onclick
        binding.loginButton.setOnClickListener(v -> {
            logInUser();
        });
    }

    private void logInUser() {
        // Get input values
        String username = Utility.getTextInputEditTextValue(binding.username);
        String password = Utility.getTextInputEditTextValue(binding.password);

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check credentials
        User user = userDbHelper.logInUser(username, password);

        if (user != null) {
            // set user details
            loggedInUser = new User(user.getId(), user.getUsername(), user.getPassword(), user.getGender(),
                    user.getAge(), user.getHeight(), user.getWeight());
            // set shared preference
            Utility.setLoginStatus(getApplicationContext(), true, user.getId());
            // go to homepage
            Utility.navigateToActivity(this, new Intent(this, HomeActivity.class));
            finish();
        } else {
            // Invalid credentials
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}