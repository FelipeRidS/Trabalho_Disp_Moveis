package com.udesc.myapplication.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

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
