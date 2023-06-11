package com.example.sungansungan12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private ListView messageListView;
    private EditText messageInput;
    private Button sendButton,leaveButton;
    private List<String> messages;
    private MessageAdapter messageAdapter;
    private String chatRoomId;
    private DatabaseReference chatRoomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        homeButton = findViewById(R.id.homeButton);
        messageListView = findViewById(R.id.message_list_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        leaveButton = findViewById(R.id.leaveButton);


        // Handle click event of the home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatRoomActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Handle click event of the leave button
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the chat room from the database
                chatRoomRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            // Chat room removed successfully, finish the activity
                            finish();
                        } else {
                            // Error occurred while removing the chat room, show a toast message or handle the error
                            Toast.makeText(ChatRoomActivity.this, "Error removing chat room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // Retrieve the chat room ID from the previous activity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("chatRoomId")) {
            chatRoomId = intent.getStringExtra("chatRoomId");
        }
        // Retrieve the chat room name from the previous activity
        String chatRoomName = ""; // Default chat room name
        if (intent != null && intent.hasExtra("chatRoomName")) {
            chatRoomName = intent.getStringExtra("chatRoomName");
        }
        // Set the chat room name as the title
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(chatRoomName + " 체팅방");

        // Initialize the list of messages
        messages = new ArrayList<>();
        // Initialize the message adapter
        messageAdapter = new MessageAdapter(this, messages);
        // Set the adapter to the list view
        messageListView.setAdapter(messageAdapter);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.i("SunganLog", "ChatRoomActivity- chatRoomId: " + chatRoomId + " chatRoomName: " + chatRoomName);

        chatRoomRef = database.getReference("ChatRooms").child(chatRoomId);

        chatRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the existing messages
                messages.clear();
                // Retrieve and add new messages to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Object messageObj = snapshot.getValue();
                    if (messageObj != null && messageObj instanceof HashMap) {
                        // Get the message text
                        String message = (String) ((HashMap) messageObj).get("message");
                        // Get the author name
                        String author = (String) ((HashMap) messageObj).get("author");

                        if (message != null && author != null) {
                            // Create the formatted message with author name
                            String formattedMessage = author + ": " + message;
                            // Add the message to the list
                            messages.add(formattedMessage);
                        }
                    }
                }
                // Notify the adapter that the data set has changed
                messageAdapter.notifyDataSetChanged();
                // Scroll to the bottom of the list
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error if needed
            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the message text from the input field
                String message = messageInput.getText().toString().trim();
                if (!message.isEmpty()) {
                    // Save the message to the Firebase Realtime Database
                    HashMap<String, Object> messageData = new HashMap<>();

                    Log.i("SunganLog", "로그인된 사용자 정보 확인중");
                    final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final String[] strName = new String[1]; // 배열로 선언하여 값을 변경할 수 있도록 함

                    FirebaseDatabase.getInstance().getReference("Users/" + currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.i("SunganLog", "리얼타임 호출 중");
                            // dataSnapshot에서 원하는 값을 가져와 변수에 저장
                            strName[0] = dataSnapshot.child("name").getValue(String.class);
                            Log.i("SunganLog", "리얼타임 호출 성공");

                            // 변수 값 출력
                            Log.i("SunganLog", "strName: " + strName[0]);
                            messageData.put("message", message);
                            messageData.put("author",  strName[0]);
                            chatRoomRef.push().setValue(messageData);

                            //매시지 보낸후 비우기
                            messageInput.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 가져오기가 취소된 경우 처리
                            Log.e("SunganLog", "리얼타임 호출 실패");
                        }
                    });

                    Log.i("SunganLog", "로그인된 사용자 UID "+currentUserId);




                } else {
                    Toast.makeText(ChatRoomActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}