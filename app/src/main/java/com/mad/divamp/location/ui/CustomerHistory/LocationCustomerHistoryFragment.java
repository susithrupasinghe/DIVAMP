package com.mad.divamp.location.ui.CustomerHistory;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import  com.mad.divamp.location.models.historyCardItem;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;
import com.mad.divamp.databinding.LocationCustomerHistoryFragmentBinding;
import com.mad.divamp.location.ui.Home.HomeViewModel;
import com.mad.divamp.location.LocationRecyclerViewAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import es.dmoral.toasty.Toasty;

public class LocationCustomerHistoryFragment extends Fragment {

    private LocationCustomerHistoryFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationCustomerHistoryViewModel galleryViewModel =
                new ViewModelProvider(this).get(LocationCustomerHistoryViewModel.class);

        binding = LocationCustomerHistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RenderRecyclerView(binding.historyRecyclerView,"shavidilunika10s@gmail.com");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void RenderRecyclerView(RecyclerView recyclerView, String email) {
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
                            historyCardItem[] myCardList = new historyCardItem[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                historyCardItem Card = new historyCardItem();

                                Card.setImgUrl(document.get("locationImg").toString());
                                Card.setTitle(document.get("Name").toString());
                                Card.setRow3(document.get("telePhone").toString());

                                Timestamp ts=new Timestamp(Long.parseLong(document.get("DateTime").toString()));
                                Card.setRow1( sdf1.format(ts));
                                Card.setRow2("Date & Time : " + sdf2.format(ts));
                                myCardList[i] = Card;
                                i++;
                            }
                            LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(myCardList);
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