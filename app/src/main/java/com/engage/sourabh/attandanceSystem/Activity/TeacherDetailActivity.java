package com.engage.sourabh.attandanceSystem.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.addTeacherdatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.engage.sourabh.attandanceSystem.Adapter.teacherList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherDetailActivity extends AppCompatActivity {
    ListView listView;
    String icode="";
    DatabaseReference notice;
    ImageView backBtn;
    List<addTeacherdatabase> teacherinfo;
    ProgressBar detailtpb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        listView=findViewById(R.id.teacherinfo);
        detailtpb=findViewById(R.id.detailTeacherpb);
        icode=((global)getApplication()).getInstituteCode();

        notice = FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/Teacher");
        teacherinfo=new ArrayList<>();
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        detailtpb.setVisibility(View.VISIBLE);
        notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot info:dataSnapshot.getChildren()){
                    addTeacherdatabase showinfo=info.getValue(addTeacherdatabase.class);
                    teacherinfo.add(showinfo);
                }
                teacherList adapter = new teacherList(TeacherDetailActivity.this,teacherinfo);
                listView.setAdapter(adapter);
                detailtpb.setVisibility(View.GONE);

                if(teacherinfo.isEmpty()){

                    Toast.makeText(TeacherDetailActivity.this, "No teacher is register", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(TeacherDetailActivity.this,"error"+error,Toast.LENGTH_LONG).show();
                detailtpb.setVisibility(View.GONE);
            }
        });
    }
}