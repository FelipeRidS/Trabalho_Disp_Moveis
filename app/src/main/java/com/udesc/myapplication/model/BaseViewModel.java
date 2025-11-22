package com.udesc.myapplication.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }

    public void setError(String error) {
        mError.setValue(error);
    }

    public MutableLiveData<String> getError() {
        return mError;
    }
}
