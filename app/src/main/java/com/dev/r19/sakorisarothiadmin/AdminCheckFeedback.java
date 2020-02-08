package com.dev.r19.sakorisarothiadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminCheckFeedback extends AppCompatActivity {

    private ListView feedbackList;
    private FirebaseDatabase databaseToFB;
    private DatabaseReference refToDatabaseToFB;
    private List<String> fdList;
    private ArrayAdapter<String> getFdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_feedback);
        //init activity var
        feedbackList = (ListView)findViewById(R.id.feedback_List);

        databaseToFB = FirebaseDatabase.getInstance();
        refToDatabaseToFB = databaseToFB.getReference("UserFeedback");
    }
}
