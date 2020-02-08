package com.dev.r19.sakorisarothiadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminPushNotification extends AppCompatActivity {

    static public String takeJobName, takeLastDate;
    private Button sendNoti;
    //volley newtrok library
    private RequestQueue myRequestQueue;
    private String toFCMData;
    public static String subsTopic;
    //for app Token
    private String appToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_push_notification);
        //init activity var
        sendNoti = (Button)findViewById(R.id.send_noti);
        //on submit
        sendNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init and assign the var
                initAndAssignTheVar();
                //send noti
                sendNotiToUser();
            }
        });
    }

    public void sendNotiToUser() {
        JSONObject mainJsonObj = new JSONObject();
        try {
            mainJsonObj.put("to","/topics/"+subsTopic);
            JSONObject notiDetails = new JSONObject();
            notiDetails.put("title",takeJobName);
            notiDetails.put("body",takeLastDate);

            JSONObject notiData = new JSONObject();
            notiData.put("jobDate","new job");

           // JSONObject notiExtraData = new JSONObject();
          //  notiExtraData.put("notificationDetails", notiDetails);
          //  notiExtraData.put("notificationData", notiData);

            mainJsonObj.put("notification",notiDetails);
            mainJsonObj.put("ExtraData",notiData);

            JsonObjectRequest myJsonRequest = new JsonObjectRequest(Request.Method.POST, toFCMData,
                    mainJsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                          //  Intent intent = new Intent(AdminPushNotification.this, AdminUploadJob.class);
                          //  startActivity(intent);
                            Toast.makeText(AdminPushNotification.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Intent intent = new Intent(AdminPushNotification.this, AdminUploadJobDetailsPdf.class);
                    startActivity(intent);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AIzaSyArBkEhxCBJ0ID1DSJ77rYjKO3r3Cx5qpo");
                    return header;
                }
            };
            myRequestQueue.add(myJsonRequest);
            Toast.makeText(AdminPushNotification.this, "notification sent", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initAndAssignTheVar() {
        myRequestQueue = Volley.newRequestQueue(this);
       // toLink = "https://www.googleapis.com/auth/firebase";
        toFCMData = "https://fcm.googleapis.com/fcm/send";
    }
}
