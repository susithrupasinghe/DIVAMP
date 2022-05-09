package com.mad.divamp.citizen.ui.status;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;
import com.mad.divamp.citizen.ui.home.HomeViewModel;
import com.mad.divamp.databinding.CitizenHomeFragmentBinding;
import com.mad.divamp.databinding.CitizenStatusFragmentBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class StatusFragment extends Fragment {




    private CitizenStatusFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String nic,globalRefId,status;
    Button mark;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatusViewModel homeViewModel =
                new ViewModelProvider(this).get(StatusViewModel.class);

        binding = CitizenStatusFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences =getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        nic = sharedPreferences.getString("nic","");
        GetPatientDetails(nic);

        mark=binding.statusbutton;

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("Healthy")){
                    updateStatus(nic,"infected");
                }else{
                    updateStatus(nic,"Healthy");
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private  void updateStatus(String nic,String status){
        db.collection("citizen")
                .whereEqualTo("nic",nic)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (task.getResult().size()==0){
                                Toasty.error(getActivity(),"Account not found", Toast.LENGTH_SHORT,true).show();
                            }else{
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    globalRefId = document.getId();
                                }
                            }
                        }
                        else{
                            Toasty.error(getActivity(), " Data retrieval failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
        try {
            db.collection("citizen").document(globalRefId).update("status",status);
            Toasty.success(getActivity(), "Status updated successfully", Toast.LENGTH_SHORT, true).show();
        }
        catch (Exception ex){
            Toasty.error(getActivity(), "Update process failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void GetPatientDetails(String nic){

        db.collection("citizen")
                .whereEqualTo("nic", nic)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 0){

                                Toasty.error(getActivity(), "Please input correct NIC", Toast.LENGTH_SHORT, true).show();
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try{
                                    binding.citizenUserNameStatus.setText(document.get("firstName").toString() + " " + document.get("lastName").toString());
                                    binding.citizenUserNicStatus.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("imgurl").toString()).into(binding.citizenProfileImageStatus);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                                    Date d = sdf.parse(document.get("birthday").toString());
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(d);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int date = c.get(Calendar.DATE);
                                    LocalDate l1 = LocalDate.of(year, month, date);
                                    LocalDate now1 = LocalDate.now();
                                    Period diff1 = Period.between(l1, now1);

                                    binding.citizenUserAgeStatus.setText("Age : " + diff1.getYears());
                                    binding.citizenUserStatusStatus.setText("Current Status : " + document.get("status").toString());
                                    status= document.get("status").toString();
                                    break;
                                }
                                catch (Exception Ex){
                                    Toasty.error(getActivity(), Ex.getMessage(), Toast.LENGTH_LONG, true).show();
                                }

                            }

                        } else {

                            Toasty.error(getActivity(), " Data retrieval failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


}