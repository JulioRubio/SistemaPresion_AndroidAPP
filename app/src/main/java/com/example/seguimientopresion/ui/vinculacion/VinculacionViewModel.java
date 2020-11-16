package com.example.seguimientopresion.ui.vinculacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VinculacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VinculacionViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Vinculación doctores");
    }

    public LiveData<String> getText() {
        return mText;
    }
}