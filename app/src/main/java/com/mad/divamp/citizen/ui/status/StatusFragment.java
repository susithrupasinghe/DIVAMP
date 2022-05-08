package com.mad.divamp.citizen.ui.status;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;
import com.mad.divamp.citizen.ui.home.HomeViewModel;
import com.mad.divamp.databinding.CitizenHomeFragmentBinding;
import com.mad.divamp.databinding.CitizenStatusFragmentBinding;

import es.dmoral.toasty.Toasty;

public class StatusFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String globalRefId = "";


    private CitizenStatusFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatusViewModel homeViewModel =
                new ViewModelProvider(this).get(StatusViewModel.class);

        binding = CitizenStatusFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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


}