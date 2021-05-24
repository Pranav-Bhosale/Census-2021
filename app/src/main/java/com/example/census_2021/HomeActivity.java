package com.example.census_2021;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    CardView logout,signUpBtn,signUpadmin,addsurvey,deleteSurvey,editSurvey,deleteUser,deleteAdmin,settings;
    String uid;
    TextView nameView;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nameView=(TextView)findViewById(R.id.textView2);
        uid=getIntent().getStringExtra("uid").toString();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users-admin").child(uid);
        nameView.setText("UserName");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userdata=snapshot.getValue(UserHelperClass.class);
                nameView.setText(userdata.Name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logout =(CardView)findViewById(R.id.logout);
        addsurvey=(CardView)findViewById(R.id.addsurvey);
        signUpBtn= (CardView)findViewById(R.id.adduser);
        signUpadmin=(CardView)findViewById(R.id.addadmin);
        deleteSurvey=(CardView)findViewById(R.id.deletesurvey);
        editSurvey=(CardView)findViewById(R.id.editSurvey);
        deleteUser=(CardView)findViewById(R.id.deleteuser);
        deleteAdmin=(CardView)findViewById(R.id.deleteadmin);
        settings=(CardView)findViewById(R.id.settings);
        deleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DeleteAdmin.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserSettings.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        editSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SelectSurvey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DeleteUser.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        deleteSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, deleteSurvey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        addsurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Add_survey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignUpAdmin.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

    }
}