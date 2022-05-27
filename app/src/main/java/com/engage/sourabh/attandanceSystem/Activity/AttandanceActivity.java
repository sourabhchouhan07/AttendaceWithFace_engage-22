package com.engage.sourabh.attandanceSystem.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttandanceActivity extends AppCompatActivity {

       ImageView backBtn;
    private Spinner selectCourses, selectYear, selectSubject;
    private EditText division,starttime,endtime;

    private Button camera,rollnumber;
    int CAMERA_PIC_REQUEST=1;


    private int sub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);

        division=findViewById(R.id.divisionattndanc);
        starttime=findViewById(R.id.starttime);
        endtime=findViewById(R.id.endtime);

        selectCourses =findViewById(R.id.spinner);
        selectYear =findViewById(R.id.spinner2);
        selectSubject =findViewById(R.id.spinner3);



        camera=findViewById(R.id.camera);
        rollnumber=findViewById(R.id.rollnumber);

     backBtn=findViewById(R.id.backBtn);


        backBtn.setOnClickListener(v->{
            onBackPressed();
        });


        TakeTimeForClass();
          //Adding Spinner Data array r
        ArrayAdapter<CharSequence> DataAdapterC=ArrayAdapter.createFromResource(
                AttandanceActivity.this,R.array.SUbject,
                R.layout.spinner_item);

        DataAdapterC.setDropDownViewResource(R.layout.spinner_drop_item);

        selectCourses.setAdapter(DataAdapterC);


        selectCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AttandanceActivity.this,"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });


        ArrayAdapter<CharSequence> DataAdapter1=ArrayAdapter.createFromResource(
                AttandanceActivity.this,R.array.categriory
                ,R.layout.spinner_item);

        DataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);

        selectYear.setAdapter(DataAdapter1);

        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sub == 0 && position == 0){
                    ArrayAdapter<CharSequence> adpter2=ArrayAdapter.createFromResource(AttandanceActivity.this,R.array.FY_1,R.layout.spinner_item);

                    adpter2.setDropDownViewResource(R.layout.spinner_drop_item);

                    selectSubject.setAdapter(adpter2);
                }else if(sub == 0 && position == 1){
                    ArrayAdapter<CharSequence> adpter2 =ArrayAdapter.createFromResource(AttandanceActivity.this,R.array.FY_2,R.layout.spinner_item);

                    adpter2.setDropDownViewResource(R.layout.spinner_drop_item);

                    selectSubject.setAdapter(adpter2);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AttandanceActivity.this,"Error"+parent,Toast.LENGTH_LONG).show();
            }
        });
        List<String> categories2=new ArrayList<>();
        categories2.add("Communication skill");
        categories2.add("Operating");
        ArrayAdapter<String> dataAdapter2=new ArrayAdapter<>(AttandanceActivity.this,android.R.layout.simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSubject.setAdapter(dataAdapter2);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course= selectCourses.getSelectedItem().toString().trim();
                String years= selectYear.getSelectedItem().toString().trim();
                String subjectAttendance= selectSubject.getSelectedItem().toString().trim();
                String divisions=division.getText().toString().trim();
                String sTime=starttime.getText().toString().trim();
                String eTime=endtime.getText().toString().trim();
                if(divisions.isEmpty()){
                    division.setError(getString(R.string.divAlert));
                    division.requestFocus();
                }else if(sTime.isEmpty()){
                    starttime.setError(getString(R.string.enterStime));
                    starttime.requestFocus();
                }else if(eTime.isEmpty()){
                    endtime.setError(getString(R.string.enterStime));
                    endtime.requestFocus();
                }else{

                    Intent intent=new Intent(AttandanceActivity.this, CameraAttendance.class);
                    intent.putExtra("course",course);
                    intent.putExtra("year",years);
                    intent.putExtra("division",divisions);
                    intent.putExtra("subject",subjectAttendance);
                    intent.putExtra("starttime",sTime);
                    intent.putExtra("endtime",eTime);
                    startActivity(intent);
                }
            }
        });
        rollnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String subjectAttendance= selectSubject.getSelectedItem().toString().trim();
                String divisions=division.getText().toString().trim();
                String sTime=starttime.getText().toString().trim();
                String eTime=endtime.getText().toString().trim();


                String courseA= selectCourses.getSelectedItem().toString().trim();
                String yearS= selectYear.getSelectedItem().toString().trim();

                if(divisions.isEmpty()){
                    division.setError(getString(R.string.divAlert));
                    division.requestFocus();
                }else if(sTime.isEmpty()){
                    starttime.setError(getString(R.string.enterStime));
                    starttime.requestFocus();
                }else if(eTime.isEmpty()){
                    endtime.setError(getString(R.string.enterEtime));
                    endtime.requestFocus();
                }else{
                    Intent intent=new Intent(AttandanceActivity.this, manualAttendance.class);
                    intent.putExtra("course",courseA);
                    intent.putExtra("year",yearS);
                    intent.putExtra("division",divisions);
                    intent.putExtra("subject",subjectAttendance);
                    intent.putExtra("starttime",sTime);
                    intent.putExtra("endtime",eTime);
                    startActivity(intent);
                }
            }
        });

    }

    private void TakeTimeForClass() {

        starttime.setInputType(InputType.TYPE_NULL);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                TimePickerDialog picker1;
                picker1 = new TimePickerDialog(AttandanceActivity.this,R.style.MyTimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        starttime.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
                picker1.getWindow().setBackgroundDrawableResource(R.color.grey);
                picker1.show();
            }
        });

        endtime.setInputType(InputType.TYPE_NULL);
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                TimePickerDialog picker1;
                picker1 = new TimePickerDialog(AttandanceActivity.this,R.style.MyTimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        endtime.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
                picker1.getWindow().setBackgroundDrawableResource(R.color.grey);
                picker1.show();
            }
        });
    }
}

