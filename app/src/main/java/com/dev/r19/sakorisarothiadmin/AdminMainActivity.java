package com.dev.r19.sakorisarothiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity {

    private Button ToUploadJob, toLogOut;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        //init
        ToUploadJob = (Button)findViewById(R.id.upload_job);
        toLogOut = (Button)findViewById(R.id.to_log_out);
        //firebase init
        auth = FirebaseAuth.getInstance();
        //on click
        ToUploadJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminUploadJob.class);
                startActivity(intent);
            }
        });
        toLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            auth.signOut();
                auth.getCurrentUser();
                if (auth == null) {
                    Intent intent = new Intent(AdminMainActivity.this, AdminLogin.class);
                    Toast.makeText(AdminMainActivity.this, "Successfully Sign Out", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });
    }
}
