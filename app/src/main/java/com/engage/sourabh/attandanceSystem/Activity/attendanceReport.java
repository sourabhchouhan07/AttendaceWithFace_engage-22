package com.engage.sourabh.attandanceSystem.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class attendanceReport extends AppCompatActivity {
    private Spinner selectCourse, selectYear;
    private EditText searchroll, SelectDiv;
    private Button search;
    ImageView backBtn;
    private DatabaseReference databaseRef;
    private DatabaseReference databaseRef2;
    private List<Integer> minNoList =new ArrayList<>();
    private List<Integer> maxNoList =new ArrayList<>();
    private int yearPos, coursePos;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private int count =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);






        selectCourse =findViewById(R.id.searchspinner);
        selectYear =findViewById(R.id.searchspinner2);
        searchroll=findViewById(R.id.searchroll);
        SelectDiv =findViewById(R.id.searchdivision);
        search=findViewById(R.id.search);
        backBtn=findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

        ArrayAdapter<CharSequence> DataAdapterC=ArrayAdapter
                .createFromResource(attendanceReport.this,R.array.SUbject,R.layout.spinner_item);

        DataAdapterC.setDropDownViewResource(R.layout.spinner_drop_item);

        selectCourse.setAdapter(DataAdapterC);


        ArrayAdapter<CharSequence> DataAdapter1=ArrayAdapter
                .createFromResource(attendanceReport.this,R.array.categriory,R.layout.spinner_item);

        DataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);

        selectYear.setAdapter(DataAdapter1);


        selectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursePos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(attendanceReport.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearPos =position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(attendanceReport.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                final String stdRollno=searchroll.getText().toString().trim();
                final String stdDiv= SelectDiv.getText().toString().trim();
                final String stdCourse= selectCourse.getSelectedItem().toString().trim();
                final String stdYear= selectYear.getSelectedItem().toString().trim();
                if(stdRollno.isEmpty()){
                    searchroll.setError("please enter Roll number");
                    searchroll.requestFocus();
                }else if(stdDiv.isEmpty()){
                    SelectDiv.setError("please enter Roll number");
                    SelectDiv.requestFocus();
                }else{
                    tableLayout.removeAllViews();
                    if(coursePos ==0&& yearPos ==0){
                        for(int i = 0; i<= Constants.FY1_BSC.size()-1; i++){

                            createAttendanceTable(stdCourse,stdYear,stdDiv,stdRollno,Constants.FY1_BSC.get(i));
                        }
                    }else if(coursePos ==0&& yearPos ==1){
                        for(int i = 0; i<= Constants.FY2_BSC.size()-1; i++){

                            createAttendanceTable(stdCourse,stdYear,stdDiv,stdRollno, Constants.FY2_BSC.get(i));
                        }
                    }
                }
            }
        });
        tableLayout=findViewById(R.id.list);

    }


    private void createAttendanceTable (final String stdCourse,
                                        final String stdYear,
                                        final String stdDiv,
                                        String stdRollno,
                                        final String dataclass){


         String icode=((global)getApplication()).getInstituteCode();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("institutes/"+icode);
        databaseRef = dbref.child("Attandance/"+stdCourse+"/"+stdYear+"/"+stdDiv+"/"+stdRollno+"/"+dataclass);



        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    final long child=dataSnapshot.getChildrenCount();

                    final int minchild=(int)child;
                    databaseRef2 = dbref.child("Attandancedetail/"+stdCourse+"/"+stdYear+"/"+stdDiv+"/"+dataclass);
                    databaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                count = count +1;
                                long tchild=dataSnapshot.getChildrenCount();

                                int maxchild=(int)tchild;
                                progresReport(dataclass,minchild,maxchild);
                                report(count);
                            }  else {
                                count = count +1;

                                progresReport(dataclass,minchild,minchild);
                                report(count);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(attendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    count = count +1;

                    int i=0,j=0;
                    progresReport(dataclass,i,j);

                    report(count);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test","Error   "+error);
                Toast.makeText(attendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void progresReport (String i, int min, int max){
        minNoList.add(min);
        maxNoList.add(max);
        tableRow = new TableRow(attendanceReport.this);
        TextView textView=new TextView(attendanceReport.this);
        textView.setText(i);
        textView.setTextSize(20);
        textView.setPadding(5,0,0,0);
        textView.setWidth(800);
        textView.setTextColor(Color.BLACK);
        TextView textView1=new TextView(attendanceReport.this);
        int percentage;
        if(max==0){
            percentage=0;
        }else {
            percentage =(min*100)/max;
        }
        textView1.setTextColor(Color.BLACK);
        if(percentage>100){
            percentage=100;
        }
        textView1.setText(min+"/"+max+"\t\t"+percentage+"%");
        textView1.setTextSize(13);
        textView1.setPadding(0,0,0,0);
        tableRow.addView(textView);
        tableRow.addView(textView1);
        ProgressBar proBar=new ProgressBar(attendanceReport.this,
                null,android.R.attr.progressBarStyleHorizontal);
        proBar.setMin(0);
        proBar.setMax(max);
        proBar.setProgress(min);
        proBar.setPadding(15,0,15,5);
        TextView qw=new TextView(attendanceReport.this);
        qw.setBackgroundColor(Color.BLACK);
        qw.setHeight(5);
        tableLayout.addView(tableRow);
        tableLayout.addView(proBar);
        tableLayout.addView(qw);
    }
    @SuppressLint("SetTextI18n")
    private void report(int qw){
        if(qw==10){
            int newmin=0;
            int newmax=0;
            count =0;
            for(int v = 0; v< minNoList.size(); v++){
                newmax=newmax+ maxNoList.get(v);
                newmin=newmin+ minNoList.get(v);
            }
            int newpercentage;
            if(newmax==0){
                newpercentage=0;
            }else {
                newpercentage =(newmin*100)/newmax;
            }

            TableRow firstRow = new TableRow(attendanceReport.this);
            firstRow.setBackgroundColor(Color.parseColor("#3c415e"));
            TextView textView3=new TextView(attendanceReport.this);
            textView3.setText("Total Attendance ");
            textView3.setTextSize(20);
            textView3.setPadding(35,10,0,15);
            textView3.setWidth(800);
            textView3.setTextColor(Color.WHITE);
            TextView tView2=new TextView(attendanceReport.this);
            tView2.setTextColor(Color.WHITE);
            if(newpercentage>100){
                newpercentage=100;
            }
            tView2.setText(newmin+"/"+newmax+"\t\t"+newpercentage+"%");
            tView2.setTextSize(15);
            tView2.setPadding(0,10,0,15);
            firstRow.addView(textView3);
            firstRow.addView(tView2);
            tableLayout.addView(firstRow);
            newmax=0;
            newmin=0;
            newpercentage=0;
            maxNoList.clear();
            minNoList.clear();
        }
    }


}

