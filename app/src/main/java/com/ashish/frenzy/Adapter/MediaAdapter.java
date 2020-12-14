package com.ashish.frenzy.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.Viewholder> {

    private final List<String> mMediaList;
    private final Context mContext;

    public MediaAdapter(Context context, List<String> mediaList) {
        this.mContext = context;
        this.mMediaList = mediaList;
    }

    @NonNull
    @Override
    public MediaAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.media_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaAdapter.Viewholder holder, int position) {
        // Using glide to load images.
        Glide.with(mContext).load(mMediaList.get(position)).into(holder.media_item);
    }

    @Override
    public int getItemCount() {
        return mMediaList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView media_item;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            media_item = itemView.findViewById(R.id.media_item);
        }
    }
}
