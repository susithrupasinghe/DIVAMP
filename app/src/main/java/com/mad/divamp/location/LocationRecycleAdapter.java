package com.mad.divamp.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.divamp.R;

import com.mad.divamp.location.models.cardItem;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocationRecycleAdapter extends RecyclerView.Adapter<LocationRecycleAdapter.ViewHolder>{

    private cardItem[] listdata;

    public LocationRecycleAdapter(cardItem[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.location_custoer_recycle_view_item, parent, false);
        ViewHolder viewHolder = new LocationRecycleAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationRecycleAdapter.ViewHolder holder, int position) {
        final com.mad.divamp.location.models.cardItem myListData = listdata[position];
        holder.title.setText(listdata[position].getTitle());
        Glide.with(holder.imageView.getContext()).load(listdata[position].getImgUrl()).into(holder.imageView);
        holder.row1.setText(listdata[position].getRow1());
        holder.row2.setText(listdata[position].getRow2());
        holder.row3.setText(listdata[position].getRow3());
        holder.time.setText(listdata[position].getRow2());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView title;
        public TextView row1;
        public  TextView row2;
        public  TextView row3;
        public  TextView time;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (CircleImageView) itemView.findViewById(R.id.card_item_image);
            this.title = (TextView) itemView.findViewById(R.id.card_item_name);
            this.row1 = (TextView) itemView.findViewById(R.id.card_item_row1);
            this.row2 = (TextView) itemView.findViewById(R.id.card_item_row2);
            this.row3 = (TextView) itemView.findViewById(R.id.card_item_row3);
            this.time = (TextView) itemView.findViewById(R.id.card_item_row);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.cardRelativeLayout);
        }
    }
}
