package com.udesc.myapplication.ui.treinosexecutados;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.udesc.myapplication.DTOs.ExecucaoTreinoDTO;
import com.udesc.myapplication.model.BaseViewModel;
import com.udesc.myapplication.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreinosExecutadosViewModel extends BaseViewModel {
    private final MutableLiveData<List<ExecucaoTreinoDTO>> mTrainings = new MutableLiveData<>();

    public TreinosExecutadosViewModel(@NonNull Application application) {
        super(application);
        loadTrainings();
    }

    public void loadTrainings() {
        setLoading(true);

        Context c = getApplication();
        SharedPreferences sp = c.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        
        RetrofitClient.getApiService().execucaoTreinos(sp.getLong("id", 0L)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<ExecucaoTreinoDTO>> call, @NonNull Response<List<ExecucaoTreinoDTO>> response) {
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    mTrainings.setValue(response.body());
                } else {
                    setError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ExecucaoTreinoDTO>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public MutableLiveData<List<ExecucaoTreinoDTO>> getTrainings() { 
        return mTrainings; 
    }
}
