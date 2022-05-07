package com.mad.divamp.location.ui.CustomerHistory;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.mad.divamp.location.LocationRecycleAdapter;
import com.mad.divamp.location.models.cardItem;
import com.mad.divamp.databinding.LocationCustomerHistoryFragmentBinding;
import com.mad.divamp.location.ui.Home.HomeViewModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import android.content.SharedPreferences;

import es.dmoral.toasty.Toasty;

public class LocationCustomerHistoryFragment extends Fragment {
    SharedPreferences sharedpreferences;

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    private LocationCustomerHistoryFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LocationCustomerHistoryViewModel galleryViewModel =
                new ViewModelProvider(this).get(LocationCustomerHistoryViewModel.class);

        binding = LocationCustomerHistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RenderRecyclerView(binding.customerHistoryRecyc, "shavidilunika10s@gmail.com");
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void RenderRecyclerView(RecyclerView recyclerView, String email){


        db.collection("customer")
                .whereEqualTo("email", email)
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
                                Card.setTitle(document.get("Name").toString());

                                Timestamp ts=new Timestamp(Long.parseLong(document.get("DateTime").toString()));
                                Card.setTime( sdf1.format(ts));
                                Card.setRow2("Date & Time : " + sdf2.format(ts));
                                myCardList[i] = Card;
                                i++;

                            }
                            LocationRecycleAdapter adapter = new LocationRecycleAdapter(myCardList);
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