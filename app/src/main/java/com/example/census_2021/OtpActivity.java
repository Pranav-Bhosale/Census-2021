package com.example.census_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    TextInputLayout otp;
    Button signInButton;
    String userphno;
    ProgressBar bar;
    String optid;
    private  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp=(TextInputLayout)findViewById(R.id.otp);
        signInButton=(Button)findViewById(R.id.SignInButton);
        bar=findViewById(R.id.progressBar2);
        userphno=getIntent().getStringExtra("mobileNo").toString();
        mAuth = FirebaseAuth.getInstance();
        initiateotp();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(otp.getEditText().getText().toString().isEmpty())
                {
                    otp.setError("Please Enter OTP");
                    otp.requestFocus();
//                    Toast.makeText(getApplicationContext(), "Fields can not be empty..", Toast.LENGTH_SHORT).show();
                }
                else   if((otp.getEditText().getText().toString().length()!=6) ){
                    otp.setError("OTP Should Be 6 Digit");
                    otp.requestFocus();
//                    Toast.makeText(getApplicationContext(), "Invalid Fields In OTP..", Toast.LENGTH_SHORT).show();
                }
                    else
                {
                    bar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(optid,otp.getEditText().getText().toString());
                    signInWithPhoneAuthCredential(credential);
                    bar.setVisibility(View.INVISIBLE);
                }


            }
        });
    }


    public void initiateotp()
    {
        bar.setVisibility(View.VISIBLE);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(userphno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(OtpActivity.this, "OTP has been sent...", Toast.LENGTH_SHORT).show();
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
                                bar.setVisibility(View.INVISIBLE);
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
                            bar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(OtpActivity.this,HomeActivity.class));
                        } else {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(OtpActivity.this, "Error In Signing In ...", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

}