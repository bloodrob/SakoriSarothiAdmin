package com.dev.r19.sakorisarothiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {

    private Button ToUploadJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        //init
        ToUploadJob = (Button)findViewById(R.id.upload_job);
        //on click
        ToUploadJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminUploadJob.class);
                startActivity(intent);
            }
        });
    }
}
