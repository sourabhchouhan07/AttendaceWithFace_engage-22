package com.engage.sourabh.attandanceSystem.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.addstudentdatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity {
    private EditText rollnumberDetail, divisonDetail;
    private TextView fullNameDetail, showCourseDetail, BODdetail, rolldetail, contactDetail, mailDetail, addressDetail, studentDivDetail,uidstudent;
    private ImageView profileDetail,backBtn;
    private Button showDetail;
    private Spinner courseDetail, yearDetail;
    private DatabaseReference notice;

    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);



        showCourseDetail =findViewById(R.id.detailshowcource);
        BODdetail =findViewById(R.id.detailBirthofdate);
        rolldetail =findViewById(R.id.detailroll);
        contactDetail =findViewById(R.id.detailContact);
        mailDetail =findViewById(R.id.detailmail);
        addressDetail =findViewById(R.id.detailaddress);
        uidstudent=findViewById(R.id.uidstudent);
        studentDivDetail =findViewById(R.id.divisionstudent);
        rollnumberDetail =findViewById(R.id.detailrollnumber);
        divisonDetail =findViewById(R.id.detaildivison);
        profileDetail =findViewById(R.id.detailprofile);
        showDetail =findViewById(R.id.detailshow);
        courseDetail =findViewById(R.id.detailcource);
        yearDetail =findViewById(R.id.detailyear);
        fullNameDetail =findViewById(R.id.detailfull_name);

        progressbar =findViewById(R.id.progressdetail);
        progressbar.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> courseAdapter=ArrayAdapter.createFromResource(StudentDetailActivity.this,R.array.SUbject,R.layout.spinner_item);
        courseAdapter.setDropDownViewResource(R.layout.spinner_drop_item);


        courseDetail.setAdapter(courseAdapter);

        List<String> categories1 = new ArrayList<String>();
        categories1.add("FY");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(StudentDetailActivity.this, R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        yearDetail.setAdapter(dataAdapter1);

        showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                String rollnumberlocal= rollnumberDetail.getText().toString().trim();
                final String divisionlocal= divisonDetail.getText().toString().trim();
                final String coureseslocal = courseDetail.getSelectedItem().toString();
                final String yearslocal = yearDetail.getSelectedItem().toString();
                if(!(rollnumberlocal.isEmpty())){
                    if(divisionlocal.isEmpty()){
                        divisonDetail.setError("Please enter division");
                        divisonDetail.requestFocus();
                        progressbar.setVisibility(View.GONE);
                    }else {
                        String icode=((global)getApplication()).getInstituteCode();
                        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/student");
                        notice = dbref.child(coureseslocal+"/"+yearslocal+"/"+divisionlocal+"/"+rollnumberlocal);

                        notice.addValueEventListener(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    addstudentdatabase showinfo=dataSnapshot.getValue(addstudentdatabase.class);

                                    if(showinfo==null){
                                        Toast.makeText(StudentDetailActivity.this,"Student Doesn't exit",Toast.LENGTH_LONG).show();
                                    }

                                    if(showinfo!=null){
                                        fullNameDetail.setText(showinfo.getFullName());
                                        showCourseDetail.setText(showinfo.getCourse());
                                        BODdetail.setText(showinfo.getBrithofDate());
                                        contactDetail.setText(showinfo.getNumber());
                                        mailDetail.setText(showinfo.getEmail());
                                        addressDetail.setText(showinfo.getAddress());
                                        rolldetail.setText(showinfo.getRollNumber());
                                        String uids=showinfo.getUid();
                                        uidstudent.setText(uids);
                                        studentDivDetail.setText(showinfo.getDivision());
                                        progressbar.setVisibility(View.GONE);

                                    }else {
                                        Toast.makeText(StudentDetailActivity.this,"This Roll no. student is not register",Toast.LENGTH_LONG).show();
                                        fullNameDetail.setText("---- ----");
                                        showCourseDetail.setText("----");
                                        BODdetail.setText("----");
                                        contactDetail.setText("----------");
                                        mailDetail.setText("---------------");
                                        addressDetail.setText("-------------\n------");
                                        rolldetail.setText("--");
                                        uidstudent.setText("------------");
                                        studentDivDetail.setText("--");
                                        progressbar.setVisibility(View.GONE);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(StudentDetailActivity.this,"Error"+error,Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                        });
                    }
                }else {
                    Toast.makeText(StudentDetailActivity.this,getString(R.string.enterDetails),Toast.LENGTH_LONG).show();
                    rollnumberDetail.requestFocus();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
  //backPress fun
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

    }
}