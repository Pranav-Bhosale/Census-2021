package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteAdmin extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference reference, refdelete;
    LinearLayout surveyListLayout;
    String uID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_admin);
        uID = getIntent().getStringExtra("uid").toString();
        surveyListLayout = (LinearLayout) findViewById(R.id.xyz);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String useruidTemp = snap.getKey();
                    for (DataSnapshot data : snap.getChildren()) {
                        if (data.getKey().equals("name")) {
                            String name = data.getValue(String.class);
                            addbutonuser(useruidTemp, name);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteAdmin.this, "Error Fetching Admin Names..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addbutonuser(String useruidTemp, String name) {
        rootnode.getReference("users").child(useruidTemp).child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot abc) {
                String post=abc.getValue(String.class);
                if(post.equals("admin"))
                {
                    if(!uID.equals(useruidTemp))
                    {
                        rootnode.getReference("users").child(useruidTemp).child("state").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String state = snapshot.getValue(String.class);
                                if (state.equals("enabled")) {
                                    Button b = new Button(getApplicationContext());
                                    b.setText(name + "  (Enabled)");
                                    b.setHint(useruidTemp);
                                    b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                                    b.setTextColor(Color.parseColor("#FFFFFF"));
                                    b.setBackgroundColor(Color.parseColor("#FF871DB6"));
                                    b.setGravity(Gravity.CENTER_HORIZONTAL);
                                    b.setPadding(5, 50, 5, 50);
                                    b.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(DeleteAdmin.this).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setCancelable(true);
                                            alertDialog.setMessage("Do you want to Disable  Admin?\n" + name);
                                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            refdelete = rootnode.getReference("users").child(useruidTemp).child("state");
                                                            refdelete.setValue("disabled");
                                                            Toast.makeText(DeleteAdmin.this, "Admin " + name + " Disabled Successfully..", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(DeleteAdmin.this, DeleteAdmin.class);
                                                            intent.putExtra("uid", uID);
                                                            dialog.dismiss();
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });
                                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            alertDialog.show();

                                        }
                                    });
                                    surveyListLayout.addView(b);
                                }
                                if (state.equals("disabled")) {
                                    Button b = new Button(getApplicationContext());
                                    b.setText(name + "  (Disabled)");
                                    b.setHint(useruidTemp);
                                    b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                                    b.setTextColor(Color.parseColor("#FFFFFF"));
                                    b.setBackgroundColor(Color.parseColor("#FF871DB6"));
                                    b.setGravity(Gravity.CENTER_HORIZONTAL);
                                    b.setPadding(5, 50, 5, 50);
                                    b.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(DeleteAdmin.this).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setCancelable(true);
                                            alertDialog.setMessage("Do you want to Enable  Admin?\n" + name);
                                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            refdelete = rootnode.getReference("users").child(useruidTemp).child("state");
                                                            refdelete.setValue("enabled");
                                                            Toast.makeText(DeleteAdmin.this, "Admin " + name + " Enabled Successfully..", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(DeleteAdmin.this, DeleteAdmin.class);
                                                            intent.putExtra("uid", uID);
                                                            dialog.dismiss();
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    });
                                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            alertDialog.show();

                                        }
                                    });
                                    surveyListLayout.addView(b);
                                }
                                if (state.equals("main")) {
                                    Button b = new Button(getApplicationContext());
                                    b.setText(name + "  (Admin-Main)");
                                    b.setHint(useruidTemp);
                                    b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                                    b.setTextColor(Color.parseColor("#FFFFFF"));
                                    b.setBackgroundColor(Color.parseColor("#FF871DB6"));
                                    b.setGravity(Gravity.CENTER_HORIZONTAL);
                                    b.setPadding(5, 50, 5, 50);
                                    b.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(DeleteAdmin.this).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setCancelable(true);
                                            alertDialog.setMessage("You Can Not Change Main Admin settings\n" + name + " is a Main-Admin");
                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            alertDialog.show();
                                        }
                                    });
                                    surveyListLayout.addView(b);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(DeleteAdmin.this, "Error Fetching Admin Names..", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}