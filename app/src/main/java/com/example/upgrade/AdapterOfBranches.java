package com.example.upgrade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOfBranches extends RecyclerView.Adapter<AdapterOfBranches.MyViewHolder> {

    ArrayList<Branches> arrayList = new ArrayList<>();
    Context context;

    public AdapterOfBranches(ArrayList<Branches> arrayList,Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.ivBrIcon.setImageResource(arrayList.get(position).getIconId());
        holder.tvBrName.setText(arrayList.get(position).getBrName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,GetSyllabus.class);
                i.putExtra("urlodbrsyllabus",arrayList.get(position).getUrl());
                i.putExtra("brName",arrayList.get(position).getBrName().toLowerCase().trim());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

         ImageView ivBrIcon;
         TextView tvBrName;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);
             tvBrName = itemView.findViewById(R.id.tvBrName);
             ivBrIcon = itemView.findViewById(R.id.ivBrIcon);

         }
     }

    public void setFilter(ArrayList<Branches> newList)
    {
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}
