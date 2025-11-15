package com.udesc.myapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        var viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        var res = getResources();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        return root;
    }

    private LinearLayout createExerciseCard(Context exercisesContainerContext, ExerciseDTO exerciseDto) {
        var res = getResources();
        var exerciseLayout = new LinearLayout(exercisesContainerContext);
        var context = exerciseLayout.getContext();

        var layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        exerciseLayout.setLayoutParams(layoutParams);
        exerciseLayout.setBackgroundResource(R.drawable.rounded_countainer);
        exerciseLayout.setOrientation(LinearLayout.VERTICAL);

        var titleView = new TextView(context);
        titleView.setText(exerciseDto.getName());
        titleView.setTextSize(16);

        var muscularGroupNameView = new TextView(context);
        muscularGroupNameView.setText(exerciseDto.getMuscularGroupName());
        muscularGroupNameView.setPadding(0, 6, 0, 20);

        var startButton = new Button(context);
        startButton.setText(R.string.button_start_routine);
        startButton.setBackgroundTintList(res.getColorStateList(R.color.purple_500, context.getTheme()));
        startButton.setTextColor(res.getColorStateList(R.color.white, context.getTheme()));

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