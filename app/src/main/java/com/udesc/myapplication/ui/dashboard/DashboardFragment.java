package com.udesc.myapplication.ui.dashboard;

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

import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.databinding.FragmentDashboardBinding;
import com.udesc.myapplication.helpers.DateHelpers;

import java.math.BigDecimal;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        final LinearLayout layout = binding.dashboardLayout;

        dashboardViewModel.getTrainings().observe(
            getViewLifecycleOwner(),
            trainings -> {
                for (var training : trainings) {
                    layout.addView(createTrainingCard(layout.getContext(), training));
                }
            }
        );

        dashboardViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            Log.e("DashboardFragment", errorMessage);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });

        dashboardViewModel.fetch();
    }

    private LinearLayout createTrainingCard(Context trainingsContainerContext, TreinoDTO trainingDto) {
        var res = getResources();
        var trainingLayout = new LinearLayout(trainingsContainerContext);
        var context = trainingLayout.getContext();

        var layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        trainingLayout.setLayoutParams(layoutParams);
        trainingLayout.setBackgroundResource(R.drawable.rounded_countainer);
        trainingLayout.setOrientation(LinearLayout.VERTICAL);

        var titleView = new TextView(context);
        titleView.setText(trainingDto.getNomeTreino());
        titleView.setTextSize(16);
        titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);

        var dateView = new TextView(context);
        dateView.setText(DateHelpers.format(trainingDto.getDataCriacao()));
        dateView.setPadding(0, 6, 0, 20);

        var viewTrainingButton = new Button(context);
        viewTrainingButton.setText(R.string.button_view_training);
        viewTrainingButton.setBackgroundTintList(res.getColorStateList(R.color.purple_500, context.getTheme()));
        viewTrainingButton.setTextColor(res.getColorStateList(R.color.white, context.getTheme()));

        var detailsLayout = createDetailsLayout(context, trainingDto);

        trainingLayout.addView(titleView);
        trainingLayout.addView(dateView);
        trainingLayout.addView(detailsLayout);
        trainingLayout.addView(viewTrainingButton);

        return trainingLayout;
    }

    private LinearLayout createDetailsLayout(Context context, TreinoDTO trainingDto) {
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

    private static BigDecimal calculateVolumeKg(TreinoDTO trainingDto) {
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
}