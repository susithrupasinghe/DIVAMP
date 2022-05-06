package com.mad.divamp.location.ui.CustomerHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationCustomerHistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LocationCustomerHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}