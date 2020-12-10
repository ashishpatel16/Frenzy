package com.ashish.frenzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Display_Contacts_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Contact> mContactList;
    private ContactRecyclerViewAdapter mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__contacts_);

        mContactList = new ArrayList<>();

        initRecyclerView();
        getPermissions();
        fetchPhoneContacts();

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.display_contacts_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mRecyclerViewAdapter = new ContactRecyclerViewAdapter(mContactList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void fetchPhoneContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            mContactList.add(new Contact(name,phone));
            mRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},1);
        }
    }
}