package com.example.sungansungan12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListActivity extends AppCompatActivity {
    private List<DataSnapshot> chatRoomSnapshotList; // 새로 추가된 리스트
    private ListView chatRoomListView;
    private ArrayAdapter<String> chatRoomAdapter;
    private List<String> chatRoomList;
    private ImageButton homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        homeButton = findViewById(R.id.homeButton);

        // Handle click event of the home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatRoomListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        chatRoomListView = findViewById(R.id.chat_room_list_view);

        chatRoomList = new ArrayList<>();
        chatRoomSnapshotList = new ArrayList<>();

        chatRoomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatRoomList);

        chatRoomListView.setAdapter(chatRoomAdapter);
        chatRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSnapshot snapshot = chatRoomSnapshotList.get(position);
                String chatRoomId = getChatRoomId(position);
                String chatRoomName = chatRoomList.get(position);
                // ChatRoomActivity로 이동
                Intent intent = new Intent(ChatRoomListActivity.this, ChatRoomActivity.class);
                intent.putExtra("chatRoomId", chatRoomId);
                intent.putExtra("chatRoomName", chatRoomName);

                Log.i("SunganLog", "ChatRoomListActivity/chatRoomListView- chatRoomId: " + chatRoomId + " chatRoomName: " + chatRoomName);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference("ChatRooms")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chatRoomList.clear();
                        chatRoomSnapshotList.clear();

                        // 현재 사용자 ID 가져오기
                        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // 체팅방 ID와 이름 가져오기
                            String chatRoomId = snapshot.getKey();
                            String chatRoomName = snapshot.child("name").getValue(String.class);

                            // 현재 사용자가 체팅방의 멤버인지 확인
                            if (snapshot.child("users").hasChild(currentUserId)) {
                                // 체팅방을 리스트에 추가
                                chatRoomList.add(chatRoomName);
                                chatRoomSnapshotList.add(snapshot);
                            }
                        }
                        chatRoomAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 읽기 작업이 취소된 경우 오류 처리
                    }
                });

    }
    private String getChatRoomId(int position) {
        DataSnapshot snapshot = chatRoomSnapshotList.get(position);
        return snapshot.getKey(); // chatRoomId를 가져와 반환합니다.
    }

}
