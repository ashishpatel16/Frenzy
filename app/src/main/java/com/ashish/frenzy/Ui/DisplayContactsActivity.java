package com.ashish.frenzy.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.ashish.frenzy.Adapter.ContactRecyclerViewAdapter;
import com.ashish.frenzy.Model.Contact;
import com.ashish.frenzy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import com.ashish.frenzy.Methods.AuthUtils;

public class DisplayContactsActivity extends AppCompatActivity {

    private static final String TAG = "Display_Contacts";
    private RecyclerView mRecyclerView;
    private List<Contact> mPhoneContactsList, mFrenzyUserList;
    private ContactRecyclerViewAdapter mRecyclerViewAdapter;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__contacts_);

        mPhoneContactsList = new ArrayList<>();
        mFrenzyUserList = new ArrayList<>();

        fetchPhoneContacts();
        initRecyclerView();
        getPermissions();


    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.display_contacts_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mRecyclerViewAdapter = new ContactRecyclerViewAdapter(mFrenzyUserList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void fetchPhoneContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
            Contact newContact = new Contact("",name,phone);
            mPhoneContactsList.add(newContact);
            checkFrenzyUser(newContact);
        }

    }

    private void checkFrenzyUser(Contact newContact) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        String num = AuthUtils.validatePhone(newContact);
        Log.i(TAG, "checkFrenzyUser: Checking for user "+num);
        Query query = mDatabaseReference.orderByChild("phone").equalTo(num);

        
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Log.i(TAG, "onDataChange: Snapshot exists");
                    String name = "", ph="";
                    for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Log.i(TAG, "onDataChange: Checking for : " + newContact.getName());
                        if(childSnapshot.child("name").getValue() != null) {
                            name = childSnapshot.child("name").getValue().toString();
                        }
                        if(childSnapshot.child("phone").getValue() != null) {
                            ph = childSnapshot.child("phone").getValue().toString();
                            Log.i(TAG, "onDataChange: Got one");
                        }
                        Contact mContact =  new Contact(childSnapshot.getKey(),name,ph);
                        if(name.equals(ph)) {
                           mContact.setName(newContact.getName());
                        }
                        mFrenzyUserList.add(mContact);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: Database error while verifying contact: "+error.getMessage());
            }
        });
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},1);
        }
    }
}