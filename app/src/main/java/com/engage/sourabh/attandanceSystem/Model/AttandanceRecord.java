package com.engage.sourabh.attandanceSystem.Model;

public class AttandanceRecord {
    private String Starttime,Endtime;

    public AttandanceRecord(String starttime, String endtime) {
        Starttime = starttime;
        Endtime = endtime;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }
}
