package com.mad.divamp.admin.ui.registration;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.admin.ui.registration.RegistrationViewModel;
import com.mad.divamp.databinding.FragmentRegistrationBinding;

import  com.mad.divamp.admin.models.registrationModel;

import es.dmoral.toasty.Toasty;

public class RegistrationFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentRegistrationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegistrationViewModel registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);

        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRegistration;
        insertVaccinationCenter(null,null,null,null, null, null,null,null);
        registrationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void insertVaccinationCenter(String email, String CenterName, String province,
                                         String district, String password, String inchargeNIC,
                                         String contactNo , String inchargeEmail){

        registrationModel regModel = new registrationModel();
        regModel.setEmail(email);
        regModel.setCenterName(CenterName);
        regModel.setProvince(province);
        regModel.setDistrict(district);
        regModel.setPassword(password);
        regModel.setInchargeNIC(inchargeNIC);
        regModel.setContactNo(contactNo);
        regModel.setInchargeEmail(inchargeEmail);

        db.collection("center").add(regModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toasty.success(getActivity(), "Vaccination center registration successful", Toast.LENGTH_LONG, true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getActivity(), "Vaccination center registration failed", Toast.LENGTH_LONG, true).show();
                    }
                });
    }
}