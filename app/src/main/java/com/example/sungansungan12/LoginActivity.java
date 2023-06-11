package com.example.sungansungan12;
//강수 담당
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth myFirebaseAuth;//인증
    private DatabaseReference myDatabaseRef;//실시간 DB
    private EditText loginID,loginPwd;
    private TextView signinBtn;
    private AppCompatButton loginBtn;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myFirebaseAuth = FirebaseAuth.getInstance();//인증
        myDatabaseRef = FirebaseDatabase.getInstance().getReference();//리얼타임

        loginID =findViewById(R.id.editID);
        loginPwd = findViewById(R.id.ediPassword);
        signinBtn = findViewById(R.id.signinBtn);
        loginBtn = findViewById(R.id.loginBtn);


        //회원가입 버튼 클릭시, 회원가입 페이지로 이동
        signinBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
        //로그인 버튼
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String strEmail = loginID.getText().toString();
                String strPwd = loginPwd.getText().toString();

                if (TextUtils.isEmpty(strEmail)) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지
                }

                if (TextUtils.isEmpty(strPwd)) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // 로그인 시도 중지
                }


                myFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
}