package com.udesc.myapplication.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udesc.myapplication.DTOs.ExercicioDTO;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<ExercicioDTO>> mExercises;

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

    public MutableLiveData<List<ExercicioDTO>> getExercises() { return mExercises; }
}