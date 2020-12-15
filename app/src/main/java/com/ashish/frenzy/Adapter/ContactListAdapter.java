package com.ashish.frenzy.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.Methods.Constants;
import com.ashish.frenzy.Model.Contact;
import com.ashish.frenzy.R;
import com.ashish.frenzy.Ui.ChatActivity;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.Viewholder> {

    private final List<Contact> mList;

    public ContactListAdapter(List<Contact> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ContactListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.Viewholder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.phone.setText("+91 "+ mList.get(position).getPh_number());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // init a new chat or reload if already found one!
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(mList.get(position).getName());
                FirebaseDatabase.getInstance().getReference().child("user").child(mList.get(position).getUid()).child("chat").child(key).setValue(mList.get(position).getName());
                Log.i("Chat Debug","Chat Init.");

                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CHAT_ID,key);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView name,phone;
        ConstraintLayout layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            phone = itemView.findViewById(R.id.contact_phone);
            layout = itemView.findViewById(R.id.contact_row_layout);
        }
    }
}
