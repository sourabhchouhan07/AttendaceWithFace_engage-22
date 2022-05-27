package com.engage.sourabh.attandanceSystem.Model;

public class profiledatabase {


    private String code, courece, division;
    private String Fullname;
    private String email;
    private String birthofdate;
    private String numbers;
    private String course;
    private String uid;
    private String addresss;
    private String password;
    private String userType;
    private String rollnumber;
    private String year;
    private String degree;


    public profiledatabase() {
    }


    public profiledatabase(String code , String fullname , String email , String birthofdate , String numbers , String uid , String addresss , String password , String userType , String rollnumber , String course , String year , String degree , String courece , String division) {
        Fullname = fullname;
        this.code = code;
        this.email = email;
        this.birthofdate = birthofdate;
        this.numbers = numbers;
        this.uid = uid;
        this.addresss = addresss;
        this.password = password;
        this.userType = userType;
        this.rollnumber = rollnumber;
        this.course = course;
        this.year = year;
        this.degree = degree;
        this.courece = courece;
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCourece() {
        return courece;
    }



    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthofdate() {
        return birthofdate;
    }

    public void setBirthofdate(String birthofdate) {
        this.birthofdate = birthofdate;
    }

    public String getNumbers() {
        return numbers;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddresss() {
        return addresss;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }



    public String getDegree() {
        return degree;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getUserType() {
        return userType;
    }


}

