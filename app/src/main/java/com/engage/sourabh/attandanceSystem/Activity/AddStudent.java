package com.engage.sourabh.attandanceSystem.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddStudent extends AppCompatActivity {
     private  Button sumitBtn;
    private String icode="121212";
    private ImageView backBtn;
    private ImageView homeBtn;
    private EditText studentName, studentRoll;
    private EditText studentNo, studentMail, studentDiv;
    private EditText studentDoB,studentaddresss;

    private Spinner studentcourse,studentyear;


    private DatabaseReference studentRef;

    private ProgressBar pbadd;

    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_student);


        initVar();


        studentRef = FirebaseDatabase.getInstance().getReference("Student");
        icode=((global)getApplication()).getInstituteCode();

        studentDoB.setInputType(InputType.TYPE_NULL);

        //backButton fun

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });



        studentDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddStudent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                studentDoB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        //adding data to spinner

        ArrayAdapter<CharSequence> adapterCourse=ArrayAdapter.createFromResource(AddStudent.this,R.array.SUbject,R.layout.spinner_item);
        adapterCourse.setDropDownViewResource(R.layout.spinner_drop_item);


        studentcourse.setAdapter(adapterCourse);



        ArrayAdapter<CharSequence> YearAdapter=ArrayAdapter.createFromResource(AddStudent.this,R.array.yearArray,R.layout.spinner_item);
        adapterCourse.setDropDownViewResource(R.layout.spinner_drop_item);

        studentyear.setAdapter(YearAdapter);


        //Submit button fun

        sumitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               RegisterStudent();

            }
        });
    }

    private void initVar() {
      //initialing the Views

        sumitBtn=findViewById(R.id.studentsubmit);
        backBtn=findViewById(R.id.backBtn);
        homeBtn=findViewById(R.id.home_btn);

        pbadd=findViewById(R.id.progressaddstudent);
        pbadd.setVisibility(View.GONE);

        studentcourse=findViewById(R.id.studentcourse);
        studentyear=findViewById(R.id.studentyear);
        studentName =findViewById(R.id.studentfullname);
        studentRoll =findViewById(R.id.studentrollnumber);
        studentNo =findViewById(R.id.studentnumber);
        studentMail =findViewById(R.id.studentemail);
        studentDiv =findViewById(R.id.studentdivision);
        studentDoB =findViewById(R.id.studentbirtofdate);
        studentaddresss=findViewById(R.id.studentaddresss);
    }

    private void RegisterStudent() {
        pbadd.setVisibility(View.VISIBLE);

        final String division = studentDiv.getText().toString().trim();
        final String stdEmail = studentMail.getText().toString().trim();
        final String nameStudent = studentName.getText().toString().trim();
        final String rollNO = studentRoll.getText().toString().trim();
        final String birthOfDate = studentDoB.getText().toString().trim();

        final String numbers = studentNo.getText().toString().trim();
        final String divisions = studentDiv.getText().toString().trim();
        final String addresses = studentaddresss.getText().toString().trim();
        final String coureses = studentcourse.getSelectedItem().toString();
        final String years = studentyear.getSelectedItem().toString();

//
        if (stdEmail.isEmpty()) {
            studentMail.setError(getString(R.string.emailAlert));
            studentMail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (nameStudent.isEmpty()) {
            studentName.setError(getString(R.string.fullNameAlert));
            studentName.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (rollNO.isEmpty()) {
            studentRoll.setError(getString(R.string.enterRoll));
            studentRoll.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (birthOfDate.isEmpty()) {
            studentDoB.setError(getString(R.string.BOD));
            studentDoB.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (numbers.isEmpty()) {
            studentNo.setError(getString(R.string.enterNo));
            studentNo.requestFocus();
            pbadd.setVisibility(View.GONE);

        }  else if (numbers.length() < 10) {
            studentNo.setError(getString(R.string.validNo));
            studentNo.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (addresses.length() < 7) {
            studentaddresss.setError(getString(R.string.properAddress));
            studentaddresss.requestFocus();
            pbadd.setVisibility(View.GONE);

        }else if (divisions.isEmpty()) {
            studentDiv.setError(getString(R.string.divAlert));
            studentDiv.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (addresses.isEmpty()) {
            studentaddresss.setError(getString(R.string.properAddress));
            studentaddresss.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (stdEmail.length() < 6) {
            studentMail.setError(getString(R.string.properEmailAlert));
            studentMail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (nameStudent.length() < 7) {
            studentName.setError(getString(R.string.fullNameAlert));
            studentName.requestFocus();
            pbadd.setVisibility(View.GONE);

        }
        else if (stdEmail.isEmpty() && nameStudent.isEmpty() && rollNO.isEmpty() && birthOfDate.isEmpty() && numbers.isEmpty() && divisions.isEmpty() && addresses.isEmpty()) {
            Toast.makeText(AddStudent.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
            pbadd.setVisibility(View.GONE);

        } else {
       //sending data to addstudentface activity through intent

            Intent intent = new Intent(AddStudent.this,AddStudentFace.class);
            intent.putExtra("number", numbers);
            intent.putExtra("address", addresses);

            intent.putExtra("rollnumber", rollNO);
            intent.putExtra("birthofdate", birthOfDate);
            intent.putExtra("cource", coureses);
            intent.putExtra("year", years);
            intent.putExtra("division", division);
            intent.putExtra("fullname", nameStudent);
            intent.putExtra("email", stdEmail);
            intent.putExtra("code", icode);

            startActivity(intent);
            finish();




        }
    }


    }
