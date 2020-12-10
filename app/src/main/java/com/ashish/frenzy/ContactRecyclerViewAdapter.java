package com.ashish.frenzy;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView name,phone;
        ImageView display_pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            phone = itemView.findViewById(R.id.contact_phone);
            display_pic = itemView.findViewById(R.id.display_picture);
        }
    }
}
