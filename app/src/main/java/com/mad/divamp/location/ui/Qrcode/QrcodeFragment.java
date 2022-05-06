package com.mad.divamp.location.ui.Qrcode;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.divamp.R;
import com.mad.divamp.databinding.LocationNotificationFragmentBinding;
import com.mad.divamp.databinding.LocationQrcodeFragmentBinding;
import com.mad.divamp.location.ui.Notification.NotificationViewModel;

public class QrcodeFragment extends Fragment {
    private LocationQrcodeFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QrcodeViewModel galleryViewModel =
                new ViewModelProvider(this).get(QrcodeViewModel.class);

        binding = LocationQrcodeFragmentBinding.inflate(inflater, container, false);
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