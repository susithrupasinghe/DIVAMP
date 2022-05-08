package com.mad.divamp.admin.ui.centerlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mad.divamp.databinding.CenterListFragmentBinding;


public class CenterListFragment extends Fragment {

    private CenterListFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CenterListViewModel registrationViewModel =
                new ViewModelProvider(this).get(CenterListViewModel.class);

        binding = CenterListFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}