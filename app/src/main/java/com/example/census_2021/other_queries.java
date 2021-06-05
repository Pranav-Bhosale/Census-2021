package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class other_queries extends AppCompatActivity {

    String uid;
    private RecyclerView recyclerView;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref2;
    private MyAdapter adapter;
    private ArrayList<Userqueries> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_queries);

        rootnode=FirebaseDatabase.getInstance();

        reference =FirebaseDatabase.getInstance().getReference("Queries").child("OtherQueries");
        recyclerView=findViewById(R.id.other_query_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<Userqueries>();
        adapter =new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid =  dataSnapshot.getKey();
                    ref2=rootnode.getReference("users").child(uid);
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot abx) {
                            UserHelperClass data= abx.getValue(UserHelperClass.class);
                            if(abx.exists())
                            {
                                String name=data.getName();
                                for(DataSnapshot datahash: dataSnapshot.getChildren() )
                                {
                                    String hash= datahash.getKey();
                                    Toast.makeText(other_queries.this, hash, Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(other_queries.this, "Error Getting User Name", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    Userqueries model=dataSnapshot.getValue(Userqueries.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}