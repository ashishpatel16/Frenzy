package com.ashish.frenzy.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ashish.frenzy.Adapter.ChatListAdapter;
import com.ashish.frenzy.Model.Chat;
import com.ashish.frenzy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private RecyclerView mRecyclerView;
    private ChatListAdapter mAdapter;
    private List<Chat> mChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatList = new ArrayList<>();
        initRecyclerView();
        populateChatList();
    }

    private void populateChatList() {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Chat chat = new Chat(childSnapshot.getKey());
                        // Redundancy check for chats
                        boolean isRepeated = false;
                        for(Chat obj : mChatList) {
                            if(obj.getChatId().equals(chat.getChatId())) {
                                isRepeated = true;
                            }
                        }
                        if(isRepeated) continue;
                        // If unique chatId found, Add them to our list
                        mChatList.add(chat);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: DatabaseError : " + error.getMessage());
            }
        });

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.chat_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        mAdapter = new ChatListAdapter(mChatList);
        mRecyclerView.setAdapter(mAdapter);
    }
}