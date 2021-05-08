package com.example.census_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    EditText otp;
    Button signInButton;
    String userphno;
    String optid;
    private  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp=(EditText)findViewById(R.id.otp);
        signInButton=(Button)findViewById(R.id.SignInButton);
        userphno=getIntent().getStringExtra("mobileNo").toString();
        mAuth = FirebaseAuth.getInstance();
        initiateotp();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields can not be empty..", Toast.LENGTH_SHORT).show();
                }
                else   if(otp.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(), "Invalid Fields In OTP..", Toast.LENGTH_SHORT).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(optid,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }


            }
        });
    }


    public void initiateotp()
    {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(userphno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                optid=s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                            {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(OtpActivity.this, "Error Signing In with OTP...", Toast.LENGTH_SHORT).show();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(OtpActivity.this,HomeActivity.class));
                        } else
                            Toast.makeText(OtpActivity.this, "Error In Signing In ...", Toast.LENGTH_SHORT).show();


                    }

                });
    }


}