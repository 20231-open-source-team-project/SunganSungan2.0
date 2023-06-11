package com.example.sungansungan12;
//강수 담당

//서비스 사용에 사용할 유저정보
public class User {

    private String name="이름";
    private String address="주소";
    private String birthyear="년";
    private String birthdate="월";
    private String birthday="일";

    public User() {
    }


    public String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name =name;
    }

    public String getAddress() {
        return address;
    }

    public  void setAddress(String address) {
        this.address =address;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public  void setBirthyear(String birthyear) {
        this.birthyear =birthyear;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public  void setBirthdate(String birthdate) {
        this.birthdate =birthdate;
    }

    public String getBirthday() {
        return birthday;
    }

    public  void setBirthday(String birthday) {
        this.birthday =birthday;
    }
}