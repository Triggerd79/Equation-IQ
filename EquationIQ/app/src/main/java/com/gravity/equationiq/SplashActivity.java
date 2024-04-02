package com.gravity.equationiq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        // Delay for 2 seconds and then start the main activity
        new Handler().postDelayed(() -> {
            // Start the main activity after the delay
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish(); // Finish the splash screen activity to prevent going back to it on back press
        }, 2000);
    }
}