package com.thiago.social;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView splashText, loadingText;
    private ProgressBar loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashText = findViewById(R.id.splash_text);
        loadingCircle = findViewById(R.id.loading_circle);
        loadingText = findViewById(R.id.loading_text);

        // Step 1: Show SOCIAL DOWNLOADS for 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashText.setVisibility(View.GONE);
                loadingCircle.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);

                // Step 2: Show loading circle for 3 seconds then go Home
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            }
        }, 2000);
    }
}