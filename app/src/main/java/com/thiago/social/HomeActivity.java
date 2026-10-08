package com.thiago.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private void openPlatform(String platformName) {
        Intent intent = new Intent(HomeActivity.this, PlatformActivity.class);
        intent.putExtra("PLATFORM", platformName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView fb = findViewById(R.id.btn_facebook);
        TextView ig = findViewById(R.id.btn_instagram);
        TextView tt = findViewById(R.id.btn_tiktok);
        TextView yt = findViewById(R.id.btn_youtube);
        TextView x = findViewById(R.id.btn_x);

        fb.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { openPlatform("facebook"); } });
        ig.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { openPlatform("instagram"); } });
        tt.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { openPlatform("tiktok"); } });
        yt.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { openPlatform("youtube"); } });
        x.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { openPlatform("x"); } });
    }
}