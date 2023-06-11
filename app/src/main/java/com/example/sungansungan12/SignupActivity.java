package com.example.sungansungan12;
//강수 담당

//회원가입 처리
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    private TextView back;
    private EditText name,address,pw,pw2,email,birthyear,birthdate,birthday;
    private Button pwcheck, submit;

    private FirebaseAuth myFirebaseAuth;//인증



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myFirebaseAuth = FirebaseAuth.getInstance();

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        name = findViewById(R.id.signName);
        address=findViewById(R.id.address);
        pw=findViewById(R.id.signPW);
        pw2=findViewById(R.id.signPW2);
        email=findViewById(R.id.signmail);
        birthyear=findViewById(R.id.signBirth);
        birthdate=findViewById(R.id.signBirth2);
        birthday=findViewById(R.id.signBirth3);



        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if(pw.getText().toString().equals(pw2.getText().toString())){
                pwcheck.setText("일치");
            }else{
                Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        //회원가입 완료 버튼
        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //회원가입 처리 시작
                //파이어베이스에 식별자로 등록
                String strEmail = email.getText().toString().trim();
                String strPwd = pw.getText().toString().trim();
                //기타 사용자정보
                String strName =name.getText().toString().trim();
                String strAddress =address.getText().toString().trim();
                String strBirthyear =birthyear.getText().toString().trim();
                String strBirthdate =birthdate.getText().toString().trim();
                String strBirthday =birthday.getText().toString().trim();
                //사용자등록

                //입력 공란 경고
                if (TextUtils.isEmpty(strName)) {
                    Toast.makeText(SignupActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지
                }
                if (TextUtils.isEmpty(strAddress)) {
                    Toast.makeText(SignupActivity.this, "거주지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지
                }
                if (TextUtils.isEmpty(strEmail)) {
                    Toast.makeText(SignupActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지
                }
                if (TextUtils.isEmpty(strPwd)) {
                    Toast.makeText(SignupActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지

                }





                // user -> strEmail strPwd strName strAddress
                myFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = myFirebaseAuth.getCurrentUser();
                            UserAccount account =new UserAccount();
                            String Uid = firebaseUser.getUid();//UID변경오류방지용

                            account.setIdToken(Uid);
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            //유저 추가 데이터 등록
                            Log.i("SunganLog.LoginActivity", "유저 데이터: "+Uid+" / "+firebaseUser.getEmail()+" / "+strName+" / "+strAddress);
                            //리얼타임 객체생성
                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users/"+Uid);

                            //양식 적용
                            User user = new User();
                            user.setName(strName);
                            user.setAddress(strAddress);
                            user.setBirthyear(strBirthyear);
                            user.setBirthdate(strBirthdate);
                            user.setBirthday(strBirthday);

                            //등록
                            databaseRef.setValue(user);
                            

                            Toast.makeText(SignupActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();

                            Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(SignupActivity.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}