package com.engage.sourabh.attandanceSystem.Adapter;

import android.annotation.SuppressLint;
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

public class teacherList extends ArrayAdapter<addTeacherdatabase> {

    private TextView teacherName, teacherCourse, teaherDOB,teacheremail,teacheraddress;
    private Activity context;
    private List<addTeacherdatabase> teacherinfo;


    public teacherList(Context context, List<addTeacherdatabase> teacherinfo) {
        super(context, R.layout.teacherlist,teacherinfo);
        this.context= (Activity) context;
        this.teacherinfo=teacherinfo;
    }
    
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =context.getLayoutInflater();


            View listViewItem=inflater.inflate(R.layout.teacherlist,null,true);

            //Init. View

            teacherName =listViewItem.findViewById(R.id.teachername);
            teacherCourse =listViewItem.findViewById(R.id.teachercourse);
            teaherDOB =listViewItem.findViewById(R.id.degree);
            teacheremail=listViewItem.findViewById(R.id.teacheremail);
            teacheraddress=listViewItem.findViewById(R.id.teacheraddress);


            addTeacherdatabase addTeacherdatbase=teacherinfo.get(position);

            //setting data to Textview;
            teacherName.setText(addTeacherdatbase.getFullname());
            teacherCourse.setText(addTeacherdatbase.getCourese());
            teaherDOB.setText(addTeacherdatbase.getDegree());
            teacheremail.setText(addTeacherdatbase.getEmail());
            teacheraddress.setText(addTeacherdatbase.getAddresss());




            return listViewItem;
        }
    }
