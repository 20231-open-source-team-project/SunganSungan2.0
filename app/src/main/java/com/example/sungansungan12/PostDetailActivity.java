package com.example.sungansungan12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView availableTextView;
    private ImageButton homeButton;
    private ImageView productImageView;
    private TextView uploaderTextView; // 올린 사람의 이름을 표시할 TextView 추가
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.contentTextView);
        productImageView = findViewById(R.id.photoImageView);
        uploaderTextView = findViewById(R.id.userIdTextView); // 올린 사람의 이름을 표시할 TextView 초기화

        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");

        databaseReference = FirebaseDatabase.getInstance().getReference("posts").child(postId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post != null) {
                        titleTextView.setText(post.getName());
                        descriptionTextView.setText(post.getDescription());
                        Glide.with(PostDetailActivity.this)
                                .load(post.getImageUrl())
                                .into(productImageView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });

        DatabaseReference uploaderRef = FirebaseDatabase.getInstance().getReference("posts").child(postId);
        uploaderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String uploaderId = dataSnapshot.child("userId").getValue(String.class);

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uploaderId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String uploaderName = dataSnapshot.child("name").getValue(String.class);
                                uploaderTextView.setText(uploaderName);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 로드 실패 시 처리할 내용 작성
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });

        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(PostDetailActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}
