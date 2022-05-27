package com.engage.sourabh.attandanceSystem.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Constants;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceReport extends AppCompatActivity {



    private List<Integer> minmum=new ArrayList<>();
    private List<Integer> manmum=new ArrayList<>();
    private String searchCourses1,searchingDiv,searchingRoll,searchYear1,classyear;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private DatabaseReference atttendanceRef;
    private DatabaseReference dbRef2;
    private Spinner selectsemister;
    private ProgressBar pb;
    private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_report);






        selectsemister=findViewById(R.id.selectsemister);
        pb=findViewById(R.id.studentattandacepb);
        pb.setVisibility(View.VISIBLE);




        //Adding data to Spinner


        List<String> categories1 = new ArrayList<String>();

            categories1.add("FY_1BSCIT");
            categories1.add("FY_2BSCIT");



        ArrayAdapter<CharSequence> DataAdapterFY=ArrayAdapter.createFromResource(
                StudentAttendanceReport.this,
                R.array.FY_1,R.layout.spinner_item);

        DataAdapterFY.setDropDownViewResource(R.layout.spinner_drop_item);

        ArrayAdapter<CharSequence> DataAdapterFY2=ArrayAdapter.createFromResource(
                StudentAttendanceReport.this,
                R.array.FY_2,R.layout.spinner_item);

        DataAdapterFY2.setDropDownViewResource(R.layout.spinner_drop_item);





        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(StudentAttendanceReport.this,
                R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);




        selectsemister.setAdapter(dataAdapter1);


        //giving results based on the semester he select in the spinner
        CallselectSpinner();





    }

    private void CallselectSpinner() {
        searchCourses1=((global) getApplication()).getCourse();
        searchYear1=((global) getApplication()).getYear();
        searchingDiv=((global)getApplication()).getDivision();
        searchingRoll=((global)getApplication()).getRollnumber();

        classyear=searchCourses1+searchYear1;

        selectsemister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableLayout.removeAllViews();
                if(position == 0 && classyear.equals("BSCITFY")){
                    for(int i = 0; i<= Constants.FY1_BSC.size()-1; i++){

                        createAttendance(searchCourses1,"FY_1",searchingDiv,searchingRoll,
                                Constants.FY1_BSC.get(i));
                    }
                }else if(position == 1&& classyear.equals("BSCITFY")){
                    for(int i=0;i<= Constants.FY2_BSC.size()-1;i++){


                        createAttendance(searchCourses1,"FY_2",searchingDiv,searchingRoll,
                                Constants.FY2_BSC.get(i));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(StudentAttendanceReport.this,"Error"+parent,Toast.LENGTH_LONG).show();
            }
        });

        tableLayout=findViewById(R.id.studentattandace);

    }


    private void createAttendance(final String searchcource1, final String searchyear1, final String searchdiv1, String searchroll1, final String dataclass){
        Log.d("error","hey"+searchcource1+searchyear1+searchdiv1+searchroll1+dataclass);
          String icode=((global)getApplication()).getInstituteCode();
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("institutes/"+icode);
        atttendanceRef = dbref.child("Attandance/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+searchroll1+"/"+dataclass);
        atttendanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    final long child=dataSnapshot.getChildrenCount();
                    Log.d("report","mainchild----"+child);
                    final int minchild=(int)child;

                    dbRef2 = dbref.child("Attandancedetail/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+dataclass);
                    dbRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                num=num+1;

                                long tchild=dataSnapshot.getChildrenCount();

                                int maxchild=(int)tchild;
                                makeProgressReport(dataclass,minchild,maxchild);
                                report(num);
                            }  else {
                                num=num+1;

                                makeProgressReport(dataclass,minchild,minchild);
                                report(num);
                            }
                            pb.setVisibility(View.GONE);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("report","Error--"+error);
                            pb.setVisibility(View.GONE);
                            Toast.makeText(StudentAttendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    num=num+1;

                    pb.setVisibility(View.GONE);
                    int i=0,j=0;
                    makeProgressReport(dataclass,i,j);

                    report(num);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("report","Error--"+error);
                pb.setVisibility(View.GONE);
                Toast.makeText(StudentAttendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
            }
        });

    }



    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void makeProgressReport (String i, int min, int max){
        minmum.add(min);
        manmum.add(max);
        tableRow = new TableRow(StudentAttendanceReport.this);
        TextView textV=new TextView(StudentAttendanceReport.this);
        textV.setText(i);
        textV.setTextSize(20);
        textV.setPadding(5,0,0,0);
        textV.setWidth(800);
        textV.setTextColor(Color.BLACK);
        TextView textV2=new TextView(StudentAttendanceReport.this);
        int percentage=0;
        if(max==0){
            percentage=0;
        }else {
            percentage =(min*100)/max;
        }
        textV2.setTextColor(Color.GRAY);
        if(percentage>100){
            percentage=100;
        }
        textV2.setText(min+"/"+max+"\t\t"+percentage+"%");
        textV2.setTextSize(14);
        textV2.setPadding(0,0,0,0);
        tableRow.addView(textV);
        tableRow.addView(textV2);
        ProgressBar pbar=new ProgressBar(StudentAttendanceReport.this,
                null,android.R.attr.progressBarStyleHorizontal);
        pbar.setMin(0);
        pbar.setMax(max);
        pbar.setProgress(min);
        pbar.setPadding(15,0,15,5);
        TextView qw=new TextView(StudentAttendanceReport.this);
        qw.setBackgroundColor(Color.BLACK);
        qw.setHeight(5);
        tableLayout.addView(tableRow);
        tableLayout.addView(pbar);
        tableLayout.addView(qw);
    }


    @SuppressLint("SetTextI18n")
    private void report(int qw){
        if(qw==10){
            int newmin=0;
            int newmax=0;
            num=0;
            for(int v=0;v<minmum.size();v++){
                newmax=newmax+manmum.get(v);
                newmin=newmin+minmum.get(v);
            }
            int newpercentage;
            if(newmax==0){
                newpercentage=0;
            }else {
                newpercentage =(newmin*100)/newmax;
            }
            Log.d("atttt","per"+newpercentage);
            TableRow tableRow1 = new TableRow(StudentAttendanceReport.this);
            tableRow1.setBackgroundColor(Color.parseColor("#1134af"));
            TextView textView3=new TextView(StudentAttendanceReport.this);
            textView3.setText("Total Attendance");
            textView3.setTextSize(20);
            textView3.setPadding(35,10,0,15);
            textView3.setWidth(800);
            textView3.setTextColor(Color.WHITE);
            TextView tv2=new TextView(StudentAttendanceReport.this);
            tv2.setTextColor(Color.WHITE);
            if(newpercentage>100){
                newpercentage=100;
            }
            tv2.setText(newmin+"/"+newmax+"\t\t"+newpercentage+"%");
            tv2.setTextSize(14);
            tv2.setPadding(0,10,0,15);
            tableRow1.addView(textView3);
            tableRow1.addView(tv2);
            tableLayout.addView(tableRow1);
            newmax=0;
            newmin=0;
            newpercentage=0;
            manmum.clear();
            minmum.clear();
        }
    }
}

