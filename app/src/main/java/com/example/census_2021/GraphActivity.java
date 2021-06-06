package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.text.UStringsKt;
public class GraphActivity extends AppCompatActivity {

    String qStatement,surveyName;
    FirebaseDatabase rootnode;
    DatabaseReference ref1,ref2;
    List<DataEntry> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        surveyName=getIntent().getStringExtra("name");
        qStatement=getIntent().getStringExtra("qsts");
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        rootnode=FirebaseDatabase.getInstance();
       ref2=rootnode.getReference("graph").child(surveyName).child(qStatement);
        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(GraphActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long cnt=snapshot.getChildrenCount();
                int i=cnt.intValue();
                String arrstr[]=new String[i];
                int arr[]=new int[i];
                int j=0;
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    String key=snap.getKey();
                    Long val=snap.getValue(Long.class);
                    int val2=val.intValue();
                    Toast.makeText(GraphActivity.this, key, Toast.LENGTH_SHORT).show();
                    arr[j]=val2;
                    arrstr[j]=key;
                    j++;
                }
                for(int k=0;k<arr.length;k++)
                {
                    Integer keyy=arr[k];
                    int kie=keyy.intValue();
                    String val= arrstr[k];
                    String kee=keyy.toString();
                    Toast.makeText(GraphActivity.this, kee, Toast.LENGTH_SHORT).show();
                    data.add(new ValueDataEntry(val,kie));
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        data.add(new ValueDataEntry("hi",4));
        pie.data(data);
//        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<DataEntry> data = new ArrayList<>();
//                for(DataSnapshot data3:snapshot.getChildren())
//                {
//                    String key=data3.getKey();
//                    Long val=data3.getValue(Long.class);
//                    int vak=val.intValue();
//                    data.add(new ValueDataEntry(key, vak));
//                    Toast.makeText(GraphActivity.this, "loop", Toast.LENGTH_SHORT).show();
//                }
//                data.add(new ValueDataEntry("Xyz", 5));
//                pie.data(data);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        for(int j=0;j<arrstr.length;j++)
//        {
//            String val=arrstr[j];
//            Toast.makeText(this,val, Toast.LENGTH_SHORT).show();
//        }

//            String ans=entry.getKey();
//            Integer freq=entry.getValue();
//            String Freq=freq.toString();
//            Toast.makeText(this, ans, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, Freq, Toast.LENGTH_SHORT).show();
//            data.add(new ValueDataEntry(ans,freq));
        pie.title(qStatement);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Data")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);
    }
}



