package com.dev.r19.sakorisarothiadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {


    private EditText userid, pass;
    private Button login;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        // initialization

        userid = (EditText)findViewById(R.id.AUserId);
        pass = (EditText)findViewById(R.id.APass);
        login = (Button)findViewById(R.id.AdminLogin);
        auth = FirebaseAuth.getInstance();

        // cheking the session
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(AdminLogin.this, AdminMainActivity.class);
            startActivity(intent);
        }
        // Set up onclick listener for admin login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get and convert string and match with a existing string

                String UserId = userid.getText().toString().trim();
                if (TextUtils.isEmpty(UserId)) {
                    Toast.makeText(AdminLogin.this, "Required User Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Password = pass.getText().toString().trim();
                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(AdminLogin.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(UserId, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(AdminLogin.this, AdminMainActivity.class);
                            startActivity(intent);
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(AdminLogin.this, "Wrong Credential", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

            }
        });

    }
}
