package com.mad.divamp.location.ui.Qrcode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QrcodeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public QrcodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}