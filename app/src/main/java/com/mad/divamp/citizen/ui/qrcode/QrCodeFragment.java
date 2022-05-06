package com.mad.divamp.citizen.ui.qrcode;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.divamp.R;
import com.mad.divamp.citizen.ui.notification.AlertNotificationViewModel;
import com.mad.divamp.databinding.CitizenAlertNotificationFragmentBinding;
import com.mad.divamp.databinding.CitizenQrCodeFragmentBinding;

public class QrCodeFragment extends Fragment {

    private CitizenQrCodeFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QrCodeViewModel homeViewModel =
                new ViewModelProvider(this).get(QrCodeViewModel.class);

        binding = CitizenQrCodeFragmentBinding.inflate(inflater, container, false);
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