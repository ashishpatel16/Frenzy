package com.ashish.frenzy.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ashish.frenzy.Adapter.DisplayMediaAdapter;
import com.ashish.frenzy.Adapter.MediaAdapter;
import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayMedia extends AppCompatActivity {

    Bundle mBundle;
    List<String> mUrls;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_media);



        mBundle = getIntent().getExtras();
        mUrls = mBundle.getStringArrayList(Constants.MEDIA_URLS);

        for(String s : mUrls) {
            Log.i("URLS", s);
        }
        initMediaRecyclerView();
    }

    private void initMediaRecyclerView() {
        mRecyclerView = findViewById(R.id.display_media_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        DisplayMediaAdapter adapter = new DisplayMediaAdapter(getApplicationContext(), mUrls);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}