package com.ashish.frenzy.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ashish.frenzy.Adapter.ChatAdapter;
import com.ashish.frenzy.Model.Message;
import com.ashish.frenzy.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Message> mMessageList;
    private ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mMessageList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.chat_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mChatAdapter = new ChatAdapter(mMessageList);
        mRecyclerView.setAdapter(mChatAdapter);
    }
}