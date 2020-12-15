package com.ashish.frenzy.Ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashish.frenzy.Adapter.ChatAdapter;
import com.ashish.frenzy.Adapter.MediaAdapter;
import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.Model.Message;
import com.ashish.frenzy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private static final int CHOOSE_IMAGE_CODE = 1;
    private RecyclerView mChatRecyclerView, mMediaRecyclerView;
    private List<Message> mChatList;
    private ChatAdapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager, mMediaLayoutManager;
    private MaterialButton mSendButton,mAtatchMediaButton;
    private TextInputEditText mInputMessage;
    private Bundle mBundle;
    private String mChatId, mSenderId;
    private List<String> mAttachedMedia;
    private MediaAdapter mMediaAdapter;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBundle = getIntent().getExtras();
        mChatId = mBundle.getString(Constants.CHAT_ID);
        mInputMessage = findViewById(R.id.message_editText);
        mSendButton = findViewById(R.id.send_button);
        mAtatchMediaButton = findViewById(R.id.add_media_button);


        Log.i(TAG, "onCreate: Chat ID Found : "+ mChatId);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mAtatchMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        initMessageRecyclerView();
        initMediaRecyclerView();
        populateMessageList();

    }

    private void initMessageRecyclerView() {
        mChatList = new ArrayList<>();
        mSenderId = FirebaseAuth.getInstance().getUid();
        mChatRecyclerView = findViewById(R.id.chat_recyclerView);
        mChatRecyclerView.setHasFixedSize(false);
        mChatRecyclerView.setNestedScrollingEnabled(false);
        mChatLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mChatRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(mChatList,mSenderId);
        mChatRecyclerView.setAdapter(mChatAdapter);

        mChatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom < oldBottom) {
                    mChatRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mChatRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount());
                        }
                    }, 10);
                }
            }
        });
    }

    private void initMediaRecyclerView() {
        mAttachedMedia = new ArrayList<>();
        mMediaRecyclerView = findViewById(R.id.media_recyclerview);
        mMediaRecyclerView.setHasFixedSize(false);
        mMediaRecyclerView.setNestedScrollingEnabled(false);
        mMediaAdapter = new MediaAdapter(getApplicationContext(), mAttachedMedia);
        mMediaLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mMediaRecyclerView.setLayoutManager(mMediaLayoutManager);
        mMediaRecyclerView.setAdapter(mMediaAdapter);
    }

    /** Sending a message, or more precisely making an entry corresponding to the desired ChatId on database */
    private void sendMessage() {
        if(!mInputMessage.getText().toString().isEmpty() || !mAttachedMedia.isEmpty()) {
            DatabaseReference mMessageDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chat").child(mChatId).push();
            String messageId = mMessageDatabaseReference.getKey();
            String senderId = FirebaseAuth.getInstance().getUid();
            Map<String, Object> map = new HashMap<>();
            map.put("text", mInputMessage.getText().toString());
            map.put("senderId", senderId);

            if (mAttachedMedia.isEmpty()) {
                mMessageDatabaseReference.updateChildren(map);
            }else {
                Toast.makeText(this, "Sending Media", Toast.LENGTH_SHORT).show();
                List<String> mediaLinks = new ArrayList<>();
                List<String> mediaIDs = new ArrayList<>();

                for (String uri : mAttachedMedia) {
                    String mediaId = mMessageDatabaseReference.child("media").push().getKey();
                    final StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("media").child(mChatId).child(messageId).child(mediaId);
                    mediaIDs.add(mediaId);
                    UploadTask uploadTask = mStorageReference.putFile(Uri.parse(uri));
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i(TAG, "onSuccess: Download Link : " + uri);
                                    map.put("/media/" + mediaIDs.get(counter) + "/", uri.toString());
                                    mediaLinks.add(uri.toString());
                                    counter++;
                                    if (counter == mAttachedMedia.size()) {
                                        mMessageDatabaseReference.updateChildren(map);
                                        mAttachedMedia.clear();
                                        mediaIDs.clear();
                                        mMediaAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    });

                }
                for (String s : mediaLinks) Log.i(TAG, "sendMessage: Media Link " + s);
            }
        }
        mInputMessage.setText(null);
    }


    /** Start an intent to pick and select multiple images from gallery*/
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CHOOSE_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == CHOOSE_IMAGE_CODE) {
                if(data.getClipData() == null) {
                    Log.i(TAG, "onActivityResult: Image Uri captured : " + data.getData().toString());
                    mAttachedMedia.add(data.getData().toString());
                }else {
                    for(int i=0; i<data.getClipData().getItemCount(); i++) {
                        mAttachedMedia.add(data.getClipData().getItemAt(i).getUri().toString());
                    }
                }
                mMediaAdapter.notifyDataSetChanged();
            }
        }
    }

    /** Fetching chat messages from database */
    private void populateMessageList() {
        final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("chat").child(mChatId);
        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    List<String> media = new ArrayList<>();
                    String text="", senderId = "";
                    if(snapshot.child("text").getValue() != null) {
                        text = snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("senderId").getValue() != null) {
                        senderId = snapshot.child("senderId").getValue().toString();
                    }
                    if(snapshot.child("media").getChildrenCount() > 0) {
                        for(DataSnapshot childSnapshot : snapshot.child("media").getChildren()) {
                            media.add(childSnapshot.getValue().toString());
                        }
                    }

                    Message msg = new Message(senderId,snapshot.getKey(),text,media);
                    mChatList.add(msg);
                    mChatLayoutManager.scrollToPosition(mChatList.size() - 1);
                    mChatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}