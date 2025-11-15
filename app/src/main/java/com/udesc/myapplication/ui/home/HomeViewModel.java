package com.udesc.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<ExerciseDTO>> mExercises;

    public HomeViewModel() {
        mExercises = new MutableLiveData<>();
        var exercises = new ArrayList<ExerciseDTO>();
        var exerciseOne = new ExerciseDTO();

        exerciseOne.setDescription("descrição do exercício 1");
        exerciseOne.setId(1L);
        exerciseOne.setMuscularGroupId(1L);
        exerciseOne.setMuscularGroupName("Perna");
        exerciseOne.setName("Exercício 1");

        var exerciseTwo = new ExerciseDTO();
        exerciseTwo.setDescription("descrição do exercício 2");
        exerciseTwo.setId(1L);
        exerciseTwo.setMuscularGroupId(1L);
        exerciseTwo.setMuscularGroupName("Perna");
        exerciseTwo.setName("Exercício 2");

        exercises.add(exerciseOne);
        exercises.add(exerciseTwo);

        mExercises.setValue(exercises);
    }

    public MutableLiveData<List<ExerciseDTO>> getExercises() { return mExercises; }
}