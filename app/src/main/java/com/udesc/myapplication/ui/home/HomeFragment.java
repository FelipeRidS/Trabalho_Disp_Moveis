package com.udesc.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
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

import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentHomeBinding;
import com.udesc.myapplication.ui.treino.DetalhesTreinoActivity;
import com.udesc.myapplication.ui.treino.IniciarTreinoActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        var viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        var res = getResources();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                }
            }
        );

        return root;
    }

    private View createTrainingCard(Context context, TreinoDTO treinoDto) {
        View cardView = LayoutInflater.from(context).inflate(R.layout.training_card, null, false);

        TextView titleView = cardView.findViewById(R.id.training_title);
        TextView dateView = cardView.findViewById(R.id.training_date);
        Button btnViewDetails = cardView.findViewById(R.id.btn_view_details);
        Button btnStartRoutine = cardView.findViewById(R.id.btn_start_routine);

        titleView.setText(treinoDto.getNomeTreino());
        if (treinoDto.getDataCriacao() != null) {
            dateView.setText(treinoDto.getDataCriacao().toString());
        }

        // Botão Ver Detalhes
        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetalhesTreinoActivity.class);
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_ID, treinoDto.getIdTreino());
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_NOME, treinoDto.getNomeTreino());
            intent.putExtra(DetalhesTreinoActivity.EXTRA_TREINO_DATA, treinoDto.getDataCriacao());
            startActivity(intent);
        });

        // Botão Iniciar Rotina
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
