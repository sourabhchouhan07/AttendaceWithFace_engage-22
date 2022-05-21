package com.engage.sourabh.attandanceSystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class AddStudent extends AppCompatActivity {
     Button sumitBtn;
    String icode="121212";
    private EditText studentfullname, studentrollnumber,studentnumber,studentemail,studentdivision,studentbirtofdate,studentaddresss;
    private TextView faceidstudent;
    private ImageButton studentimage;
    private Spinner studentcourse,studentyear;
    private DatabaseReference studentRef;
    private ProgressDialog detectionProgressDialog;
    private FirebaseAuth mAuth1;
    private DatabaseReference profileRef;
    private ProgressBar pbadd;

    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        sumitBtn=findViewById(R.id.studentsubmit);


        pbadd=findViewById(R.id.progressaddstudent);
        pbadd.setVisibility(View.GONE);

        studentcourse=findViewById(R.id.studentcourse);
        studentyear=findViewById(R.id.studentyear);
        studentfullname=findViewById(R.id.studentfullname);
        studentrollnumber=findViewById(R.id.studentrollnumber);
        studentnumber=findViewById(R.id.studentnumber);
        studentemail=findViewById(R.id.studentemail);
        studentdivision=findViewById(R.id.studentdivision);
        studentbirtofdate=findViewById(R.id.studentbirtofdate);
        studentaddresss=findViewById(R.id.studentaddresss);
        studentRef = FirebaseDatabase.getInstance().getReference("Student");


            icode=((global)getApplication()).getInstituteCode();



        studentbirtofdate.setInputType(InputType.TYPE_NULL);
        studentbirtofdate.setOnClickListener(new View.OnClickListener() {
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
                                studentbirtofdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddStudent.this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        studentcourse.setAdapter(dataAdapter);

        List<String> categories1 = new ArrayList<String>();
        categories1.add("FY");
        categories1.add("SY");
        categories1.add("TY");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddStudent.this, R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        studentyear.setAdapter(dataAdapter1);

        sumitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               RegisterStudent();

            }
        });
    }

    private void RegisterStudent() {
        pbadd.setVisibility(View.VISIBLE);

        final String emai = studentemail.getText().toString().trim();
        final String fullnames = studentfullname.getText().toString().trim();
        final String rollnumbers = studentrollnumber.getText().toString().trim();
        final String birthofdates = studentbirtofdate.getText().toString().trim();
        final String numbers = studentnumber.getText().toString().trim();
        final String divisions = studentdivision.getText().toString().trim();
        final String addresss = studentaddresss.getText().toString().trim();
        final String coureses = studentcourse.getSelectedItem().toString();
        final String years = studentyear.getSelectedItem().toString();
        final String division = studentdivision.getText().toString().trim();
//
        if (emai.isEmpty()) {
            studentemail.setError("Please enter email id");
            studentemail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (fullnames.isEmpty()) {
            studentfullname.setError("Please enter FullName id");
            studentfullname.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (rollnumbers.isEmpty()) {
            studentrollnumber.setError("Please enter roll number");
            studentrollnumber.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (birthofdates.isEmpty()) {
            studentbirtofdate.setError("Please enter birth date");
            studentbirtofdate.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (numbers.isEmpty()) {
            studentnumber.setError("Please enter number");
            studentnumber.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (divisions.isEmpty()) {
            studentdivision.setError("Please enter division");
            studentdivision.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (addresss.isEmpty()) {
            studentaddresss.setError("Please enter address");
            studentaddresss.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (emai.length() < 10) {
            studentemail.setError("Please enter proper email id");
            studentemail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (fullnames.length() < 9) {
            studentfullname.setError("Please enter FullName");
            studentfullname.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (numbers.length() < 10) {
            studentnumber.setError("Please enter valid number");
            studentnumber.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (addresss.length() < 7) {
            studentaddresss.setError("Please enter proper addrresss");
            studentaddresss.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (!isValid(emai)) {
            studentemail.setError("Email not valid");
            studentemail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (emai.isEmpty() && fullnames.isEmpty() && rollnumbers.isEmpty() && birthofdates.isEmpty() && numbers.isEmpty() && divisions.isEmpty() && addresss.isEmpty()) {
            Toast.makeText(AddStudent.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
            pbadd.setVisibility(View.GONE);

        } else {

            Intent intent = new Intent(AddStudent.this,AddStudentFace.class);
            intent.putExtra("number", numbers);
            intent.putExtra("address", addresss);
            intent.putExtra("cource", coureses);
            intent.putExtra("year", years);
            intent.putExtra("division", division);
            intent.putExtra("fullname", fullnames);
            intent.putExtra("email", emai);
            intent.putExtra("rollnumber", rollnumbers);
            intent.putExtra("birthofdate", birthofdates);
            intent.putExtra("code", icode);

            startActivity(intent);
            studentbirtofdate.setText("");
            studentnumber.setText("");
            studentaddresss.setText("");
            studentdivision.setText("");
            studentfullname.setText("");
            studentemail.setText("");


            studentrollnumber.setText("");


        }
    }
    public static boolean isValid(String email) {
        String emailcheck = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailcheck);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    }
