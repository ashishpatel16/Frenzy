package com.ashish.frenzy.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.Model.Chat;
import com.ashish.frenzy.Model.Contact;
import com.ashish.frenzy.R;
import com.ashish.frenzy.Ui.ChatActivity;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final List<Chat> mChatList;
    public ChatListAdapter(List<Chat> chatList) {
        this.mChatList = chatList;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
        holder.title.setText(mChatList.get(position).getContactName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CHAT_ID,mChatList.get(holder.getAdapterPosition()).getChatId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView title;
        LinearLayoutCompat layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chat_title);
            layout = itemView.findViewById(R.id.chat_layout);
        }
    }
}
