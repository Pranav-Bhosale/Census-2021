package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class UserSettings extends AppCompatActivity {
    String uID;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        uID=getIntent().getStringExtra("uid").toString();
        Button resendEmail=(Button)findViewById(R.id.resendEmail);
        resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                for (UserInfo profile : user.getProviderData()) {
                     email = profile.getEmail();
                };
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserSettings.this, "Password Reset Email has been sent to your Email", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserSettings.this, HomeActivity.class);
                                    intent.putExtra("uid",uID);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}