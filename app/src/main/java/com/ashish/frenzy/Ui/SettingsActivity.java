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

        String url = "https://firebasestorage.googleapis.com/v0/b/frenzy-55380.appspot.com/o/media%2F-MOVaCNUsNHr9jS-QoX9%2F-MOVe14RD5UiIv3Gt9XZ%2F-MOVe14TLZPAbc3MJXOc?alt=media&token=a37c583c-c8e4-476a-b0ae-bd4ff851c34f";

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), resource, "myImg" , "");
                        Log.i("debug","IMage Ready");
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        ArrayList<String> mList = new ArrayList<>();
        mList.add("https://firebasestorage.googleapis.com/v0/b/frenzy-55380.appspot.com/o/media%2F-MOVaCNUsNHr9jS-QoX9%2F-MOVecxQcmnAkm0XaEek%2F-MOVecxnpKagkFGnoLNh?alt=media&token=c57ac74e-f88b-4128-aa09-1c5aa3e62486");
        mList.add("https://firebasestorage.googleapis.com/v0/b/frenzy-55380.appspot.com/o/media%2F-MOVaCNUsNHr9jS-QoX9%2F-MOVecxQcmnAkm0XaEek%2F-MOVecy-itcrEyRFTxRT?alt=media&token=47ec3824-39b0-4257-b193-66b0cc6e5df9");
        mList.add("https://firebasestorage.googleapis.com/v0/b/frenzy-55380.appspot.com/o/media%2F-MOVaCNUsNHr9jS-QoX9%2F-MOVecxQcmnAkm0XaEek%2F-MOVecxRfj5zuh_I6Z6M?alt=media&token=42b7bbdc-4435-4f0f-94fa-428c99a5291e");


        Intent intent = new Intent(this, DisplayMedia.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constants.MEDIA_URLS,(ArrayList) mList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}