package com.mad.divamp.admin.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.admin.centerListRecyclerViewAdapter;
import com.mad.divamp.admin.models.centerListCardItem;
import com.mad.divamp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button btt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calculateVaccineStatistics();
        calculateInfectedCount();
        calculateTotalCenterCount();

        return root;
    }

    public  void  calculateTotalCenterCount(){

        db.collection("center")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            binding.centerCount.setText("Total Center Count : " + String.valueOf(task.getResult().size()));

                        } else {

                            Toasty.error(getActivity(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
    public  void calculateInfectedCount(){

        db.collection("citizen").whereEqualTo("status", "Infected")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            binding.totalInfectedCount.setText("Total Infected Count : " + String.valueOf(task.getResult().size()));

                        }
                    }
                });

    }
    public void calculateVaccineStatistics(){

        db.collection("vaccine")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<String> NICList = new ArrayList<>(task.getResult().size());

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NICList.add(document.get("nic").toString());
                            }


                            binding.totalVaccinatedCount.setText("Total Vaccinated Count : " + String.valueOf(NICList.stream().distinct().count()));
                            binding.dose1Only.setText("1st Dose Only : " + String.valueOf(calculate1stDoseOnlyCount(NICList)));
                            binding.dose2Only.setText("Up to 2nd Dose : " + String.valueOf(calculate2ndDoseOnlyCount(NICList)));
                            binding.dose3Only.setText("Up to 3rd Dose : " + String.valueOf(calculate3rdDoseOnlyCount(NICList)));

                        } else {

                            Toasty.error(getActivity(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public  long calculateTotalVaccinatedCount(ArrayList NIC){

        return NIC.stream().distinct().count();
    }
    public int  calculate1stDoseOnlyCount(ArrayList NIC){

        int doseCount = 0;
        List<String> newarr = (List<String>) NIC.stream().distinct().collect(Collectors.toList());

        for (int i = 0; i < newarr.size(); i++) {

            if( Collections.frequency(NIC, newarr.get(i)) == 1){
                doseCount++;
            }

        }
        return doseCount;
    }

    public int  calculate2ndDoseOnlyCount(ArrayList NIC){

        int doseCount = 0;
        List<String> newarr = (List<String>) NIC.stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < newarr.size(); i++) {

            if( Collections.frequency(NIC, newarr.get(i)) == 2){
                doseCount++;
            }

        }
        return doseCount;
    }

    public int  calculate3rdDoseOnlyCount(ArrayList NIC){

        int doseCount = 0;
        List<String> newarr = (List<String>) NIC.stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < newarr.size(); i++) {

            if( Collections.frequency(NIC, newarr.get(i)) == 3){
                doseCount++;
            }

        }
        return doseCount;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}