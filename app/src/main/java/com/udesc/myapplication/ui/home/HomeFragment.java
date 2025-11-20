package com.udesc.myapplication.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udesc.myapplication.DTOs.ExercicioDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        var res = getResources();

        final TextView myRoutinesTextView = binding.myRoutines;
        final LinearLayout exercisesContainer = binding.exercisesContainer;

        viewModel.getExercises().observe(
            getViewLifecycleOwner(),
            exerciseDtos -> {
                myRoutinesTextView.setText(res.getString(R.string.my_routines, exerciseDtos.size()));

                for (var exerciseDto : exerciseDtos) {
                    var exerciseCard = createExerciseCard(
                        exercisesContainer.getContext(), exerciseDto
                    );

                    exercisesContainer.addView(exerciseCard);
                }
            }
        );

        viewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            Log.e("HomeFragment", errorMessage);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.fetch();
    }

    private LinearLayout createExerciseCard(Context exercisesContainerContext, ExercicioDTO exercicioDto) {
        var res = getResources();
        var exerciseLayout = new LinearLayout(exercisesContainerContext);
        var context = exerciseLayout.getContext();

        var layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        exerciseLayout.setLayoutParams(layoutParams);
        exerciseLayout.setBackgroundResource(R.drawable.rounded_countainer);
        exerciseLayout.setOrientation(LinearLayout.VERTICAL);

        var titleView = new TextView(context);
        titleView.setText(exercicioDto.getNome());
        titleView.setTextSize(16);
        titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);

        var muscularGroupNameView = new TextView(context);
        muscularGroupNameView.setText(exercicioDto.getNomeGrupoMuscular());
        muscularGroupNameView.setPadding(0, 6, 0, 20);

        var startButton = new Button(context);
        startButton.setText(R.string.button_start_routine);
        startButton.setBackgroundTintList(res.getColorStateList(R.color.purple_500, context.getTheme()));
        startButton.setTextColor(res.getColorStateList(R.color.white, context.getTheme()));
        startButton.setOnClickListener((View v) -> {
            Bundle b = new Bundle();
//            Gson gson = new Gson();
//            b.putString("exercicioJson",gson.toJson(exercicioDto));
//            Navigator.callActivity(this.getContext(), MainActivity.class, b); // Futura tela de execução de exercício
        });

        exerciseLayout.addView(titleView);
        exerciseLayout.addView(muscularGroupNameView);
        exerciseLayout.addView(startButton);

        return exerciseLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
