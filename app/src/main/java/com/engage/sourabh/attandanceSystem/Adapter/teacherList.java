package com.engage.sourabh.attandanceSystem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.engage.sourabh.attandanceSystem.Model.addTeacherdatabase;
import com.engage.sourabh.attandanceSystem.R;

import java.util.List;

public class teacherList extends ArrayAdapter <addTeacherdatabase> {


    private List <addTeacherdatabase> teacherDataList;
    private TextView teacherName, teacherCourse, teaherDOB;
    private TextView emailOfTeacher, addressOfTeacher;
    private Activity context;
    private int pos;

    public teacherList(Context context , List <addTeacherdatabase> teacherDataList) {
        super(context , R.layout.teacherlist , teacherDataList);
        this.context = (Activity) context;
        this.teacherDataList = teacherDataList;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();


        View viewItem = inflater.inflate(R.layout.teacherlist , null , true);

        //Init. View
        pos = position;
        teacherName = viewItem.findViewById(R.id.teachername);
        teacherCourse = viewItem.findViewById(R.id.teachercourse);
        teaherDOB = viewItem.findViewById(R.id.degree);
        emailOfTeacher = viewItem.findViewById(R.id.teacheremail);
        addressOfTeacher = viewItem.findViewById(R.id.teacheraddress);


        //setting data to Textview;

        setTextView();


        return viewItem;
    }


    private void setTextView() {
        addTeacherdatabase teacherData = teacherDataList.get(pos);
        teacherName.setText(teacherData.getFullname());
        teacherCourse.setText(teacherData.getCourese());
        teaherDOB.setText(teacherData.getDegree());
        emailOfTeacher.setText(teacherData.getEmail());
        addressOfTeacher.setText(teacherData.getAddresss());
    }
}
