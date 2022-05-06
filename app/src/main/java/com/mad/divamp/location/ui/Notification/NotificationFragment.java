package com.mad.divamp.location.ui.Notification;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.divamp.R;
import com.mad.divamp.databinding.LocationHomeFragmentBinding;
import com.mad.divamp.databinding.LocationNotificationFragmentBinding;
import com.mad.divamp.location.ui.Home.HomeViewModel;

public class NotificationFragment extends Fragment {
    private LocationNotificationFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationViewModel galleryViewModel =
                new ViewModelProvider(this).get(NotificationViewModel.class);

        binding = LocationNotificationFragmentBinding.inflate(inflater, container, false);
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