package com.example.sungansungan12;
//강수담당

//회원정보 수정
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileEditActivity extends AppCompatActivity {


    private ImageButton home;
    private AppCompatButton profileFix;
    private EditText name,address,pw,pw2,birthyear,birthdate,birthday;
    private Button pwcheck;

    //UserUsing에서 가져옴
    private String strName ="초기값";
    private String strAddress ="초기값";

    private String strBirthyear ="초기값";
    private String strBirthdate ="초기값";
    private String strBirthday="초기값";

    private String strUid ="초기값";

    private String strPwd ="초기값";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SunganLog", "profileEditActivity 실행");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //레이아웃 항목
        name = findViewById(R.id.signName);
        address=findViewById(R.id.signaddress);

        pw=findViewById(R.id.signPW);
        pw2=findViewById(R.id.signPW2);

        birthyear=findViewById(R.id.signBirth);
        birthdate=findViewById(R.id.signBirth2);
        birthday=findViewById(R.id.signBirth3);




        Log.i("SunganLog", "로그인 된 유저 정보 호출중");
        //저장된 정보 정보 변수에 등록
        strName=UserUsing.getInstance().getName();
        strAddress=UserUsing.getInstance().getAddress();

        strBirthyear=UserUsing.getInstance().getBirthyear();
        strBirthdate=UserUsing.getInstance().getBirthdate();
        strBirthday=UserUsing.getInstance().getBirthday();

        strUid=UserUsing.getInstance().getUid();
    
        Log.i("SunganLog", "name :"+strName);
        Log.i("SunganLog", "strAddress :"+strAddress);
        Log.i("SunganLog", "strBirthyear :"+strBirthyear);
        Log.i("SunganLog", "strBirthdate :"+strBirthdate);
        Log.i("SunganLog", "strBirthday :"+strBirthday);

        Log.i("SunganLog", "strUid :"+strUid);

        //레이아웃에 적용될 문자
        name.setText(strName);
        address.setText(strAddress);

        birthyear.setText(strBirthyear);
        birthdate.setText(strBirthdate);
        birthday.setText(strBirthday);




        //입력 공란 경고
        if (TextUtils.isEmpty(strName)) {
            Toast.makeText(profileEditActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return; // 로그인 시도 중지
        }
        if (TextUtils.isEmpty(strAddress)) {
            Toast.makeText(profileEditActivity.this, "거주지를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return; // 로그인 시도 중지
        }

        if (TextUtils.isEmpty(strPwd)) {
            Toast.makeText(profileEditActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return; // 로그인 시도 중지

        }



        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if(pw.getText().toString().equals(pw2.getText().toString())){
                pwcheck.setText("일치");
            }else{
                Toast.makeText(profileEditActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });


        home = findViewById(R.id.homeButton);
        //홈으로
        home.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        //수정완료 버튼
        profileFix = findViewById(R.id.profileFixbutton);
        profileFix.setOnClickListener(v -> {

            //입력된 변수 받아서 저장
            //파이어베이스에 식별자로 등록


            //기타 사용자정보
             strName =name.getText().toString().trim();
             strAddress =address.getText().toString().trim();
             strBirthyear =birthyear.getText().toString().trim();
             strBirthdate =birthdate.getText().toString().trim();
             strBirthday =birthday.getText().toString().trim();
             strPwd = pw.getText().toString().trim();
            Log.i("SunganLog", "개인정보 수정중");
            Log.i("SunganLog", "name :"+strName);
            Log.i("SunganLog", "strAddress :"+strAddress);
            Log.i("SunganLog", "strBirthyear :"+strBirthyear);
            Log.i("SunganLog", "strBirthdate :"+strBirthdate);
            Log.i("SunganLog", "strBirthday :"+strBirthday);

            Log.i("SunganLog", "strUid :"+strUid);

            //파베에 데이터 등록
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users/"+strUid);

            //양식 적용
            User user = new User();
            user.setName(strName);
            user.setAddress(strAddress);
            user.setBirthyear(strBirthyear);
            user.setBirthdate(strBirthdate);
            user.setBirthday(strBirthday);

            //리얼타임 등록
            databaseRef.setValue(user);
            Log.i("SunganLog", "리얼타임 개인정보 수정 완료");
            Toast.makeText(profileEditActivity.this,"개인정보 수정 완료",Toast.LENGTH_SHORT).show();


            //로그인 이후에 사용가능함
            Log.i("SunganLog", "현유저 객체 생성 시도");
            FirebaseUser userI = FirebaseAuth.getInstance().getCurrentUser();
            //비밀번호 변경
            Log.i("SunganLog", "비밀 번호 갱신시도");
            userI.updatePassword(strPwd)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("SunganLog", "비밀 번호 갱신완료");
                            }else{
                                Log.i("SunganLog", "비밀 번호 갱신실패");
                            }
                        }
                    });


            Intent intent = new Intent(this, profileActivity.class);
            startActivity(intent);

            Log.i("SunganLog", "profileEditActivity 백그라운드 실행");
        });

    }
}