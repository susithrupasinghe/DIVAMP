package com.mad.divamp.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.mad.divamp.admin.models.centerListCardItem;

import es.dmoral.toasty.Toasty;

public class centerListRecyclerViewAdapter extends RecyclerView.Adapter<centerListRecyclerViewAdapter.ViewHolder>{

    private centerListCardItem[] listdata;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public centerListRecyclerViewAdapter(centerListCardItem[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.admin_center_list_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.centerName.setText(listdata[position].getCenterName());
        holder.inchargeName.setText(listdata[position].getInchargeFullName());
        holder.inchargeEmail.setText(listdata[position].getInchargeEmail());
        holder.contact.setText(listdata[position].getContact());
        holder.delete.bringToFront();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setTitle("Confirm your delete request !");
                alertDialogBuilder.setIcon(R.drawable.ic_baseline_auto_delete_24);
                alertDialogBuilder.setMessage("Are you sure,You want to delete this vaccination center ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        db.collection("center").document(listdata[position].getDocumentId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toasty.success(view.getContext(), "Vaccine center delete process successful", Toast.LENGTH_SHORT, true).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(view.getContext(), "Vaccine center delete process failed", Toast.LENGTH_SHORT, true).show();
                            }
                        });

                    }
                });

                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toasty.info(view.getContext(), "Delete process Cancelled !", Toast.LENGTH_SHORT, true).show();
                    }
                });

                alertDialogBuilder.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView centerName;
        public TextView inchargeName;
        public  TextView inchargeEmail;
        public  TextView contact;
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            this.centerName = (TextView) itemView.findViewById(R.id.card_item_name);
            this.inchargeName = (TextView) itemView.findViewById(R.id.card_item_row1_center);
            this.inchargeEmail = (TextView) itemView.findViewById(R.id.card_item_row2_center);
            this.contact = (TextView) itemView.findViewById(R.id.card_item_row3_center);
            this.delete = (ImageView) itemView.findViewById(R.id.admin_center_deleteBin);

        }
    }
}
