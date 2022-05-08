package com.mad.divamp.location.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.databinding.LocationQrcodeFragmentBinding;
import com.mad.divamp.databinding.LocationSettingsFragmentBinding;
import com.mad.divamp.location.ui.Qrcode.QrcodeViewModel;

import es.dmoral.toasty.Toasty;

public class SettingsFragment extends Fragment {

    Button delete;
    String getEmail;
    private String globalrefId = "";
    SharedPreferences sharedpreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private LocationSettingsFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel galleryViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = LocationSettingsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedpreferences =getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        getEmail = sharedpreferences.getString("email","");


        delete = binding.deleteBtn;
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Delete(getEmail);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private  void Delete(String email){

        try {
            db.collection("location").document(globalrefId).delete();
//            db.collection("citizen").document(globalrefId).update("status","Infected");
            Toasty.success(getActivity(), "Status Updated as Infected Succusfully", Toast.LENGTH_SHORT, true).show();
//            RenderUserDetails(NIC);
        }
        catch (Exception ex){
            Toasty.error(getActivity(), "Update process faild", Toast.LENGTH_SHORT, true).show();
        }
    }
}