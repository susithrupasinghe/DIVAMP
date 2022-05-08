package com.mad.divamp.admin;

import com.bumptech.glide.Glide;
import  com.mad.divamp.admin.models.cardItem;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.divamp.R;



import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private cardItem[] listdata;

    public RecyclerViewAdapter(cardItem[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.admin_recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final cardItem myListData = listdata[position];
        holder.title.setText(listdata[position].getTitle());
        Glide.with(holder.imageView.getContext()).load(listdata[position].getImgUrl()).into(holder.imageView);
        holder.row1.setText(listdata[position].getRow1());
        holder.row2.setText(listdata[position].getRow2());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView title;
        public TextView row1;
        public  TextView row2;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (CircleImageView) itemView.findViewById(R.id.card_item_image);
            this.title = (TextView) itemView.findViewById(R.id.card_item_name);
            this.row1 = (TextView) itemView.findViewById(R.id.card_item_row1);
            this.row2 = (TextView) itemView.findViewById(R.id.card_item_row2);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.cardRelativeLayout);
        }
    }

}

