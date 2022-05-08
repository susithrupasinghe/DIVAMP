package com.mad.divamp.center;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import  com.mad.divamp.center.models.vaccinecard;
import com.mad.divamp.R;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class VaccineRecyclerViewAdapter extends RecyclerView.Adapter<VaccineRecyclerViewAdapter.ViewHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private vaccinecard[] listdata;

    public VaccineRecyclerViewAdapter(vaccinecard[] listdata) {
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
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.batchId.setText(listdata[position].getBatchId());
        Glide.with(holder.imageView.getContext()).load(listdata[position].getLogoUrl()).into(holder.imageView);
        holder.center.setText(listdata[position].getCenter());
        holder.date.setText(listdata[position].getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("vaccines").document(listdata[position].getDocumentId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toasty.success(view.getContext(), "Vaccine delete successful", Toast.LENGTH_SHORT, true).show();

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(view.getContext(), "Vaccine delete failed", Toast.LENGTH_SHORT, true).show();
                            }
                        });
            }
        });
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
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.imageView = (CircleImageView) itemView.findViewById(R.id.card_item_image);
//            this.title = (TextView) itemView.findViewById(R.id.card_item_name);
//            this.row1 = (TextView) itemView.findViewById(R.id.card_item_row1);
//            this.row2 = (TextView) itemView.findViewById(R.id.card_item_row2);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.cardRelativeLayout);
        }
    }

}
