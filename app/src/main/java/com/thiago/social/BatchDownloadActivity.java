package com.thiago.social;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.Python;
import com.chaquo.python.PyObject;

public class BatchDownloadActivity extends AppCompatActivity {

    String platform;
    PyObject pythonModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We no need layout, we show dialog directly
        platform = getIntent().getStringExtra("PLATFORM");
        
        if (!Python.isStarted()) Python.start(new com.chaquo.python.android.AndroidPlatform(this));
        pythonModule = Python.getInstance().getModule("social_core");

        showBatchDialog();
    }

    private void showBatchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_batch);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText inputMany = dialog.findViewById(R.id.input_many_links);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnContinue = dialog.findViewById(R.id.btn_continue);

        btnCancel.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); finish(); } });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allLinks = inputMany.getText().toString().trim();
                if(allLinks.isEmpty()) return;
                String[] links = allLinks.split("\n");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(String link : links) {
                            if(!link.trim().isEmpty()){
                                pythonModule.callAttr("download_video", link.trim(), platform);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BatchDownloadActivity.this, "All Downloads Finished", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });
        dialog.show();
    }
}