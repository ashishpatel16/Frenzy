package com.ashish.frenzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ashish.frenzy.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class DisplayMediaAdapter extends RecyclerView.Adapter<DisplayMediaAdapter.Viewholder> {

    private final List<String> mList;
    private Context mContext;

    public DisplayMediaAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DisplayMediaAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_media_row,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayMediaAdapter.Viewholder holder, int position) {
        Glide.with(mContext).load(mList.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.display_img);

        }
    }
}
