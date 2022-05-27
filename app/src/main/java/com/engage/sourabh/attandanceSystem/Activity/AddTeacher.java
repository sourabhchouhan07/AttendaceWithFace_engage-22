package com.engage.sourabh.attandanceSystem.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.R;

import java.util.Calendar;

public class AddTeacher extends AppCompatActivity {

      private ImageView homeButton, backbtn;
    Button submitBtn;
    private EditText name,degree_t, teacherBOD;
    private EditText number,email,address;
    private Spinner courses;
    private ProgressDialog detectionProgressDialog;
    DatePickerDialog pickDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        courses=findViewById(R.id.course);
        degree_t=findViewById(R.id.degree);
        name=findViewById(R.id.fullname);
        teacherBOD =findViewById(R.id.birtofdate);
        number=findViewById(R.id.number);
        email=findViewById(R.id.email);
        address=findViewById(R.id.addresss);
       submitBtn=findViewById(R.id.submit);
        teacherBOD.setInputType(InputType.TYPE_NULL);


        ArrayAdapter<CharSequence> subject=ArrayAdapter.createFromResource(AddTeacher.this,R.array.SUbject,R.layout.spinner_item);
        subject.setDropDownViewResource(R.layout.spinner_drop_item);

        courses.setAdapter(subject);

        backbtn=findViewById(R.id.backBtn);
        homeButton=findViewById(R.id.home_btn);

        backbtn.setOnClickListener(v->{
            onBackPressed();
        });


        teacherBOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                pickDate = new DatePickerDialog(AddTeacher.this
                        ,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                teacherBOD.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickDate.show();
            }
        });

        //submiting the Details

        submitingDetails();



    }

    private void submitingDetails () {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String BOD= teacherBOD.getText().toString();
                final String numbers=number.getText().toString();
                final String addresss=address.getText().toString();
                final String coureses =courses.getSelectedItem().toString();
                final String fullName = name.getText().toString();
                final String userEmail = email.getText().toString();
                final String degree = degree_t.getText().toString();


                //checking creditianl

                if(userEmail.isEmpty()){
                    email.setError(getString(R.string.emailAlert));
                    email.requestFocus();
                } else if(fullName.isEmpty()){
                    name.setError(getString(R.string.nameAlert));
                    name.requestFocus();
                } else if(degree.isEmpty()){
                    degree_t.setError(getString(R.string.alertDegree));
                    degree_t.requestFocus();
                } else if(BOD.isEmpty()){
                    teacherBOD.setError("Please enter  DOB ");
                    teacherBOD.requestFocus();
                } else if(numbers.isEmpty()){
                    number.setError(getString(R.string.validNo));
                    number.requestFocus();
                } else if(addresss.isEmpty()){
                    address.setError(getString(R.string.address));
                    address.requestFocus();
                } else if(userEmail.length()<6){
                    email.setError(getString(R.string.properEmailAlert));
                    email.requestFocus();
                } else if(fullName.length()<9){
                    name.setError(getString(R.string.fullNameAlert));
                    name.requestFocus();
                } else if(numbers.length()<10){
                    number.setError(getString(R.string.noLength));
                    number.requestFocus();
                } else if(addresss.length()<7){
                    address.setError(getString(R.string.properAddress));
                    address.requestFocus();

                }
                else  if(!(userEmail.isEmpty() && fullName.isEmpty()&&BOD.isEmpty()&&numbers.isEmpty()&&addresss.isEmpty())){
                    Intent intent=new Intent(AddTeacher.this, setpassword.class);

                    intent.putExtra("degree",degree);
                    intent.putExtra("birthofdate",BOD);
                    intent.putExtra("number",numbers);
                    intent.putExtra("address",addresss);
                    intent.putExtra("cource",coureses);
                    intent.putExtra("fullname",fullName);
                    intent.putExtra("email",userEmail);


                    startActivity(intent);
                    teacherBOD.setText("");
                    number.setText("");
                    email.setText("");
                    address.setText("");
                    name.setText("");
                    degree_t.setText("");

                } else {
                    Toast.makeText(AddTeacher.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        detectionProgressDialog = new ProgressDialog(AddTeacher.this);
    }


}