package com.example.census_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btn;
    TextView signin;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseUser muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        signin = findViewById(R.id.textView) ;
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if(mail.isEmpty())
                {
                    email.setError("Please Enter an Email");
                    email.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    password.setError("Please Enter a Password");
                    password.requestFocus();
                }
                else if(mail.isEmpty() && pass.isEmpty())
                {
                    email.setError("Please Enter an Email");
                    password.setError("Please Enter a Password");
                    email.requestFocus();
                    password.requestFocus();
                }
                else if(!(mail.isEmpty() && pass.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String mail = email.getText().toString();
                                String pass = password.getText().toString();
                                rootnode=FirebaseDatabase.getInstance();
                                reference=rootnode.getReference("users");
                                muser = mFirebaseAuth.getCurrentUser();
                                String uid = muser.getUid();
                                UserHelperClass newuser = new UserHelperClass(mail, pass);
                                reference.child(uid).setValue(newuser);
                                Intent intent =  new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}