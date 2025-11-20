package com.udesc.myapplication.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.model.BaseViewModel;
import com.udesc.myapplication.network.ApiService;
import com.udesc.myapplication.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel {
    private final MutableLiveData<List<TreinoDTO>> mTrainings = new MutableLiveData<>();
    private final ApiService apiService = RetrofitClient.getApiService();

    public HomeViewModel() {
        mExercises = new MutableLiveData<>();
        var exercises = new ArrayList<ExercicioDTO>();
        var exerciseOne = new ExercicioDTO();

        exerciseOne.setDescricao("descrição do exercício 1");
        exerciseOne.setId(1L);
        exerciseOne.setIdGrupoMuscular(1L);
        exerciseOne.setNomeGrupoMuscular("Perna");
        exerciseOne.setNome("Exercício 1");

        var exerciseTwo = new ExercicioDTO();
        exerciseTwo.setDescricao("descrição do exercício 2");
        exerciseTwo.setId(1L);
        exerciseTwo.setIdGrupoMuscular(1L);
        exerciseTwo.setNomeGrupoMuscular("Perna");
        exerciseTwo.setNome("Exercício 2");

        exercises.add(exerciseOne);
        exercises.add(exerciseTwo);

        mExercises.setValue(exercises);
    }

    public MutableLiveData<List<TreinoDTO>> getTrainings() {
        return mTrainings;
    }

    public void fetch() {
        apiService.treinos().enqueue(new Callback<List<TreinoDTO>>() {
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
