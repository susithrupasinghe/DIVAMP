package com.mad.divamp.citizen.ui.notification;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.divamp.R;
import com.mad.divamp.citizen.ui.home.HomeViewModel;
import com.mad.divamp.databinding.CitizenAlertNotificationFragmentBinding;
import com.mad.divamp.databinding.CitizenHomeFragmentBinding;

public class AlertNotificationFragment extends Fragment {


    private CitizenAlertNotificationFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AlertNotificationViewModel homeViewModel =
                new ViewModelProvider(this).get(AlertNotificationViewModel.class);

        binding = CitizenAlertNotificationFragmentBinding.inflate(inflater, container, false);
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

}