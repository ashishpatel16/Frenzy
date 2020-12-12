package com.ashish.frenzy.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.frenzy.Model.Contact;
import com.ashish.frenzy.R;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.Viewholder> {

    private List<Contact> mList;

    public ContactRecyclerViewAdapter(List<Contact> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ContactRecyclerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerViewAdapter.Viewholder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.phone.setText("+91 "+ mList.get(position).getPh_number());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // init a new chat or reload if already found one!
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(mList.get(position).getUid()).child("chat").child(key).setValue(true);
                Log.i("Chat Debug","Chat Init.");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView name,phone;
        ImageView display_pic;
        ConstraintLayout layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            phone = itemView.findViewById(R.id.contact_phone);
            display_pic = itemView.findViewById(R.id.display_picture);
            layout = itemView.findViewById(R.id.contact_row_layout);
        }
    }
}
