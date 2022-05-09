package com.mad.divamp.center.ui.vaccines;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;
import com.mad.divamp.center.ui.home.HomeViewModel;
import com.mad.divamp.databinding.FragmentCenterHomeBinding;
import com.mad.divamp.databinding.ManageVaccinesFragmentBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

 class vaccineModel{

     public String getNIC() {
         return NIC;
     }

     public void setNIC(String NIC) {
         this.NIC = NIC;
     }

     public String getCenter() {
         return Center;
     }

     public void setCenter(String center) {
         Center = center;
     }

     public String getBatchId() {
         return BatchId;
     }

     public void setBatchId(String batchId) {
         BatchId = batchId;
     }

     public String getVaccineName() {
         return vaccineName;
     }

     public void setVaccineName(String vaccineName) {
         this.vaccineName = vaccineName;
     }

     public long getTimestamp() {
         return timestamp;
     }

     public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }

     private String NIC;
     private String Center;
     private String BatchId;
     private String vaccineName;
     private long timestamp;

}

public class ManageVaccinesFragment extends Fragment {

    private ManageVaccinesFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ManageVaccinesViewModel homeViewModel =
                new ViewModelProvider(this).get(ManageVaccinesViewModel.class);

        binding = ManageVaccinesFragmentBinding.inflate(inflater, container, false);
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

    private  void InsertVaccine(String NIC, String Center, String BatchId, String vaccineName){

        vaccineModel mymodel = new vaccineModel();
        mymodel.setBatchId(BatchId);
        mymodel.setCenter(Center);
        mymodel.setNIC(NIC);
        mymodel.setVaccineName(vaccineName);

        Calendar calendar = Calendar.getInstance();

        long timeMilli2 = calendar.getTimeInMillis();
        mymodel.setTimestamp(timeMilli2);

        db.collection("vaccines").add(mymodel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {


                Toasty.error(getActivity(), "Vaccine insertion successful", Toast.LENGTH_SHORT, true).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getActivity(), "Vaccine insertion failed", Toast.LENGTH_SHORT, true).show();
                    }
                });

    }


    private void GetPatientDetails(String NIC){

        db.collection("citizen")
                .whereEqualTo("nic", NIC)
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
                                    binding.vaccinePatientName.setText(document.get("firstname").toString() + " " + document.get("lastname").toString());
                                    binding.vaccinePatientNic.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("imgurl").toString()).into(binding.manageVaccineImage);

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                                    Date d = sdf.parse(document.get("dob").toString());
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(d);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int date = c.get(Calendar.DATE);
                                    LocalDate l1 = LocalDate.of(year, month, date);
                                    LocalDate now1 = LocalDate.now();
                                    Period diff1 = Period.between(l1, now1);

                                    binding.vaccinePatientAge.setText("Age : " + diff1.getYears());
                                    binding.vaccinePatientStatus.setText("Current Status : " + document.get("status").toString());
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