package com.engage.sourabh.attandanceSystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.engage.sourabh.attandanceSystem.Activity.AddStudent;
import com.engage.sourabh.attandanceSystem.Activity.AttandanceActivity;
import com.engage.sourabh.attandanceSystem.Activity.SendMessage;
import com.engage.sourabh.attandanceSystem.Activity.StudentDetailActivity;
import com.engage.sourabh.attandanceSystem.Activity.TeacherDetailActivity;
import com.engage.sourabh.attandanceSystem.Activity.attendanceReport;
import com.engage.sourabh.attandanceSystem.R;


public class TeacherHomeFragment extends Fragment {
      CardView attendance,attendanceReport,sendSms,studentDetail,teacherDetail,addStudent;


    public TeacherHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View layout= inflater.inflate(R.layout.fragment_teacher_home, container, false);

       attendance=layout.findViewById(R.id.attadance);
        addStudent=layout.findViewById(R.id.add_student);
        attendanceReport=layout.findViewById(R.id.attandance_report);
        teacherDetail=layout.findViewById(R.id.teacher_detail);
       studentDetail =layout.findViewById(R.id.student_detail);
       sendSms=layout.findViewById(R.id.send_msg);

       attendance.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getContext(), AttandanceActivity.class);
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

       studentDetail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent i=new Intent(getContext(), StudentDetailActivity.class);
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
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SendMessage.class);
                startActivity(i);
            }
        });







       return layout;
    }
}