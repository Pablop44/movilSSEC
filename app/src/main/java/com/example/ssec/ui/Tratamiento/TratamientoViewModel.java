package com.example.ssec.ui.Tratamiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TratamientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TratamientoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tratamiento fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}