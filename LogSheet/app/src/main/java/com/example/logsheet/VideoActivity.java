package com.example.logsheet;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logsheet.Utilities.Utility;
import com.example.logsheet.databinding.ActivityVideoBinding;

import java.util.HashMap;

public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
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
                goBacktoHome();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // backbuton onclick
        binding.backButton.setOnClickListener(v -> {
            goBacktoHome();
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get intent
        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");

        // setup videos
        HashMap<String, Integer> vidoes = new HashMap<>();
        vidoes.put("cycling", R.raw.cycling);
        vidoes.put("dancing", R.raw.dancing);
        vidoes.put("jogging", R.raw.jogging);
        vidoes.put("jumping jacks", R.raw.jumping_jacks);
        vidoes.put("walking", R.raw.walking);
        vidoes.put("push up", R.raw.push_up);
        vidoes.put("planking", R.raw.plank);
        vidoes.put("bicycle crunches", R.raw.bicycle_crunch);
        vidoes.put("high knees", R.raw.high_knees);
        vidoes.put("wall sits", R.raw.wall_sit);
        vidoes.put("chair dips", R.raw.chair_dips);

        VideoView videoView = findViewById(R.id.video_view);
        String video_path = "android.resource://" + getPackageName() + "/" + vidoes.get(activity);
        Uri uri = Uri.parse(video_path);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.start());
    }

    private void goBacktoHome() {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }
}