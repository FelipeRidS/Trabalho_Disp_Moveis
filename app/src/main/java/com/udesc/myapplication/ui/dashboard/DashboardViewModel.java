package com.udesc.myapplication.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udesc.myapplication.DTOs.ExercicioSerieDTO;
import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.DTOs.TreinoExercicioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<TreinoDTO>> mTrainings;

    public DashboardViewModel() {
        mTrainings = new MutableLiveData<>();
        var trainings = new ArrayList<TreinoDTO>();
        var trainingOne = new TreinoDTO();
        var exercicios = new ArrayList<TreinoExercicioDTO>();

        var treinoExercicioOne = new TreinoExercicioDTO();
        var exerciciosSerie = new ArrayList<ExercicioSerieDTO>();

        var exercicioSerie = new ExercicioSerieDTO();
        exercicioSerie.setCarga(new BigDecimal(12.5));
        exercicioSerie.setRepeticoes(3);
        exerciciosSerie.add(exercicioSerie);

        treinoExercicioOne.setSeries(exerciciosSerie);
        exercicios.add(treinoExercicioOne);

        trainingOne.setNomeTreino("Treino de peito");
        trainingOne.setDataCriacao(LocalDate.now());
        trainingOne.setExercicios(exercicios);
        trainingOne.setIdTreino(1L);

        var trainingTwo = new TreinoDTO();
        trainingTwo.setNomeTreino("Treino de perna");
        trainingTwo.setDataCriacao(LocalDate.now());
        trainingTwo.setExercicios(new ArrayList<>());
        trainingTwo.setIdTreino(1L);

        trainings.add(trainingOne);
        trainings.add(trainingTwo);

        mTrainings.setValue(trainings);
    }

    public MutableLiveData<List<TreinoDTO>> getTrainings() {
        return mTrainings;
    }
}
