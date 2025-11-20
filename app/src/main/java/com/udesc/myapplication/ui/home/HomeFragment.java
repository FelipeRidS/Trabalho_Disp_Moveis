package com.udesc.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
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

import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentHomeBinding;
import com.udesc.myapplication.helpers.DateHelpers;
import com.udesc.myapplication.ui.treino.DetalhesTreinoActivity;
import com.udesc.myapplication.ui.treino.IniciarTreinoActivity;

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

        viewModel.getTrainings().observe(
            getViewLifecycleOwner(),
            trainings -> {
                myRoutinesTextView.setText(res.getString(R.string.my_routines, trainings.size()));
                exercisesContainer.removeAllViews();

                for (var training : trainings) {
                    var trainingCard = createTrainingCard(
                        exercisesContainer.getContext(), training
                    );

                    exercisesContainer.addView(trainingCard);

                    int bottomDp = 8;
                    int bottomPx = (int) (bottomDp * exercisesContainer.getContext().getResources().getDisplayMetrics().density + 0.5f);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, bottomPx);
                    trainingCard.setLayoutParams(lp);
                }
            }
        );

        viewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            Log.e("HomeFragment", errorMessage);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private View createTrainingCard(Context context, TreinoDTO treinoDto) {
        View cardView = LayoutInflater.from(context).inflate(R.layout.training_card, null, false);

        var layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        cardView.setLayoutParams(layoutParams);

        TextView titleView = cardView.findViewById(R.id.training_title);
        TextView dateView = cardView.findViewById(R.id.training_date);
        TextView exercisesCountView = cardView.findViewById(R.id.training_exercises_count);
        Button btnStartRoutine = cardView.findViewById(R.id.btn_start_routine);

        titleView.setText(treinoDto.getNomeTreino());
        
        if (treinoDto.getDataCriacao() != null) {
            dateView.setText(DateHelpers.format(treinoDto.getDataCriacao()));
        }

        // Número de exercícios
        int numExercicios = treinoDto.getNumeroExercicios();
        String exerciciosText = numExercicios + " " + (numExercicios == 1 ? "exercício" : "exercícios");
        exercisesCountView.setText(exerciciosText);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetalhesTreinoActivity.class);
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_ID, treinoDto.getIdTreino());
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_NOME, treinoDto.getNomeTreino());
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_DATA, treinoDto.getDataCriacao());
            startActivity(intent);
        });

        btnStartRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), IniciarTreinoActivity.class);
            intent.putExtra(IniciarTreinoActivity.EXTRA_TREINO_ID, treinoDto.getIdTreino());
            intent.putExtra(IniciarTreinoActivity.EXTRA_TREINO_NOME, treinoDto.getNomeTreino());
            startActivity(intent);
        });

        return cardView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
