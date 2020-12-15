package com.ashish.frenzy.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.Model.Message;
import com.ashish.frenzy.R;
import com.ashish.frenzy.Ui.DisplayMedia;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Viewholder> {

    private final List<Message> mMessageList;
    private final String mSenderId;

    public ChatAdapter(List<Message> mMessageList,String senderId) {
        this.mSenderId = senderId;
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public ChatAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.Viewholder holder, int position) {
        holder.setIsRecyclable(false);
        if(mMessageList.get(position).getSenderId().equals(mSenderId)) {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.sender_message_bg);
            holder.layout.setBackground(drawable);
        }else {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.reciever_message_bg);
            holder.layout.setBackground(drawable);
        }

        if(mMessageList.get(position).getMediaList().size() > 0) {
            holder.mediaButton.setVisibility(View.VISIBLE);
            holder.mediaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DisplayMedia.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(Constants.MEDIA_URLS,(ArrayList) mMessageList.get(position).getMediaList());
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            });
        }

        //holder.senderId.setText(mMessageList.get(position).getSenderId());
        if(!mMessageList.get(position).getText().isEmpty()) {
            holder.message.setText(mMessageList.get(position).getText());
        }else {
            holder.message.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView message, senderId;
        MaterialButton mediaButton;
        ConstraintLayout layout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            // senderId = itemView.findViewById(R.id.senderId);
            layout = itemView.findViewById(R.id.message_layout);
            mediaButton = itemView.findViewById(R.id.view_media);
        }
    }
}
