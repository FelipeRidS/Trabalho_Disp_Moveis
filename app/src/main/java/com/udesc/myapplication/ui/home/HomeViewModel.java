package com.udesc.myapplication.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udesc.myapplication.DTOs.ExercicioDTO;
import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.model.BaseViewModel;
import com.udesc.myapplication.network.ApiService;
import com.udesc.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel {
    private final MutableLiveData<List<ExercicioDTO>> mExercises = new MutableLiveData<>();
    private ApiService apiService = RetrofitClient.getApiService();

    public HomeViewModel() {
        setLoading(true);
    }

    public MutableLiveData<List<ExercicioDTO>> getExercises() { return mExercises; }

    public void fetch() {
        apiService.exercicios().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<ExercicioDTO>> call, @NonNull Response<List<ExercicioDTO>> response) {
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    mExercises.setValue(response.body());
                } else {
                    setError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ExercicioDTO>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}