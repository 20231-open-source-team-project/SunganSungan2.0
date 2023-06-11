package com.example.sungansungan12;
//강수 담당

//파이어베이스 고유 유저 식별자 발급용도
public class UserAccount {

    public UserAccount(){}

    private String idToken;

    public  String getIdToken(){ return idToken; }

    public  void setIdToken(String idToken) {
        this.idToken =idToken;
    }

    private String emailId;

    public  String getEmailId(){ return emailId; }

    public  void setEmailId(String emailId) {
        this.emailId =emailId;
    }

    private String password;

    public  String getPassword(){ return password; }

    public  void setPassword(String password) {
        this.password =password;
    }





}