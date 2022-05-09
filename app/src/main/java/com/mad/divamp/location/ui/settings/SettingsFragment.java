package com.mad.divamp.location.ui.settings;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
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
import com.mad.divamp.databinding.LocationQrcodeFragmentBinding;
import com.mad.divamp.databinding.LocationSettingsFragmentBinding;
import com.mad.divamp.location.logIn.LogIn1Activity;
import com.mad.divamp.location.registration.Registration1Activity;
import com.mad.divamp.location.registration.Registration2Activity;
import com.mad.divamp.location.registration.Registration3Activity;
import com.mad.divamp.location.ui.Qrcode.QrcodeViewModel;

import es.dmoral.toasty.Toasty;

public class SettingsFragment extends Fragment {

    Button delete,logOut;
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
        RenderUserDetails(getEmail);

        delete = binding.deleteBtn;
        logOut = binding.logOut;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Delete(getEmail);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                logOut();

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
            Toasty.success(getActivity(), "Location Delete Successfully", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(getActivity(), Registration1Activity.class);
            startActivity(intent);
        }
        catch (Exception ex){
            Toasty.error(getActivity(), "Delete process failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void logOut(){
        getEmail = sharedpreferences.getString("","");
        Toasty.success(getActivity(), "Success", Toast.LENGTH_SHORT, true).show();
        Intent intent = new Intent(getActivity(), LogIn1Activity.class);
        startActivity(intent);
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
                                    globalrefId = document.getId().toString();
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