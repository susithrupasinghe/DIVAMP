package com.mad.divamp.citizen.recyclerviews;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.center.models.vaccinecard;
import com.mad.divamp.citizen.models.VaccineCardItem;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class VaccineRecyclerViewAdapter extends RecyclerView.Adapter<VaccineRecyclerViewAdapter.ViewHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private VaccineCardItem[] listdata;

    public VaccineRecyclerViewAdapter(VaccineCardItem[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.citizen_vaccine_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.batchId.setText(listdata[position].getBatchId());
        Glide.with(holder.imageView.getContext()).load(listdata[position].getLogoUrl()).into(holder.imageView);
        holder.center.setText(listdata[position].getCenter());
        holder.date.setText(listdata[position].getDate());
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView batchId;
        public TextView center;
        public  TextView date;
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (CircleImageView) itemView.findViewById(R.id.center_vaccine_list_image);
            this.batchId = (TextView) itemView.findViewById(R.id.center_card_item_row1);
            this.center = (TextView) itemView.findViewById(R.id.center_card_item_row2_center);
            this.date = (TextView) itemView.findViewById(R.id.card_item_row3_center);
            this.delete = (ImageView) itemView.findViewById(R.id.center_deleteBin_card);
        }
    }

}


