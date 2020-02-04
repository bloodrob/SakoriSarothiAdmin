package com.dev.r19.sakorisarothiadmin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPushNotification extends AppCompatActivity {

    static public String takeJobName, takeLastDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_push_notification);
    }
}
