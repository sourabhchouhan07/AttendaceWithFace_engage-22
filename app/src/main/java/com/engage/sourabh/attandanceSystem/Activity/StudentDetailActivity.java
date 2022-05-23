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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity {
    private EditText detailrollnumber,detaildivison;
    private TextView detailfull_name,detailshowcource,detailBirthofdate,detailroll,detailContact,detailmail,detailaddress,divisionstudent,uidstudent;
    private ImageView detailprofile;
    private Button detailshow;
    private Spinner detailcource,detailyear;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private ProgressBar detailstudentpb;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        detailrollnumber=findViewById(R.id.detailrollnumber);
        detaildivison=findViewById(R.id.detaildivison);

        detailfull_name=findViewById(R.id.detailfull_name);
        detailshowcource=findViewById(R.id.detailshowcource);
        detailBirthofdate=findViewById(R.id.detailBirthofdate);
        detailroll=findViewById(R.id.detailroll);
        detailContact=findViewById(R.id.detailContact);
        detailmail=findViewById(R.id.detailmail);
        detailaddress=findViewById(R.id.detailaddress);
        uidstudent=findViewById(R.id.uidstudent);
        divisionstudent=findViewById(R.id.divisionstudent);

        detailprofile=findViewById(R.id.detailprofile);
        detailshow=findViewById(R.id.detailshow);

        detailcource=findViewById(R.id.detailcource);
        detailyear=findViewById(R.id.detailyear);

        detailstudentpb=findViewById(R.id.progressdetail);
        detailstudentpb.setVisibility(View.GONE);


        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(StudentDetailActivity.this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        detailcource.setAdapter(dataAdapter);

        List<String> categories1 = new ArrayList<String>();
        categories1.add("FY");
        categories1.add("SY");
        categories1.add("TY");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(StudentDetailActivity.this, R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        detailyear.setAdapter(dataAdapter1);

        detailshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailstudentpb.setVisibility(View.VISIBLE);
                String rollnumberlocal=detailrollnumber.getText().toString().trim();
                final String divisionlocal=detaildivison.getText().toString().trim();
                final String coureseslocal =detailcource.getSelectedItem().toString();
                final String yearslocal = detailyear.getSelectedItem().toString();
                if(!(rollnumberlocal.isEmpty())){
                    if(divisionlocal.isEmpty()){
                        detaildivison.setError("Please enter division");
                        detaildivison.requestFocus();
                        detailstudentpb.setVisibility(View.GONE);
                    }else {
                        String icode=((global)getApplication()).getInstituteCode();
                        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/student");
                        notice = dbref.child(coureseslocal+"/"+yearslocal+"/"+divisionlocal+"/"+rollnumberlocal);
                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        notice.addValueEventListener(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    addstudentdatabase showinfo=dataSnapshot.getValue(addstudentdatabase.class);

                                    if(showinfo==null){
                                        Toast.makeText(StudentDetailActivity.this,"Student Doesn't exit",Toast.LENGTH_LONG).show();
                                    }
                                    assert showinfo != null;
                                    if(showinfo!=null){
                                        detailfull_name.setText(showinfo.getFullName());
                                        detailshowcource.setText(showinfo.getCourse());
                                        detailBirthofdate.setText(showinfo.getBrithofDate());
                                        detailContact.setText(showinfo.getNumber());
                                        detailmail.setText(showinfo.getEmail());
                                        detailaddress.setText(showinfo.getAddress());
                                        detailroll.setText(showinfo.getRollNumber());
                                        String uids=showinfo.getUid();
                                        uidstudent.setText(uids);
                                        divisionstudent.setText(showinfo.getDivision());
                                        detailstudentpb.setVisibility(View.GONE);

                                    }else {
                                        Toast.makeText(StudentDetailActivity.this,"First enter student",Toast.LENGTH_LONG).show();
                                        detailfull_name.setText("---- ----");
                                        detailshowcource.setText("----");
                                        detailBirthofdate.setText("----");
                                        detailContact.setText("----------");
                                        detailmail.setText("---------------");
                                        detailaddress.setText("-------------\n------");
                                        detailroll.setText("--");
                                        uidstudent.setText("------------");
                                        divisionstudent.setText("--");
                                        detailstudentpb.setVisibility(View.GONE);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(StudentDetailActivity.this,"Error"+error,Toast.LENGTH_LONG).show();
                                detailstudentpb.setVisibility(View.GONE);
                            }
                        });
                    }
                }else {
                    Toast.makeText(StudentDetailActivity.this,"please fill field detail",Toast.LENGTH_LONG).show();
                    detailrollnumber.requestFocus();
                    detailstudentpb.setVisibility(View.GONE);
                }
            }
        });

    }
}