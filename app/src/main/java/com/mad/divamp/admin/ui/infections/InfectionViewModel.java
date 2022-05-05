package com.mad.divamp.admin.ui.infections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfectionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InfectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Infection fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}