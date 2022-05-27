package com.engage.sourabh.attandanceSystem.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.engage.sourabh.attandanceSystem.Model.notificationSendAndReceive;
import com.engage.sourabh.attandanceSystem.R;

import java.util.List;

public class messagelist extends ArrayAdapter<notificationSendAndReceive> {


    private List<notificationSendAndReceive> messageData;
    private Activity activityContext;



    public messagelist(Activity context, List<notificationSendAndReceive> messagelist){
        super(context, R.layout.resever_msg_list,messagelist);
        this.activityContext =context;
        this.messageData =messagelist;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= activityContext.getLayoutInflater();


        @SuppressLint({"InflateParams", "ViewHolder"})

        View viewItem=inflater.inflate(R.layout.resever_msg_list,null,true);




        TextView sender=viewItem.findViewById(R.id.by);
        TextView time=viewItem.findViewById(R.id.time);
        TextView subject=viewItem.findViewById(R.id.subject);
        TextView message=viewItem.findViewById(R.id.messae);
        TextView timed=viewItem.findViewById(R.id.timed);




        notificationSendAndReceive notificationSendAndReceive = messageData.get(position);



        //setting the MsG details to their View

        sender.setText(notificationSendAndReceive.getSender());
        String timeDate=notificationSendAndReceive.getTime();
        String[] arrOfStr = timeDate.split(" ");
          timed.setText(arrOfStr[0]);
        time.setText(arrOfStr[1]);
        subject.setText(notificationSendAndReceive.getSubject());
        message.setText(notificationSendAndReceive.getMessage());


        return viewItem;
    }
}
