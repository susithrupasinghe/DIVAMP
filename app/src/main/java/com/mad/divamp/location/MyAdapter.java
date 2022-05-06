package com.mad.divamp.location;
import com.mad.divamp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

//    Context context;
//
//    ArrayList<Customer> list;
//
//    public MyAdapter(Context context, ArrayList<Customer> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.items,parent, false);
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        Customer customer = list.get(position);
//        holder.name.setText(customer.getName());
//        holder.NIC.setText(customer.getNIC());
//        holder.DateTime.setText(customer.getDateTime());
//        holder.contactNo.setText(customer.getContactNum());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder
//    {
//
//        TextView name,NIC,DateTime,contactNo;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.name_C);
//            NIC = itemView.findViewById(R.id.NIC_C);
//            DateTime = itemView.findViewById(R.id.dateTime_C);
//            contactNo = itemView.findViewById(R.id.contactNumber_C);
//        }
//    }
//}
