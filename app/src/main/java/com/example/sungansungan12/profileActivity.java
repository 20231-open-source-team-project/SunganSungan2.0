package com.example.sungansungan12;
//강수 담당

//회원정보 보기
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {
    private ImageButton home;
    private AppCompatButton profileFix;
    private TextView name, address,birth1,birth2,birth3,email;

    
    //파베인증에서 가져옴
    private String strEmail="초기값";
    private String strUid ="초기값";
    //리얼타임에서 가져옴
    private String strAddress ="초기값";
    private String strBirthdate ="초기값";
    private String strBirthday="초기값";

    private String strBirthyear ="초기값";
    private String strName ="초기값";
    //유저 객체생성 profileEditActivity에서 사용함


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.i("SunganLog", "profileActivity 실행");

        home = findViewById(R.id.homeButton);
        //홈으로
        home.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        name = findViewById(R.id.NamePrint);
        address = findViewById(R.id.addressPrint);
        birth1 = findViewById(R.id.birthPrint);
        birth2 = findViewById(R.id.birth2Print);
        birth3 = findViewById(R.id.birth3Print);
        email = findViewById(R.id.mailPrint);



  
        //사용자 정보호출

        //파이어베이스에서 사용자 객체정보 받아옴<-----------------------------------------------------------------------------------------
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Log.i("SunganLog", "로그인 된 사용자 정보 확인중");
            strEmail = user.getEmail();
            strUid = user.getUid();
            Log.i("SunganLog", "로그인 된 사용자 객체에 저장중");
            //객체에 저장
            UserUsing.getInstance().setEmail(strEmail);
            UserUsing.getInstance().setUid(strUid);
            Log.i("SunganLog", "로그인 된 사용자: "+strEmail+"고유식별자: "+strUid);
            boolean emailVerified = user.isEmailVerified();

        } else {
            Log.e("SunganLog", "로그인 정보 호출 실패");
        }

        // 사용자 리얼타임 정보 가져옴<--------------------------------------------------------------------------------------------------
        FirebaseDatabase.getInstance().getReference("Users/"+strUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("SunganLog", "리얼타임 호출 중");
                // dataSnapshot에서 원하는 값을 가져와 변수에 저장
                strAddress = dataSnapshot.child("address").getValue(String.class);
                strBirthdate = dataSnapshot.child("birthdate").getValue(String.class);
                strBirthday = dataSnapshot.child("birthday").getValue(String.class);
                strBirthyear = dataSnapshot.child("birthyear").getValue(String.class);
                strName = dataSnapshot.child("name").getValue(String.class);
                Log.i("SunganLog", "리얼타임 객체에 저장중");

                //객체에 저장
                UserUsing.getInstance().setAddress(strAddress);
                UserUsing.getInstance().setBirthdate(strBirthdate);
                UserUsing.getInstance().setBirthday(strBirthday);
                UserUsing.getInstance().setBirthyear(strBirthyear);
                UserUsing.getInstance().setName(strName);
                Log.i("SunganLog", "리얼타임 호출 성공");

                // 변수 값 출력
                Log.i("SunganLog", "strAddress: "+strAddress);
                Log.i("SunganLog", "strBirthdate: "+strBirthdate);
                Log.i("SunganLog", "strBirthday: "+strBirthday);
                Log.i("SunganLog", "strBirthyer: "+strBirthyear);
                Log.i("SunganLog", "strName: "+strName);

                //받아온 값 레이아웃에 적용
                name.setText(strName);
                address.setText(strAddress);
                birth1.setText(strBirthyear+"년");
                birth2.setText(strBirthdate+"월");
                birth3.setText(strBirthday+"일");
                email.setText(strEmail);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 가져오기가 취소된 경우 처리
                Log.e("SunganLog", "리얼타임 호출 실패");
            }
        });



        profileFix = findViewById(R.id.profileFixbutton);
        //수정하기
        profileFix.setOnClickListener(v -> {
            Intent intent = new Intent(this, profileEditActivity.class);
            startActivity(intent);
            finish();
            Log.i("SunganLog", "profileActivity 종료");
        });


    }
}