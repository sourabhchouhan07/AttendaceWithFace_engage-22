package com.engage.sourabh.attandanceSystem.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engage.sourabh.attandanceSystem.Activity.AddStudent;
import com.engage.sourabh.attandanceSystem.Activity.AddTeacher;
import com.engage.sourabh.attandanceSystem.Activity.SendMessage;
import com.engage.sourabh.attandanceSystem.Activity.StudentDetailActivity;
import com.engage.sourabh.attandanceSystem.Activity.TeacherDetailActivity;
import com.engage.sourabh.attandanceSystem.Activity.attendanceReport;
import com.engage.sourabh.attandanceSystem.Model.AttandanceRecord;
import com.engage.sourabh.attandanceSystem.R;


public class InstituteHomeFragment extends Fragment {
       CardView addTeacher,attendanceReport,addStudent,studentDetail,teacherDetail,sendMsg;

    public InstituteHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_institute_home, container, false);
          addStudent=layout.findViewById(R.id.add_student);
          addTeacher=layout.findViewById(R.id.add_teacher);
          attendanceReport=layout.findViewById(R.id.attandance_report);
          studentDetail=layout.findViewById(R.id.student_detail);
          teacherDetail=layout.findViewById(R.id.teacher_detail);
          sendMsg=layout.findViewById(R.id.send_msg);

          addTeacher.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i=new Intent(getContext(), AddTeacher.class);
                  startActivity(i);
              }
          });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddStudent.class);
                startActivity(i);
            }
        });


        attendanceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), attendanceReport.class);
                startActivity(i);

            }
        });
        teacherDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), TeacherDetailActivity.class);
                startActivity(i);

            }
        });
        studentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), StudentDetailActivity.class);

                startActivity(i);
            }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SendMessage.class);
                startActivity(i);
            }
        });


        return layout;}
}