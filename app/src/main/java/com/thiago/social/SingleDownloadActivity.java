package com.thiago.social;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.Python;
import com.chaquo.python.PyObject;

public class SingleDownloadActivity extends AppCompatActivity {

    String platform;
    PyObject pythonModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        platform = getIntent().getStringExtra("PLATFORM");
        ImageView logo = findViewById(R.id.top_logo);
        EditText linkInput = findViewById(R.id.input_link);
        Button btnSearch = findViewById(R.id.btn_search);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvSize = findViewById(R.id.tv_size);
        Button btnDownload = findViewById(R.id.btn_download);
        ProgressBar progress = findViewById(R.id.progress_bar);

        if(platform.equals("facebook")) logo.setImageResource(R.drawable.facebook_logo);
        if(platform.equals("instagram")) logo.setImageResource(R.drawable.instagram_logo);
        if(platform.equals("tiktok")) logo.setImageResource(R.drawable.tiktok_logo);
        if(platform.equals("youtube")) logo.setImageResource(R.drawable.youtube_logo);
        if(platform.equals("x")) logo.setImageResource(R.drawable.x_logo);

        // Start Python
        if (!Python.isStarted()) Python.start(new com.chaquo.python.android.AndroidPlatform(this));
        pythonModule = Python.getInstance().getModule("social_core");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkInput.getText().toString().trim();
                if(url.isEmpty()) return;
                progress.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PyObject result = pythonModule.callAttr("get_video_info", url);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.setVisibility(View.GONE);
                                tvTitle.setText("Title: " + result.asMap().get("title").toString());
                                tvSize.setText("Size: " + result.asMap().get("size").toString());
                                btnDownload.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkInput.getText().toString().trim();
                progress.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = pythonModule.callAttr("download_video", url, platform).toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(SingleDownloadActivity.this, res, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}