package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds, adjust as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent); // Start the LoginActivity
                finish();// Close the SplashActivity to prevent going back to it
            }
        }, SPLASH_TIMEOUT);     // Set the delay time before navigating to the next activity
    }
}