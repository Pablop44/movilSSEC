package com.example.ssec.ui.Consultas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConsultasViewModel(String valor) {
        mText = new MutableLiveData<>();
        mText.setValue(valor);
    }

    public LiveData<String> getText() {
        return mText;
    }

}