package com.udesc.myapplication.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.model.BaseViewModel;
import com.udesc.myapplication.network.ApiService;
import com.udesc.myapplication.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends BaseViewModel {
    private final MutableLiveData<List<TreinoDTO>> mTrainings = new MutableLiveData<>();
    private ApiService apiService = RetrofitClient.getApiService();

    public DashboardViewModel() {
        setLoading(true);
    }

    public MutableLiveData<List<TreinoDTO>> getTrainings() { return mTrainings; }

    public void fetch() {
        apiService.treinos().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<TreinoDTO>> call, @NonNull Response<List<TreinoDTO>> response) {
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    mTrainings.setValue(response.body());
                } else {
                    setError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TreinoDTO>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
