package com.thiago.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class PlatformActivity extends AppCompatActivity {

    String platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);

        platform = getIntent().getStringExtra("PLATFORM");
        ImageView logo = findViewById(R.id.platform_logo);
        Button btnOne = findViewById(R.id.btn_one);
        Button btnMany = findViewById(R.id.btn_many);
        Button btnBack = findViewById(R.id.btn_back);

        // Change logo depending on platform
        if(platform.equals("facebook")) logo.setImageResource(R.drawable.facebook_logo);
        if(platform.equals("instagram")) logo.setImageResource(R.drawable.instagram_logo);
        if(platform.equals("tiktok")) logo.setImageResource(R.drawable.tiktok_logo);
        if(platform.equals("youtube")) logo.setImageResource(R.drawable.youtube_logo);
        if(platform.equals("x")) logo.setImageResource(R.drawable.x_logo);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlatformActivity.this, SingleDownloadActivity.class);
                i.putExtra("PLATFORM", platform);
                startActivity(i);
            }
        });

        btnMany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlatformActivity.this, BatchDownloadActivity.class);
                i.putExtra("PLATFORM", platform);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}