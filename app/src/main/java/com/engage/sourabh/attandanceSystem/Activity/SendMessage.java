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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.notificationSendAndReceive;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessage extends AppCompatActivity {
    private EditText subject,message;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private String senderName;
    ImageView backBtn;
    private ProgressBar proBar;
    private  String iCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);


        final Button sendMessage =findViewById(R.id.send);
        subject=findViewById(R.id.subject);
        message=findViewById(R.id.message);
        proBar =findViewById(R.id.sendprogressbar);
        proBar.setVisibility(View.GONE);
        senderName =((global)getApplication()).getFullname();




        if(senderName ==null){
            senderName ="Institute";
        }
        sendMessage.setEnabled(true);
        subject.setEnabled(true);
        message.setEnabled(true);
           iCode=((global)getApplication()).getInstituteCode();
        notice = FirebaseDatabase.getInstance().getReference("institutes/"+iCode+"/Message");

        final Spinner spinner = findViewById(R.id.studentcourse);

        ArrayAdapter<CharSequence> courseAdapter=ArrayAdapter.createFromResource(SendMessage.this,R.array.SUbject,R.layout.spinner_item);
        courseAdapter.setDropDownViewResource(R.layout.spinner_drop_item);

        spinner.setAdapter(courseAdapter);




        final Spinner spinner2 =findViewById(R.id.studentyear);


        ArrayAdapter<CharSequence> spinnerData=ArrayAdapter.createFromResource(SendMessage.this,R.array.yearArray,R.layout.spinner_item);
        spinnerData.setDropDownViewResource(R.layout.spinner_drop_item);

        spinner2.setAdapter(spinnerData);




        //backpress functionality
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage.setEnabled(false);
                subject.setEnabled(false);
                message.setEnabled(false);
                proBar.setVisibility(View.VISIBLE);
                String course = spinner.getSelectedItem().toString();
                String year = spinner2.getSelectedItem().toString();
                String subJect =  subject.getText().toString().trim();
                String msg = message.getText().toString().trim();
                String userId= notice.push().getKey();
                if(subJect.isEmpty()){
                    subject.setError(getString(R.string.subjectAlert));
                    subject.requestFocus();
                    sendMessage.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    proBar.setVisibility(View.GONE);
                }else if(msg.isEmpty()){
                    message.setError(getString(R.string.msgAlert));
                    message.requestFocus();
                    sendMessage.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    proBar.setVisibility(View.GONE);
                }else if(senderName.isEmpty()){
                    senderName ="Institute";
                }else {
                    @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy ");
                    String currentDateandTime = sdf.format(new Date()).trim();
                    notificationSendAndReceive notificationSendAndReceive = new notificationSendAndReceive(subJect,msg, senderName ,currentDateandTime,course+year);
                    notice.child(userId).setValue(notificationSendAndReceive);
                    Toast.makeText(SendMessage.this,"Successfully Send Msg", Toast.LENGTH_SHORT).show();
                    subject.setText(" ");
                    message.setText(" ");
                    sendMessage.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    proBar.setVisibility(View.GONE);
                }
            }
        });

    }
}