package com.mad.divamp.center.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton46.stepsview.StepsView;
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
import com.mad.divamp.databinding.FragmentCenterHomeBinding;

import com.mad.divamp.center.VaccineRecyclerViewAdapter;
import com.mad.divamp.center.models.vaccinecard;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import com.mad.divamp.center.models.vaccine;
public class HomeFragment extends Fragment {

    private FragmentCenterHomeBinding binding;
    private String CURRENT_CENTER = "KADUWELA - MOH";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StepsView stepsView;
    private String GlobaldocId;
    private Button page_1_btn, page_2_btn, showAll, page_2_back, page_3_back;
    private TextView page_1_nic, page_2_batch_id;
    private Spinner page_2_vaccine_name;
    private ConstraintLayout top_stepper, page_1_bottom, page_2_bottom, page_3;
    private RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentCenterHomeBinding.inflate(inflater, container, false);
        stepsView = binding.stepsViewCenter;
        View root = binding.getRoot();

        stepsView
                .setLabels(new String[] {"Scan QR & Detect Patient", "Enter vaccine Data"})
                .setBarColorIndicator(getContext().getResources().getColor(R.color.inactive))
                .setProgressColorIndicator(getContext().getResources().getColor(R.color.light_green))
                .setLabelColorIndicator(getContext().getResources().getColor(R.color.inactive))
                .setCompletedPosition(0)
                .drawView();

        page_1_nic = binding.centerPage1Nic;
        page_1_btn = binding.centerPage1Next;
        page_2_btn = binding.centerBtnFinish;
        page_2_batch_id = binding.centerBatchId;
        page_2_vaccine_name = binding.centerVaccineName;
        showAll = binding.centerShowAll;
        page_2_back = binding.centerBackBttPage2;
        page_3_back = binding.centerPage3Back;


        top_stepper = binding.pageStepperCenter;
        page_1_bottom = binding.page1Bottom;
        page_2_bottom = binding.page2Bottom;
        page_3 = binding.page3;
        recycler = binding.centerVaccineListRecyclerView;
        visiblePage_1();

        page_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(page_1_nic.getText().toString().isEmpty()){
                    page_1_nic.setError("Please enter your email");
                }
                else{
                    GetPatientDetails(page_1_nic.getText().toString());
                }

            }
        });

        page_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkValidity()){

                    insertVaccine(page_2_batch_id.getText().toString(), CURRENT_CENTER, page_2_vaccine_name.getSelectedItem().toString(), page_1_nic.getText().toString());

                }

            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                visiblePage_3(page_1_nic.getText().toString());

            }
        });

        page_2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                visiblePage_1();

            }
        });

        page_3_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               visiblePage_2();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean checkValidity(){

        if(page_2_batch_id.getText().toString().isEmpty()){
            page_2_batch_id.setError("Please enter your batch ID");
            return  false;
        }

        return true;
    }
    private void visiblePage_1(){

        stepsView.setCompletedPosition(0).drawView();
        top_stepper.setVisibility(View.VISIBLE);
        page_1_bottom.setVisibility(View.VISIBLE);
        page_2_bottom.setVisibility(View.GONE);
        page_3.setVisibility(View.GONE);

    }

    private void visiblePage_2(){

        stepsView.setCompletedPosition(1).drawView();
        top_stepper.setVisibility(View.VISIBLE);
        page_1_bottom.setVisibility(View.GONE);
        page_2_bottom.setVisibility(View.VISIBLE);
        page_3.setVisibility(View.GONE);

    }
    private void visiblePage_3(String NIC){

        binding.centerUserNamePage3.setText(binding.centerUserName.getText().toString());
        binding.centerUserNicPage3.setText(binding.centerPage1Nic.getText().toString());
        binding.centerUserAgePage3.setText(binding.centerUserAge.getText().toString());
        binding.centerLastVaccinatedDateInfPage3.setText(binding.centerLastVaccinatedDateInf.getText().toString());
        binding.centerUserStatusPage3.setText(binding.centerUserStatus.getText().toString());
        binding.centerProfileImagePage3.setImageDrawable(binding.centerProfileImage.getDrawable());

        stepsView.setCompletedPosition(1).drawView();
        top_stepper.setVisibility(View.GONE);
        page_1_bottom.setVisibility(View.GONE);
        page_2_bottom.setVisibility(View.GONE);
        page_3.setVisibility(View.VISIBLE);
        RenderRecyclerView(recycler, NIC);

    }
    private void RenderRecyclerView(RecyclerView recyclerView, String NIC){


        db.collection("vaccine")
                .whereEqualTo("nic", NIC)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            vaccinecard[] myCardList = new vaccinecard[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                vaccinecard Card = new vaccinecard();

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
    private void insertVaccine(String batchId, String centerName, String vaccineName, String NIC){


        vaccine newvaccine = new vaccine();
        newvaccine.setBatchID(batchId);
        newvaccine.setCenterName(centerName);
        newvaccine.setVaccineName(vaccineName);
        if(vaccineName.equals("Phyzer")){
            newvaccine.setImgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkuAFm9jx0EMmbjQZqNg9_wV_zi62ot6GdLjPJCFtQ-4Tweks-7mRlm8dP8iVZYxK3vWc&usqp=CAU");
        }
        if(vaccineName.equals("Moderna")){
            newvaccine.setImgUrl("https://assets.flagfamily.com/web/images/articles/moderna-logo-1632484439.jpg?OZOFHPLkHV23wwlHRxV7eNqJxJ6vRamF");
        }
        if(vaccineName.equals("Astrazeneca")){
            newvaccine.setImgUrl("https://www.astrazeneca.com/etc/designs/az/img/AZ-logo-external.jpg");
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        newvaccine.setDate(df.format(new Date()));
        newvaccine.setNIC(NIC);



        db.collection("vaccine").add(newvaccine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toasty.success(getActivity(), "Vaccine inserted successful", Toast.LENGTH_LONG, true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(getActivity(), "Vaccine insertion failed", Toast.LENGTH_LONG, true).show();
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
                                    binding.centerUserName.setText(document.get("firstname").toString() + " " + document.get("lastname").toString());
                                    binding.centerUserNic.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("imgurl").toString()).into(binding.centerProfileImage);
                                    GlobaldocId = document.getId();
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

                                    binding.centerUserAge.setText("Age : " + diff1.getYears());
                                    binding.centerUserStatus.setText("Current Status : " + document.get("status").toString());
                                    visiblePage_2();
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