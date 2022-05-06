package com.mad.divamp.admin.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mad.divamp.databinding.FragmentHomeBinding;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    Button btt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btt = binding.button;
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sh = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);

              //  String s1 = sh.getString("name", "");

                Toasty.success(getActivity(), sh.getString("email", ""), Toast.LENGTH_SHORT, true).show();
                // do something when the corky2 is clicked
            }
        });



    //    final TextView textView = binding.textHome;
      //  homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}