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



    private List<Integer> minmum=new ArrayList<>();
    private List<Integer> manmum=new ArrayList<>();
    private int sub,main;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private int num=0;
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
                main = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(attendanceReport.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub=position;
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
                final String searchroll1=searchroll.getText().toString().trim();
                final String searchdiv1= SelectDiv.getText().toString().trim();
                final String searchcource1= selectCourse.getSelectedItem().toString().trim();
                final String searchyear1= selectYear.getSelectedItem().toString().trim();
                if(searchroll1.isEmpty()){
                    searchroll.setError("please enter Roll number");
                    searchroll.requestFocus();
                }else if(searchdiv1.isEmpty()){
                    SelectDiv.setError("please enter Roll number");
                    SelectDiv.requestFocus();
                }else{
                    tableLayout.removeAllViews();
                    if(main==0&&sub==0){
                        for(int i = 0; i<= Constants.FY1_BSC.size()-1; i++){

                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,Constants.FY1_BSC.get(i));
                        }
                    }else if(main==0&&sub==1){
                        for(int i = 0; i<= Constants.FY2_BSC.size()-1; i++){

                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1, Constants.FY2_BSC.get(i));
                        }
                    }
                }
            }
        });
        tableLayout=findViewById(R.id.list);

    }


    private void attandance(final String searchcource1,
                            final String searchyear1,
                            final String searchdiv1,
                            String searchroll1,
                            final String dataclass){
         String icode=((global)getApplication()).getInstituteCode();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("institutes/"+icode);
        databaseRef = dbref.child("Attandance/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+searchroll1+"/"+dataclass);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    final long child=dataSnapshot.getChildrenCount();
                    Log.d("report","mainchild----"+child);
                    final int minchild=(int)child;
                    databaseRef2 = dbref.child("Attandancedetail/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+dataclass);
                    databaseRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                num=num+1;
                                long tchild=dataSnapshot.getChildrenCount();
                                Log.d("report","subchild----"+tchild);
                                Log.d("att","num"+String.valueOf(num));
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
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("report","Error--"+error);
                            Toast.makeText(attendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    num=num+1;
                    Log.d("att","num"+String.valueOf(num));
                    int i=0,j=0;
                    progress(dataclass,i,j);
                    Log.d("report","student Not child");
                    report(num);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("report","Error--"+error);
                Toast.makeText(attendanceReport.this,"Error--"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void progress(String i, int min, int max){
        minmum.add(min);
        manmum.add(max);
        tableRow = new TableRow(attendanceReport.this);
        TextView tv=new TextView(attendanceReport.this);
        tv.setText(i);
        tv.setTextSize(20);
        tv.setPadding(5,0,0,0);
        tv.setWidth(800);
        tv.setTextColor(Color.BLACK);
        TextView tv1=new TextView(attendanceReport.this);
        int percentage;
        if(max==0){
            percentage=0;
        }else {
            percentage =(min*100)/max;
        }
        tv1.setTextColor(Color.BLACK);
        if(percentage>100){
            percentage=100;
        }
        tv1.setText(min+"/"+max+"\t\t"+percentage+"%");
        tv1.setTextSize(13);
        tv1.setPadding(0,0,0,0);
        tableRow.addView(tv);
        tableRow.addView(tv1);
        ProgressBar pb=new ProgressBar(attendanceReport.this,
                null,android.R.attr.progressBarStyleHorizontal);
        pb.setMin(0);
        pb.setMax(max);
        pb.setProgress(min);
        pb.setPadding(15,0,15,5);
        TextView qw=new TextView(attendanceReport.this);
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
            TableRow tableRow1 = new TableRow(attendanceReport.this);
            tableRow1.setBackgroundColor(Color.parseColor("#3c415e"));
            TextView tv3=new TextView(attendanceReport.this);
            tv3.setText("Total Attandance");
            tv3.setTextSize(20);
            tv3.setPadding(35,10,0,15);
            tv3.setWidth(800);
            tv3.setTextColor(Color.WHITE);
            TextView tv2=new TextView(attendanceReport.this);
            tv2.setTextColor(Color.WHITE);
            if(newpercentage>100){
                newpercentage=100;
            }
            tv2.setText(newmin+"/"+newmax+"\t\t"+newpercentage+"%");
            tv2.setTextSize(15);
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

