package com.udesc.myapplication.ui.treinosexecutados;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.udesc.myapplication.DTOs.ExecucaoExercicioSerieDTO;
import com.udesc.myapplication.DTOs.ExecucaoTreinoDTO;
import com.udesc.myapplication.DTOs.ExecucaoTreinoExercicioDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.helpers.DateHelpers;
import com.udesc.myapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesTreinoExecutadoActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView dateView;
    private LinearLayout exercisesContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalhes_treino_executado);

        titleView = findViewById(R.id.treino_title);
        dateView = findViewById(R.id.treino_date);
        exercisesContainer = findViewById(R.id.exercises_container);
        progressBar = findViewById(R.id.progress_bar);

        Long treinoId = getIntent().getExtras().getLong("treinoExecutadoId", -1);

        if (treinoId != -1) {
            carregarDetalhesTreino(treinoId);
        } else {
            Toast.makeText(this, "Erro ao carregar treino", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void carregarDetalhesTreino(Long treinoId) {
        progressBar.setVisibility(View.VISIBLE);
        exercisesContainer.setVisibility(View.GONE);

        RetrofitClient.getApiService().execucaoTreinoPorId(treinoId).enqueue(new Callback<ExecucaoTreinoDTO>() {
            @Override
            public void onResponse(@NonNull Call<ExecucaoTreinoDTO> call, @NonNull Response<ExecucaoTreinoDTO> response) {
                progressBar.setVisibility(View.GONE);
                exercisesContainer.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    exibirDetalhesTreino(response.body());
                } else {
                    Toast.makeText(DetalhesTreinoExecutadoActivity.this, "Erro ao carregar detalhes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ExecucaoTreinoDTO> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("DetalhesTreino", "Erro: " + t.getMessage());
                Toast.makeText(DetalhesTreinoExecutadoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirDetalhesTreino(ExecucaoTreinoDTO treino) {
        titleView.setText(treino.getNomeExecucaoTreino());
        
        if (treino.getDataHoraExecucao() != null) {
            dateView.setText(DateHelpers.format(treino.getDataHoraExecucao()));
        }

        exercisesContainer.removeAllViews();

        if (treino.getExercicios() != null && !treino.getExercicios().isEmpty()) {
            for (ExecucaoTreinoExercicioDTO exercicio : treino.getExercicios()) {
                View exerciseCard = criarCardExercicio(exercicio);
                exercisesContainer.addView(exerciseCard);
            }
        } else {
            TextView emptyView = new TextView(this);
            emptyView.setText("Nenhum exercício cadastrado neste treino");
            emptyView.setPadding(16, 16, 16, 16);
            exercisesContainer.addView(emptyView);
        }
    }

    private View criarCardExercicio(ExecucaoTreinoExercicioDTO exercicio) {
        LinearLayout cardLayout = new LinearLayout(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.bottomMargin = 24;
        cardLayout.setLayoutParams(cardParams);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setBackgroundResource(R.drawable.rounded_countainer);
        cardLayout.setPadding(16, 16, 16, 16);

        // Nome do exercício
        TextView nomeExercicio = new TextView(this);
        nomeExercicio.setText(exercicio.getNomeExercicio());
        nomeExercicio.setTextSize(18);
        nomeExercicio.setTypeface(nomeExercicio.getTypeface(), Typeface.BOLD);
        cardLayout.addView(nomeExercicio);

        // Grupo muscular
        TextView grupoMuscular = new TextView(this);
        grupoMuscular.setText(exercicio.getNomeGrupoMuscular());
        grupoMuscular.setTextSize(14);
        grupoMuscular.setPadding(0, 4, 0, 12);
        cardLayout.addView(grupoMuscular);

        // Descanso
        if (exercicio.getDescansoSegundos() != null && exercicio.getDescansoSegundos() > 0) {
            TextView descanso = new TextView(this);
            descanso.setText("Descanso: " + exercicio.getDescansoSegundos() + "s");
            descanso.setTextSize(14);
            descanso.setPadding(0, 0, 0, 8);
            cardLayout.addView(descanso);
        }

        // Observação
        if (exercicio.getObservacao() != null && !exercicio.getObservacao().isEmpty()) {
            TextView observacao = new TextView(this);
            observacao.setText("Obs: " + exercicio.getObservacao());
            observacao.setTextSize(14);
            observacao.setPadding(0, 0, 0, 12);
            cardLayout.addView(observacao);
        }

        // Séries
        if (exercicio.getSeries() != null && !exercicio.getSeries().isEmpty()) {
            TextView seriesLabel = new TextView(this);
            seriesLabel.setText("Séries:");
            seriesLabel.setTextSize(16);
            seriesLabel.setTypeface(seriesLabel.getTypeface(), Typeface.BOLD);
            seriesLabel.setPadding(0, 8, 0, 8);
            cardLayout.addView(seriesLabel);

            for (ExecucaoExercicioSerieDTO serie : exercicio.getSeries()) {
                View serieView = criarViewSerie(serie);
                cardLayout.addView(serieView);
            }
        }

        return cardLayout;
    }

    private View criarViewSerie(ExecucaoExercicioSerieDTO serie) {
        LinearLayout serieLayout = new LinearLayout(this);
        serieLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = 8;
        serieLayout.setLayoutParams(params);
        serieLayout.setPadding(16, 8, 16, 8);
        serieLayout.setBackgroundResource(android.R.color.darker_gray);

        // Número da série
        TextView numSerie = new TextView(this);
        numSerie.setText("Série " + serie.getNumSerie());
        numSerie.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        serieLayout.addView(numSerie);

        // Repetições
        TextView repeticoes = new TextView(this);
        repeticoes.setText(serie.getRepeticoes() + " reps");
        repeticoes.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        serieLayout.addView(repeticoes);

        // Carga
        TextView carga = new TextView(this);
        carga.setText(serie.getCarga() + " kg");
        carga.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        serieLayout.addView(carga);

        return serieLayout;
    }
}
