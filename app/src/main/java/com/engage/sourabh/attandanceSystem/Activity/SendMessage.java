package com.engage.sourabh.attandanceSystem.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.notificationSendAndReceive;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendMessage extends AppCompatActivity {
    private EditText subject,message;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private String sendername;
    ImageView backBtn;
    private ProgressBar pb1;
    private  String iCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        final Button sendbutton =findViewById(R.id.send);
        subject=findViewById(R.id.subject);
        message=findViewById(R.id.message);
        pb1=findViewById(R.id.sendprogressbar);
        pb1.setVisibility(View.GONE);
        sendername=((global)getApplication()).getFullname();
        Log.d("send","sederid"+sendername);

        if(sendername==null){
            sendername="default";
        }
        sendbutton.setEnabled(true);
        subject.setEnabled(true);
        message.setEnabled(true);
           iCode=((global)getApplication()).getInstituteCode();
        notice = FirebaseDatabase.getInstance().getReference("institutes/"+iCode+"/Message");

        final Spinner spinner = findViewById(R.id.studentcourse);
        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SendMessage.this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spinner.setAdapter(dataAdapter);

        final Spinner spinner2 =findViewById(R.id.studentyear);
        List<String> categories2 = new ArrayList<String>();
        categories2.add("FY");
        categories2.add("SY");
        categories2.add("TY");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SendMessage.this, R.layout.spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
        spinner2.setAdapter(dataAdapter2);



        //backpress functionality
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbutton.setEnabled(false);
                subject.setEnabled(false);
                message.setEnabled(false);
                pb1.setVisibility(View.VISIBLE);
                String courese = spinner.getSelectedItem().toString();
                String year = spinner2.getSelectedItem().toString();
                String subjec =  subject.getText().toString().trim();
                String messag = message.getText().toString().trim();
                String id= notice.push().getKey();
                if(subjec.isEmpty()){
                    subject.setError("Please enter Subject");
                    subject.requestFocus();
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }else if(messag.isEmpty()){
                    message.setError("Please enter Message");
                    message.requestFocus();
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }else if(sendername.isEmpty()){
                    sendername="Default";
                }else {
                    @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy ");
                    String currentDateandTime = sdf.format(new Date()).trim();
                    notificationSendAndReceive notificationSendAndReceive = new notificationSendAndReceive(subjec,messag,sendername,currentDateandTime,courese+year);
                    notice.child(id).setValue(notificationSendAndReceive);
                    Toast.makeText(SendMessage.this,"Succcessful", Toast.LENGTH_SHORT).show();
                    subject.setText(" ");
                    message.setText(" ");
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }
            }
        });

    }
}