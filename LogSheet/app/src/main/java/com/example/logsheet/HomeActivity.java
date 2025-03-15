package com.example.logsheet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Logs.LogsPage;
import com.example.logsheet.Models.User;
import com.example.logsheet.Utilities.LogDBHelper;
import com.example.logsheet.Utilities.UserDBHelper;
import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    ArrayList<ConstraintLayout> activityContainers;
    ArrayList<TextView> activityTitles;
    User loggedInUser = LoginActivity.loggedInUser;
    LogDBHelper logDBHelper;
    String activeness;
    int month, year, monthWeek;
    public static boolean popupsDone;
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
        activityContainers = new ArrayList<>(Arrays.asList(binding.activity1, binding.activity2, binding.activity3,
                binding.activity4, binding.activity5, binding.activity6, binding.activity7, binding.activity8,
                binding.activity9, binding.activity10, binding.activity11));
        activityTitles = new ArrayList<>(Arrays.asList(binding.activityTitle1, binding.activityTitle2, binding.activityTitle3,
                binding.activityTitle4, binding.activityTitle5, binding.activityTitle6, binding.activityTitle7, binding.activityTitle8,
                binding.activityTitle9, binding.activityTitle10, binding.activityTitle11));
        logDBHelper = new LogDBHelper(this);
            // Get the current date
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        monthWeek = Math.min(calendar.get(Calendar.WEEK_OF_MONTH), 3); // Limit to 1, 2, or 3
        activeness = logDBHelper.getActivityLevel(loggedInUser.getId(), year, month, monthWeek);
        // set activeness text
        binding.activeness.setText(activeness);
        // set activeness color
        Utility.setActivenessColor(getApplicationContext(), binding.activeness, activeness);

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
        // Get all unique dates
        LogDBHelper dbHelper = new LogDBHelper(this);
        ArrayList<HashMap<String, String>> uniqueDates = dbHelper.getUniqueDates(Utility.getUserId(getApplicationContext()));
        // display notif popup
        if (!LoginActivity.popupsDone && !uniqueDates.isEmpty()) {
            displayNotifPopup();
            LoginActivity.popupsDone = true;
        }
        else {
            binding.notifOverlay.setVisibility(View.GONE);
            binding.triviaOverlay.setVisibility(View.GONE);
        }

        // overlay onclick
        binding.overlay.setOnClickListener(v -> binding.overlay.setVisibility(View.GONE));

        //set trivia text
        binding.triviaMessage.setText(Utility.getRandomTrivia());

        // notif overlay onclick
        binding.notifOverlay.setOnClickListener(v -> {});
        // trivia overlay onclick
        binding.triviaOverlay.setOnClickListener(v -> {});

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

        // notifOkayButton onClick
        binding.notifOkayButton.setOnClickListener(v -> binding.notifOverlay.setVisibility(View.GONE));

        // triviaCloseButton onClick
        binding.triviaCloseButton.setOnClickListener(v -> binding.triviaOverlay.setVisibility(View.GONE));

        // set logOut button onClick
        binding.logout.setOnClickListener(v -> logOut());

        //activity onClicks
        binding.activity1.setOnClickListener(v -> activityOnClick("cycling"));
        binding.activity2.setOnClickListener(v -> activityOnClick("dancing"));
        binding.activity3.setOnClickListener(v -> activityOnClick("jogging"));
        binding.activity4.setOnClickListener(v -> activityOnClick("jumping jacks"));
        binding.activity5.setOnClickListener(v -> activityOnClick("walking"));
        binding.activity6.setOnClickListener(v -> activityOnClick("push up"));
        binding.activity7.setOnClickListener(v -> activityOnClick("planking"));
        binding.activity8.setOnClickListener(v -> activityOnClick("bicycle crunches"));
        binding.activity9.setOnClickListener(v -> activityOnClick("high knees"));
        binding.activity10.setOnClickListener(v -> activityOnClick("wall sits"));
        binding.activity11.setOnClickListener(v -> activityOnClick("chair dips"));

        // set username
        binding.pageHeader.setText("Hello, "+ loggedInUser.getUsername());

    }

    private void displayNotifPopup() {
        if (activeness.equalsIgnoreCase("Inactive") || activeness.equalsIgnoreCase("Low Activity")) {
             // get random exercises
            ArrayList<String> randomExercises = Utility.getRandomExercises();
            binding.notifOverlay.setVisibility(View.VISIBLE);
            HashMap<String, String> activenessDetails = logDBHelper.getActivenessDetails(loggedInUser.getId(), year, month, monthWeek);
            binding.notifMessage.setText(String.format("It's great to have you back!\nThe result of your physical " +
                    "activity activeness is that you spent %s engaging in Physical Activity, but unfortunately, you" +
                    " are still in %s state, doing only %s number of activities for a total of %s days. " +
                    "This week, you can try these exercises and increase the intensity " +
                    "to improve your physical activity, as this will help your endurance:",
                    activenessDetails.get("totalHours"), activeness, activenessDetails.get("totalActivities"), activenessDetails.get("daysLogged")));
            // set random exercises
            binding.suggestedPA1.setText(randomExercises.get(0));
            binding.suggestedPA2.setText(randomExercises.get(1));
        }
        else {
            binding.notifOverlay.setVisibility(View.GONE);
        }
    }

    private void logOut() {
        Utility.logOutUser(getApplicationContext()); // change data in shared preferences
        Utility.navigateToActivity(this, new Intent(this, LoginActivity.class));
        finish();
    }

    private void activityOnClick(String activity) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("activity", activity);
        Utility.navigateToActivity(this, intent);
    }


    private void filterActivities(String query, ArrayList<ConstraintLayout> activityContainers, ArrayList<TextView> activityTitles) {
        for (int i = 0; i < activityContainers.size(); i++) {
            ConstraintLayout activityContainer = activityContainers.get(i);
            TextView activityTitle = activityTitles.get(i);
            activityContainer.setVisibility(activityTitle.getText().toString().toLowerCase().contains(query) ? View.VISIBLE : View.GONE);
        }
    }
}