package com.engage.sourabh.attandanceSystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.engage.sourabh.attandanceSystem.R;


public class StudentHomeFragment extends Fragment {

   CardView attendanceReport,sendMsg,teacherInfo;
    public StudentHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_student_home, container, false);
        attendanceReport=layout.findViewById(R.id.attandance_report);
        sendMsg=layout.findViewById(R.id.send_msg);
        teacherInfo=layout.findViewById(R.id.teacher_detail);







    return layout;}
}