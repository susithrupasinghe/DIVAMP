package com.mad.divamp.admin.ui.centerlist;

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
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.admin.centerListRecyclerViewAdapter;
import com.mad.divamp.admin.models.centerListCardItem;
import com.mad.divamp.databinding.CenterListFragmentBinding;

import java.sql.Timestamp;

import es.dmoral.toasty.Toasty;


public class CenterListFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CenterListFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CenterListViewModel registrationViewModel =
                new ViewModelProvider(this).get(CenterListViewModel.class);

        binding = CenterListFragmentBinding.inflate(inflater, container, false);

        RenderRecyclerView(binding.centerListRecyclerView);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void RenderRecyclerView(RecyclerView recyclerView){


        db.collection("center")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            centerListCardItem[] myCardList = new centerListCardItem[task.getResult().size()];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                centerListCardItem Card = new centerListCardItem();

                                Card.setCenterName(document.get("centerName").toString());
                                Card.setInchargeFullName("Incharge Full Name :  " + document.get("inchargeFullName").toString());
                                Card.setInchargeEmail("Incharge Email : " + document.get("inchargeEmail").toString());
                                Card.setContact("Incharge Contact : " + document.get("contactNo").toString());
                                Card.setDocumentId(document.getId().toString());
                                myCardList[i] = Card;
                                i++;

                            }
                            centerListRecyclerViewAdapter adapter = new centerListRecyclerViewAdapter(myCardList);
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