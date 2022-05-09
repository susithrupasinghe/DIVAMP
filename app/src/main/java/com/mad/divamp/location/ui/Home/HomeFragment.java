package com.mad.divamp.location.ui.Home;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;

import com.mad.divamp.databinding.LocationHomeFragmentBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

    private String globalrefId = "";
    String getEmail;
    SharedPreferences sharedpreferences;
    private LocationHomeFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button markInfection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel galleryViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = LocationHomeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        sharedpreferences =getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        getEmail = sharedpreferences.getString("email","");
        RenderUserDetails(getEmail);
        markInfection = binding.Locationmarkasinfected;
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        markInfection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateInfectiedUser(getEmail);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private  void UpdateInfectiedUser(String getEmail){

        try {
            db.collection("location").document(globalrefId).update("status","Infected");
            Toasty.success(getActivity(), "Status Updated as Infected Successfully", Toast.LENGTH_SHORT, true).show();
            RenderUserDetails(getEmail);
        }
        catch (Exception ex){
            Toasty.error(getActivity(), "Update process failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void RenderUserDetails(String getEmail){

        db.collection("location")
                .whereEqualTo("email", getEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 0){

                                Toasty.error(getActivity(), "please logIn", Toast.LENGTH_SHORT, true).show();
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try{
                                    binding.locName.setText(document.get("location_name").toString() + " ");
                                    binding.ownerNIC.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("image").toString()).into(binding.profileimgInfected);

                                    binding.ownerName.setText("Owner Name : " +document.get("fullName").toString());
                                    binding.Address1.setText("Address : " + document.get("address_1").toString());
                                    binding.phoneNumber.setText("Contact Number : " +document.get("contactNo").toString());
                                    binding.email.setText("E-mail : " +document.get("email").toString());
                                    globalrefId = document.getId().toString();
                                    binding.secondRow.setVisibility(View.VISIBLE);
                                    binding.thirdrow.setVisibility(View.VISIBLE);
                                    binding.Locationmarkasinfected.setVisibility(View.VISIBLE);
                                    break;
                                }
                                catch (Exception Ex){
                                    Toasty.error(getActivity(), Ex.getMessage(), Toast.LENGTH_LONG, true).show();
                                }
                            }
                        } else {

                            Toasty.error(getActivity(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });


    }
}