package com.mad.divamp.admin.ui.centerlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CenterListViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CenterListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is History fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}