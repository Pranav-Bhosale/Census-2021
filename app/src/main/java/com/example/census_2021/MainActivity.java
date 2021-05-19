package com.example.census_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputLayout email, password;
    Button btn;
    ProgressBar bar;
    TextView signin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = (TextInputLayout)findViewById(R.id.editTextTextQuestionStatement);
        bar=(ProgressBar) findViewById(R.id.progressBar2);
        password = (TextInputLayout)findViewById(R.id.editTextTextoption);
//        signin = findViewById(R.id.textView2);
        btn = (Button) findViewById(R.id.img2);
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//                if (mFirebaseUser != null) {
//                    String id=mFirebaseUser.getUid();
//                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                    i.putExtra("uid",id);
//                    startActivity(i);
//                } else {
//                    Toast.makeText(MainActivity.this, "Please Log in", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setError(null);
                password.setError(null);
                bar.setVisibility(View.VISIBLE);
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if (mail.isEmpty()) {
                    bar.setVisibility(View.INVISIBLE);
                    email.setError("Please Enter an Email");
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    bar.setVisibility(View.INVISIBLE);
                    password.setError("Please Enter a Password");
                    password.requestFocus();
                } else if (mail.isEmpty() && pass.isEmpty()) {
                    bar.setVisibility(View.INVISIBLE);
                    email.setError("Please Enter an Email");
                    password.setError("Please Enter a Password");
                    email.requestFocus();
                    password.requestFocus();
                } else if (!(mail.isEmpty() && pass.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
                                email.setError("Please Enter Correct Email");
                                password.setError("Please Enter Correct Password");
                                email.requestFocus();
                                password.requestFocus();

                            } else {
                                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                String id=mFirebaseUser.getUid();
                                rootnode = FirebaseDatabase.getInstance();
                                reference = rootnode.getReference("users-admin").child(id);
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserHelperClass userdata=snapshot.getValue(UserHelperClass.class);
                                        mobileNo=userdata.Mobile_No;
//                                        Toast.makeText(MainActivity.this,mobileNo, Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(MainActivity.this, OtpActivity.class);
                                             intent.putExtra("mobileNo",mobileNo);
                                        intent.putExtra("uid",id);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this,"Error in getting mobile no", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error Occurred Contact App Dev", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//                startActivity(intent);
//            }
//        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
}