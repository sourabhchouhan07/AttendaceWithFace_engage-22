package com.engage.sourabh.attandanceSystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.engage.sourabh.attandanceSystem.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class attendanceReport extends AppCompatActivity {
    private Spinner searchcource,seachyear;
    private EditText searchroll,searchdiv;
    private Button search;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private List<String> FY_1BSCIT=new ArrayList<String>(10);
    private List<String> FY_2BSCIT=new ArrayList<String>(10);
    private List<String> SY_3BSCIT=new ArrayList<String>(10);
    private List<String> SY_4BSCIT=new ArrayList<String>(10);
    private List<String> TY_5BSCIT=new ArrayList<String>(10);
    private List<String> TY_6BSCIT=new ArrayList<String>(10);

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

        FY_1BSCIT.add("Communication Skill");
        FY_1BSCIT.add("Operating Systems");
        FY_1BSCIT.add("Digital Electronics");
        FY_1BSCIT.add("Imperative Programming");
        FY_1BSCIT.add("Discrete Mathematics");
        FY_1BSCIT.add("Imperative Programming Practical");
        FY_1BSCIT.add("Digital Electronics Practical");
        FY_1BSCIT.add("Operating Systems Practical");
        FY_1BSCIT.add("Discrete Mathematics Practical");
        FY_1BSCIT.add("Communication Skills Practical");

        FY_2BSCIT.add("Object oriented Programming");
        FY_2BSCIT.add("Microprocessor Architecture");
        FY_2BSCIT.add("Web Programming");
        FY_2BSCIT.add("Numerical and Statistical Methods");
        FY_2BSCIT.add("Green Computing");
        FY_2BSCIT.add("Object Oriented Programming Practical");
        FY_2BSCIT.add("Microprocessor Architecture Practical");
        FY_2BSCIT.add("Web Programming Practical");
        FY_2BSCIT.add("Numerical & Statistical Methods Practical");
        FY_2BSCIT.add("Green Computing Practical");

        SY_3BSCIT.add("Python Programming");
        SY_3BSCIT.add("Data Structures");
        SY_3BSCIT.add("Computer Networks");
        SY_3BSCIT.add("Database Management Systems");
        SY_3BSCIT.add("Applied Mathematics");
        SY_3BSCIT.add("Python Programming Practical");
        SY_3BSCIT.add("Data Structures Practical");
        SY_3BSCIT.add("Computer Networks Practical");
        SY_3BSCIT.add("Database Management Systems Practical");
        SY_3BSCIT.add("Mobile Programming Practical");

        SY_4BSCIT.add("Core Java");
        SY_4BSCIT.add("Introduction to Embedded Systems");
        SY_4BSCIT.add("Computer Oriented Statistical Techniques");
        SY_4BSCIT.add("Software Engineering");
        SY_4BSCIT.add("Computer Graphics and Animation");
        SY_4BSCIT.add("Core Java Practical");
        SY_4BSCIT.add("Introduction to ES Practical");
        SY_4BSCIT.add("COST Practical");
        SY_4BSCIT.add("Software Engineering Practical");
        SY_4BSCIT.add("Computer Graphics and Animation Practical");

        TY_5BSCIT.add("Software Project Management");
        TY_5BSCIT.add("Internet of Things");
        TY_5BSCIT.add("Advanced Web Programming");
        TY_5BSCIT.add("AI/Linux Admin.");
        TY_5BSCIT.add("E Java/NGT");
        TY_5BSCIT.add("Project Dissertation");
        TY_5BSCIT.add("Internet of Things Practical");
        TY_5BSCIT.add("Advanced Web Programming Practical");
        TY_5BSCIT.add("AI/Linux Admin. Practical");
        TY_5BSCIT.add("E Java/NGT Practical");

        TY_6BSCIT.add("Software Quality Assurance");
        TY_6BSCIT.add("Security in Computing");
        TY_6BSCIT.add("Business Intelligence");
        TY_6BSCIT.add("Principles of GIS/EN");
        TY_6BSCIT.add("IT Service Management/Cyber Laws");
        TY_6BSCIT.add("Project Implementation");
        TY_6BSCIT.add("Security in Computing Practical");
        TY_6BSCIT.add("Business Intelligence Practical");
        TY_6BSCIT.add("Principles of GIS/EN Practical");
        TY_6BSCIT.add("Advanced Mobile Programming");


        searchcource=findViewById(R.id.searchspinner);
        seachyear=findViewById(R.id.searchspinner2);
        searchroll=findViewById(R.id.searchroll);
        searchdiv=findViewById(R.id.searchdivision);
        search=findViewById(R.id.search);


        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(attendanceReport.this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        searchcource.setAdapter(dataAdapter);

        final List<String> categories1 = new ArrayList<String>();
        categories1.add("FY_1");
        categories1.add("FY_2");
        categories1.add("SY_3");
        categories1.add("SY_4");
        categories1.add("TY_5");
        categories1.add("TY_6");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(attendanceReport.this, R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        seachyear.setAdapter(dataAdapter1);

        searchcource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                main = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(attendanceReport.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        seachyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(attendanceReport.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tableLayout.removeAllViews();
//                final String searchroll1=searchroll.getText().toString().trim();
//                final String searchdiv1=searchdiv.getText().toString().trim();
//                final String searchcource1=searchcource.getSelectedItem().toString().trim();
//                final String searchyear1=seachyear.getSelectedItem().toString().trim();
//                if(searchroll1.isEmpty()){
//                    searchroll.setError("please enter Roll number");
//                    searchroll.requestFocus();
//                }else if(searchdiv1.isEmpty()){
//                    searchdiv.setError("please enter Roll number");
//                    searchdiv.requestFocus();
//                }else{
//                    tableLayout.removeAllViews();
//                    if(main==0&&sub==0){
//                        for(int i=0;i<=FY_1BSCIT.size()-1;i++){
//                            Log.d("report","Report"+FY_1BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,FY_1BSCIT.get(i));
//                        }
//                    }else if(main==0&&sub==1){
//                        for(int i=0;i<=FY_2BSCIT.size()-1;i++){
//                            Log.d("report","Report"+FY_2BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,FY_2BSCIT.get(i));
//                        }
//                    }else if(main==0&&sub==2){
//                        for(int i=0;i<=SY_3BSCIT.size()-1;i++){
//                            Log.d("report","Report"+SY_3BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,SY_3BSCIT.get(i));
//                        }
//                    }else if(main==0&&sub==3){
//                        for(int i=0;i<=SY_4BSCIT.size()-1;i++){
//                            Log.d("report","Report"+SY_4BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,SY_4BSCIT.get(i));
//                        }
//                    }else if(main==0&&sub==4){
//                        for(int i=0;i<=TY_5BSCIT.size()-1;i++){
//                            Log.d("report","Report"+TY_5BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,TY_5BSCIT.get(i));
//                        }
//                    }else if(main==0&&sub==5){
//                        for(int i=0;i<=TY_6BSCIT.size()-1;i++){
//                            Log.d("report","Report"+TY_6BSCIT.get(i));
//                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,TY_6BSCIT.get(i));
//                        }
//                    }
//                }
//            }
//        });
        tableLayout=findViewById(R.id.list);
    }
}