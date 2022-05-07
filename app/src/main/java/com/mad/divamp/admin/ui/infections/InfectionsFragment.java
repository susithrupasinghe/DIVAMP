package com.mad.divamp.admin.ui.infections;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.admin.AdminMainActivity;
import com.mad.divamp.admin.RecyclerViewAdapter;
import com.mad.divamp.admin.models.cardItem;
import com.mad.divamp.databinding.FragmentMarkInfectionsBinding;
import com.mad.divamp.utils.SHA256;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class InfectionsFragment extends Fragment {

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    private FragmentMarkInfectionsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfectionViewModel galleryViewModel =
                new ViewModelProvider(this).get(InfectionViewModel.class);

        binding = FragmentMarkInfectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RenderRecyclerView(binding.adminInfectionRecycler, "20013400692");


//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void RenderRecyclerView(RecyclerView recyclerView, String NIC){


        db.collection("visits")
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
                            Toasty.error(getActivity(), " added to recycler view", Toast.LENGTH_SHORT, true).show();


                        } else {

                            Toasty.error(getActivity(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });


    }
}