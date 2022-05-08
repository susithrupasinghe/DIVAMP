package com.mad.divamp.admin.ui.infections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.admin.AdminMainActivity;
import com.mad.divamp.admin.RecyclerViewAdapter;
import com.mad.divamp.admin.models.cardItem;
import com.mad.divamp.databinding.FragmentMarkInfectionsBinding;
import com.mad.divamp.utils.SHA256;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class InfectionsFragment extends Fragment {

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    private String globalrefId = "";
    private FragmentMarkInfectionsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView NIC;
    ImageButton search;
    Button markInfection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        InfectionViewModel galleryViewModel =
                new ViewModelProvider(this).get(InfectionViewModel.class);

        binding = FragmentMarkInfectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        NIC = binding.nicSearch;
        search = binding.searchbtn;
        markInfection = binding.markasinfected;
        binding.secondRow.setVisibility(View.INVISIBLE);
        binding.thirdrow.setVisibility(View.INVISIBLE);
        binding.markasinfected.setVisibility(View.INVISIBLE);




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RenderUserDetails(NIC.getText().toString());
                RenderRecyclerView(binding.adminInfectionRecycler, NIC.getText().toString());

            }
        });

        markInfection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateInfectiedUser(NIC.getText().toString());

            }
        });


//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private  void UpdateInfectiedUser(String NIC){

        try {
            db.collection("citizen").document(globalrefId).update("status","Infected");
            Toasty.success(getActivity(), "Status Updated as Infected Succusfully", Toast.LENGTH_SHORT, true).show();
            RenderUserDetails(NIC);
        }
        catch (Exception ex){
            Toasty.error(getActivity(), "Update process faild", Toast.LENGTH_SHORT, true).show();
        }





    }
    private void RenderUserDetails(String NIC){

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
                                    binding.infectionUserName.setText(document.get("firstname").toString() + " " + document.get("lastname").toString());
                                    binding.infectionUserNic.setText(document.get("nic").toString());
                                    Glide.with(getActivity()).load(document.get("imgurl").toString()).into(binding.profileimgInfected);

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

                                    binding.infectedAge.setText("Age : " + diff1.getYears());
                                    binding.userStatus.setText("Current Status : " + document.get("status").toString());
                                    globalrefId = document.getId().toString();
                                    binding.secondRow.setVisibility(View.VISIBLE);
                                    binding.thirdrow.setVisibility(View.VISIBLE);
                                    binding.markasinfected.setVisibility(View.VISIBLE);
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

    private void RenderRecyclerView(RecyclerView recyclerView, String NIC){


        db.collection("visits")
                .whereEqualTo("NIC", NIC)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            cardItem[] myCardList = new cardItem[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cardItem Card = new cardItem();

                                Card.setImgUrl(document.get("locationImg").toString());
                                Card.setTitle(document.get("locationName").toString());

                                Timestamp ts=new Timestamp(Long.parseLong(document.get("time").toString()));
                                Card.setRow1("Marked Date : " + sdf1.format(ts));
                                Card.setRow2("Marked Time : " + sdf2.format(ts));
                                myCardList[i] = Card;
                                i++;

                            }
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(myCardList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);

                        } else {

                            Toasty.error(getActivity(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });


    }
}