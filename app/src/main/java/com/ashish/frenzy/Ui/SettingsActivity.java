package com.ashish.frenzy.Ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.UrlLoader;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        Toast.makeText(this, "Nahi Nahi, Abhi time hai iss feature ko!", Toast.LENGTH_LONG).show();
    }
}