package com.udesc.myapplication.ui.treinosexecutados;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaCodec;
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

import com.udesc.myapplication.DTOs.ExecucaoTreinoDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentTreinosExecutadosBinding;
import com.udesc.myapplication.helpers.DateHelpers;
import com.udesc.myapplication.helpers.Navigator;

import java.math.BigDecimal;

public class TreinosExecutadosFragment extends Fragment {

    private FragmentTreinosExecutadosBinding binding;
    private TreinosExecutadosViewModel treinosExecutadosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTreinosExecutadosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        treinosExecutadosViewModel = new ViewModelProvider(this).get(TreinosExecutadosViewModel.class);

        final LinearLayout layout = binding.dashboardLayout;

        treinosExecutadosViewModel.getTrainings().observe(
            getViewLifecycleOwner(),
            trainings -> {
                layout.removeAllViews();

                for (var training : trainings) {
                    layout.addView(createTrainingCard(layout.getContext(), training));
                }
            }
        );

        treinosExecutadosViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            Log.e("DashboardFragment", errorMessage);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recarregar os treinos sempre que voltar para esta tela
        if (treinosExecutadosViewModel != null) {
            treinosExecutadosViewModel.loadTrainings();
        }
    }

    private LinearLayout createTrainingCard(Context trainingsContainerContext, ExecucaoTreinoDTO trainingDto) {
        var res = getResources();
        var trainingLayout = new LinearLayout(trainingsContainerContext);
        var context = trainingLayout.getContext();

        var layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        trainingLayout.setLayoutParams(layoutParams);
        trainingLayout.setBackgroundResource(R.drawable.rounded_countainer);
        trainingLayout.setOrientation(LinearLayout.VERTICAL);

        var titleView = new TextView(context);
        titleView.setText(trainingDto.getNomeExecucaoTreino());
        titleView.setTextSize(16);
        titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);

        var dateView = new TextView(context);
        dateView.setText(DateHelpers.format(trainingDto.getDataHoraExecucao()));
        dateView.setPadding(0, 6, 0, 20);

        var viewTrainingButton = new Button(context);
        viewTrainingButton.setText(R.string.button_view_training);
        viewTrainingButton.setBackgroundTintList(res.getColorStateList(R.color.purple_500, context.getTheme()));
        viewTrainingButton.setTextColor(res.getColorStateList(R.color.white, context.getTheme()));
        viewTrainingButton.setOnClickListener(v -> gotoDetalhesExecucao(trainingDto));

        var detailsLayout = createDetailsLayout(context, trainingDto);

        trainingLayout.addView(titleView);
        trainingLayout.addView(dateView);
        trainingLayout.addView(detailsLayout);
        trainingLayout.addView(viewTrainingButton);

        return trainingLayout;
    }

    private LinearLayout createDetailsLayout(Context context, ExecucaoTreinoDTO trainingDto) {
        var container = new LinearLayout(context);
        var res = getResources();
        var containerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        containerLayoutParams.bottomMargin = 20;
        container.setLayoutParams(containerLayoutParams);
        container.setOrientation(LinearLayout.HORIZONTAL);

        var totalVolume = calculateVolumeKg(trainingDto);

        LinearLayout volumeLayout = createDetail(
            container.getContext(),
            res.getString(R.string.volume),
            res.getString(R.string.kg, totalVolume)
        );

        LinearLayout seriesLayout = createDetail(
            container.getContext(),
            res.getString(R.string.exercicios),
            String.valueOf(trainingDto.getExercicios().size())
        );

        container.addView(volumeLayout);
        container.addView(seriesLayout);

        return container;
    }

    private static BigDecimal calculateVolumeKg(ExecucaoTreinoDTO trainingDto) {
        BigDecimal totalVolume = BigDecimal.ZERO;

        for (var exercicio : trainingDto.getExercicios()) {
            for (var serie : exercicio.getSeries()) {
                totalVolume = totalVolume.add(
                    serie.getCarga().multiply(new BigDecimal(serie.getRepeticoes()))
                );
            }
        }

        return totalVolume;
    }

    private LinearLayout createDetail(Context context, String label, String value) {
        var layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        layout.setOrientation(LinearLayout.VERTICAL);

        var volumeLabel = new TextView(context);
        volumeLabel.setText(label);

        var volumeValue = new TextView(context);
        volumeValue.setText(value);

        layout.addView(volumeLabel);
        layout.addView(volumeValue);

        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void gotoDetalhesExecucao(ExecucaoTreinoDTO trainingDto) {
        Bundle b = new Bundle();
        b.putLong("treinoExecutadoId", trainingDto.getIdExecucaoTreino());
        Navigator.callActivity(getContext(), DetalhesTreinoExecutadoActivity.class, b);
    }
}