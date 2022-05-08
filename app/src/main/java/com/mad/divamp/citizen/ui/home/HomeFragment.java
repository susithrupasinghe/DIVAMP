package com.mad.divamp.citizen.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mad.divamp.admin.RecyclerViewAdapter;
import com.mad.divamp.admin.models.cardItem;
import com.mad.divamp.citizen.ui.home.HomeViewModel;
import com.mad.divamp.databinding.CitizenHomeFragmentBinding;

import java.sql.Timestamp;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {



    private CitizenHomeFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = CitizenHomeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RenderRecyclerView(binding.citizenVaccineRecycler, "20013400692");
        sharedPreferences = this.getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);


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


        db.collection("vaccines")
                .whereEqualTo("NIC", NIC)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 0){
                                Toasty.error(getActivity(), " Data size 0", Toast.LENGTH_SHORT, true).show();
                            }
                            cardItem[] myCardList = new cardItem[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cardItem Card = new cardItem();

                                Card.setImgUrl(document.get("VaccineImg").toString());
                                Card.setTitle(document.get("BatchId").toString());
                                Card.setRow1(document.get("Center").toString());
                                Card.setRow1(document.get("timestamp").toString());

                                myCardList[i] = Card;
                                i++;

                            }
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(myCardList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            Toasty.error(getActivity(), " added to recycler view", Toast.LENGTH_SHORT, true).show();


                        } else {

                            Toasty.error(getActivity(), " Data retrieval failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });


    }
}