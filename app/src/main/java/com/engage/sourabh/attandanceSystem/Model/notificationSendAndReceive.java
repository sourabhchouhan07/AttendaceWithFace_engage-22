package com.engage.sourabh.attandanceSystem.Model;

public class notificationSendAndReceive {
    private String Subject,Message,Sender,Time,Fro;


    public notificationSendAndReceive() {
    }

    public notificationSendAndReceive(String subject, String message, String sender, String time,String fro) {
        Subject = subject;
        Message = message;
        Sender = sender;
        Time = time;
        Fro =fro;
    }

    public String getFro() {
        return Fro;
    }

    public void setFro(String fro) {
        Fro = fro;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSubject() {
        return Subject;
    }

    public String getMessage() {
        return Message;
    }

    public String getSender() {
        return Sender;
    }

    public String getTime() {
        return Time;
    }
}
