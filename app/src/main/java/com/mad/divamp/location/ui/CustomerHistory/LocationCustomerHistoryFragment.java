package com.mad.divamp.location.ui.CustomerHistory;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.divamp.R;
import com.mad.divamp.databinding.LocationCustomerHistoryFragmentBinding;
import com.mad.divamp.location.ui.Home.HomeViewModel;

public class LocationCustomerHistoryFragment extends Fragment {

    private LocationCustomerHistoryFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationCustomerHistoryViewModel galleryViewModel =
                new ViewModelProvider(this).get(LocationCustomerHistoryViewModel.class);

        binding = LocationCustomerHistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}