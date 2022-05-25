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

    private TableLayout tableLayout;
    private TableRow tableRow;
    private DatabaseReference notice;
    private DatabaseReference notice1;
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

        final String searchcource1=((global) getApplication()).getCourse();
        String searchyear1=((global) getApplication()).getYear();
        final String searchdiv1=((global)getApplication()).getDivision();
        final String searchroll1=((global)getApplication()).getRollnumber();

        final String classyear=searchcource1+searchyear1;
        List<String> categories1 = new ArrayList<String>();
        if(classyear.equals("BSCITFY")){
            categories1.add("FY_1BSCIT");
            categories1.add("FY_2BSCIT");
        }else {
            categories1.add("FY_1BSCIT");
            categories1.add("FY_2BSCIT");

        }

        ArrayAdapter<CharSequence> DataAdapterFY=ArrayAdapter.createFromResource(
                StudentAttendanceReport.this,
                R.array.FY_1,R.layout.spinner_item);

        DataAdapterFY.setDropDownViewResource(R.layout.spinner_drop_item);

        ArrayAdapter<CharSequence> DataAdapterFY2=ArrayAdapter.createFromResource(
                StudentAttendanceReport.this,
                R.array.FY_2,R.layout.spinner_item);

        DataAdapterFY2.setDropDownViewResource(R.layout.spinner_drop_item);





        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(StudentAttendanceReport.this, R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        selectsemister.setAdapter(dataAdapter1);
        selectsemister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableLayout.removeAllViews();
                if(position == 0 && classyear.equals("BSCITFY")){
                    for(int i=0;i<= Constants.FY1_BSC.size()-1;i++){
                        Log.d("report","Report"+ Constants.FY1_BSC.get(i));
                        attandance(searchcource1,"FY_1",searchdiv1,searchroll1, Constants.FY1_BSC.get(i));
                    }
                }else if(position == 1&& classyear.equals("BSCITFY")){
                    for(int i=0;i<= Constants.FY2_BSC.size()-1;i++){
                        Log.d("report","Report"+ Constants.FY2_BSC.get(i));
                        attandance(searchcource1,"FY_2",searchdiv1,searchroll1, Constants.FY2_BSC.get(i));
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




    private void attandance(final String searchcource1, final String searchyear1, final String searchdiv1, String searchroll1, final String dataclass){
        Log.d("error","hey"+searchcource1+searchyear1+searchdiv1+searchroll1+dataclass);
          String icode=((global)getApplication()).getInstituteCode();
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("institutes/"+icode);
        notice = dbref.child("Attandance/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+searchroll1+"/"+dataclass);
        notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    final long child=dataSnapshot.getChildrenCount();
                    Log.d("report","mainchild----"+child);
                    final int minchild=(int)child;

                    notice1 = dbref.child("Attandancedetail/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+dataclass);
                    notice1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                num=num+1;
                                Log.d("att","num"+String.valueOf(num));
                                long tchild=dataSnapshot.getChildrenCount();
                                Log.d("report","subchild----"+tchild);
                                int maxchild=(int)tchild;
                                progress(dataclass,minchild,maxchild);
                                report(num);
                            }  else {
                                num=num+1;
                                Log.d("att","num"+String.valueOf(num));
                                Log.d("report","Teacher Not child");
                                progress(dataclass,minchild,minchild);
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
                    Log.d("att","num"+String.valueOf(num));
                    pb.setVisibility(View.GONE);
                    int i=0,j=0;
                    progress(dataclass,i,j);
                    Log.d("report","student Not child");
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
    private void progress(String i, int min, int max){
        minmum.add(min);
        manmum.add(max);
        tableRow = new TableRow(StudentAttendanceReport.this);
        TextView tv=new TextView(StudentAttendanceReport.this);
        tv.setText(i);
        tv.setTextSize(20);
        tv.setPadding(5,0,0,0);
        tv.setWidth(800);
        tv.setTextColor(Color.BLACK);
        TextView tv1=new TextView(StudentAttendanceReport.this);
        int percentage=0;
        if(max==0){
            percentage=0;
        }else {
            percentage =(min*100)/max;
        }
        tv1.setTextColor(Color.GRAY);
        if(percentage>100){
            percentage=100;
        }
        tv1.setText(min+"/"+max+"\t\t"+percentage+"%");
        tv1.setTextSize(14);
        tv1.setPadding(0,0,0,0);
        tableRow.addView(tv);
        tableRow.addView(tv1);
        ProgressBar pb=new ProgressBar(StudentAttendanceReport.this,null,android.R.attr.progressBarStyleHorizontal);
        pb.setMin(0);
        pb.setMax(max);
        pb.setProgress(min);
        pb.setPadding(15,0,15,5);
        TextView qw=new TextView(StudentAttendanceReport.this);
        qw.setBackgroundColor(Color.BLACK);
        qw.setHeight(5);
        tableLayout.addView(tableRow);
        tableLayout.addView(pb);
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
            TextView tv3=new TextView(StudentAttendanceReport.this);
            tv3.setText("Total Attendance");
            tv3.setTextSize(20);
            tv3.setPadding(35,10,0,15);
            tv3.setWidth(800);
            tv3.setTextColor(Color.WHITE);
            TextView tv2=new TextView(StudentAttendanceReport.this);
            tv2.setTextColor(Color.WHITE);
            if(newpercentage>100){
                newpercentage=100;
            }
            tv2.setText(newmin+"/"+newmax+"\t\t"+newpercentage+"%");
            tv2.setTextSize(14);
            tv2.setPadding(0,10,0,15);
            tableRow1.addView(tv3);
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

