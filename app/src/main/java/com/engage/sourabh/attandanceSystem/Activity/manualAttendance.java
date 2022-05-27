package com.engage.sourabh.attandanceSystem.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.AttandanceRecord;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class manualAttendance extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private DatabaseReference databaseRef1;
    String icode="";
    ImageView backBtn;
    HashSet <String> rollList=new HashSet<String>();
    HashSet<String> stdList=new HashSet<String>();
    private DatabaseReference databaseRef2;
    private ProgressBar pbar;

    long stdCount =0;
    private String course,year,division,subject,startTime,EndTime;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectrollnumber);

        pbar =findViewById(R.id.setectpb);
        pbar.setVisibility(View.VISIBLE);
         backBtn=findViewById(R.id.backBtn);

        initializeVariable();// setting value from intent;


        databaseRef = FirebaseDatabase.getInstance().getReference("Attandance");

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });


        String yearsubstring=year.substring(0,2);
        icode=((global)getApplication()).getInstituteCode();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/"+"student");
        databaseRef = dbref.child(course+"/"+yearsubstring+"/"+division);
        DatabaseReference rollcountref= databaseRef;




        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    pbar.setVisibility(View.GONE);
                    stdCount =dataSnapshot.getChildrenCount();
                    String s1=Long.toString(stdCount);
                    Log.d("tag","hy"+s1);

                    TableLayout tableLayout = new TableLayout(getApplicationContext());
                    tableLayout=findViewById(R.id.mainLayout);
                    TableRow rollNoRow;
                    CheckBox rollNoBox;

                    for(int i = 1; i<= stdCount;){
                        rollNoRow = new TableRow(getApplicationContext());
                        for (int j = 0; j < 3; j++) {
                            if(i<= stdCount) {
                                rollNoBox = new CheckBox(getApplicationContext());
                                rollNoBox.setText("RollNo." + i);
                                rollNoBox.setTextColor(Color.BLACK);
                                rollNoBox.setId(i);
                                rollNoBox.setTextSize(12);
                                i++;
                                rollNoBox.setPadding(50, 30, 50, 30);
                                rollNoRow.addView(rollNoBox);
                            }
                        }
                        tableLayout.addView(rollNoRow);
                    }


                    Button btn=new Button(getApplicationContext());
                    btn.setText("Submit");
                    btn.setTextColor(Color.WHITE);
                    btn.setId(1000);
                    btn.setBackgroundColor(Color.parseColor("#3c415e"));

                    tableLayout.addView(btn);

                    final List <String> studentRollList = new ArrayList <>();
                    for(int cb = 1; cb <= stdCount; cb++){
                        CheckBox checkBx = findViewById(cb);
                        final int finalCb = cb;
                        checkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Log.d("tag", "cb- "+ finalCb +" checked- "+isChecked);
                                String rollNo = String.valueOf(finalCb);
                                if(isChecked){
                                    studentRollList.add(rollNo);
                                }else{
                                    studentRollList.remove(rollNo);
                                }
                                Log.d("tag","rollNoList - "+ Arrays.toString(studentRollList.toArray()));
                            }
                        });
                    }

                    databaseRef1 =FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/"+"Attandance");
                    btn=findViewById(1000);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date dNow = new Date( );
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddHH:mm");
                            final String date=ft.format(dNow);

                            pbar.setVisibility(View.VISIBLE);
                            for(int roll=0;roll<studentRollList.size();roll++){
                                AttandanceRecord attandance=new AttandanceRecord(startTime,EndTime);
                                databaseRef1.child(course+"/"+year+"/"+division+"/"+ studentRollList.get(roll)+"/"+subject+"/"+date).setValue(attandance);
                                Log.d("tag","submit");
                            }
                            Date AdNow = new Date( );
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft1 = new SimpleDateFormat ("yyyyMMddHH:mm");
                            final String Adate=ft1.format(AdNow);

                            databaseRef2 = FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/"+"Attandancedetail");
                            databaseRef2.child(course+"/"+year+"/"+division+"/"+subject+"/"+Adate).setValue("date");
                            pbar.setVisibility(View.GONE);
                            Toast.makeText(manualAttendance.this,"Submit", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }else {



                    Toast.makeText(manualAttendance.this,"First Enter a Student",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(manualAttendance.this,"Error--"+error,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeVariable () {

        final Intent intent=getIntent();
        course =intent.getStringExtra("course");
        year=intent.getStringExtra("year");
        division=intent.getStringExtra("division");
        subject=intent.getStringExtra("subject");
        startTime=intent.getStringExtra("starttime");
        EndTime=intent.getStringExtra("endtime");
    }
}
