package com.dev.r19.sakorisarothiadmin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityOpener extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ccreating a thread object.
        Thread myThread = new Thread() {
            @Override
            public void run() {
                // exception for thread
                try {
                    sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                Intent myIntent = new Intent(MainActivityOpener.this, AdminMainActivity.class);
                startActivity(myIntent);
                finish();
            }
        };
        myThread.start();
    }
}
