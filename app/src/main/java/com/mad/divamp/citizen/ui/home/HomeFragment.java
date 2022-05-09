package com.mad.divamp.citizen.ui.home;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.citizen.recyclerviews.VaccineRecyclerViewAdapter;
import com.mad.divamp.citizen.models.VaccineCardItem;
import com.mad.divamp.databinding.CitizenHomeFragmentBinding;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {



    private CitizenHomeFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String nic;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = CitizenHomeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences =getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        nic = sharedPreferences.getString("nic","");

        GetPatientDetails(nic);
        RenderRecyclerView(binding.citizenVaccineListRecyclerView, nic);



//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void RenderRecyclerView(RecyclerView recyclerView, String NIC){


        db.collection("vaccine")
                .whereEqualTo("nic", NIC)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            VaccineCardItem[] myCardList = new VaccineCardItem[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                VaccineCardItem Card = new VaccineCardItem();

                                Card.setDocumentId(document.getId());
                                Card.setBatchId(document.get("batchID").toString());
                                Card.setLogoUrl(document.get("imgUrl").toString());
                                Card.setCenter(document.get("centerName").toString());
                                Card.setDate(document.get("date").toString());

                                myCardList[i] = Card;
                                i++;

                            }
                            VaccineRecyclerViewAdapter adapter = new VaccineRecyclerViewAdapter(myCardList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);

                        } else {

                            Toasty.error(getActivity(), " Data retrieval failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });


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
                                    binding.citizenUserName.setText(document.get("firstName").toString() + " " + document.get("lastName").toString());
                                    binding.citizenUserNic.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("imgurl").toString()).into(binding.citizenProfileImage);
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

                                    binding.citizenUserAge.setText("Age : " + diff1.getYears());
                                    binding.citizenUserStatus.setText("Current Status : " + document.get("status").toString());
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