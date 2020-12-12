package com.ashish.frenzy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.Model.Message;
import com.ashish.frenzy.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Viewholder> {

    private List<Message> mMessageList;

    public ChatAdapter(List<Message> mMessageList) {
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
        holder.message.setText(mMessageList.get(position).getText());
        holder.senderId.setText(mMessageList.get(position).getSenderId());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView message, senderId;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            senderId = itemView.findViewById(R.id.senderId);
        }
    }
}
