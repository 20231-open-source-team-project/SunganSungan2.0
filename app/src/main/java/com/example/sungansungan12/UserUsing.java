package com.example.sungansungan12;
//강수담당

//User+UserAccount data 유저정보가 앱 내부에서 쓰이는 데이터
public class UserUsing {
    //파베인증에서 가져옴
    private static UserUsing instance;
    private String email="초기값";
    private String uid ="초기값";
    //리얼타임에서 가져옴
    private String address ="초기값";
    private String birthdate ="초기값";
    private String birthday="초기값";
    private String birthyear ="초기값";
    private String name ="초기값";


    public UserUsing() {
    }
    public static UserUsing getInstance() {
        if (instance == null) {
            instance = new UserUsing();
        }
        return instance;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email =email;
    }

    public String getUid() {return uid;}
    public  void setUid(String uid) {this.uid =uid;}

    public String getAddress() {
        return address;
    }
    public  void setAddress(String address) {
        this.address =address;
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

    public String getBirthyear() {
        return birthyear;
    }
    public  void setBirthyear(String birthyear) {
        this.birthyear =birthyear;
    }

    public String getName() {
        return name;
    }
    public  void setName(String name) {
        this.name =name;
    }
}
