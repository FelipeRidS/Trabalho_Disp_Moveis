package com.udesc.myapplication.ui.treino;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.udesc.myapplication.DTOs.ExecucaoTreinoExercicioDTO;
import com.udesc.myapplication.DTOs.ExecucaoExercicioSerieDTO;
import com.udesc.myapplication.DTOs.ExecucaoTreinoDTO;
import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.DTOs.TreinoExercicioDTO;
import com.udesc.myapplication.DTOs.ExercicioSerieDTO;
import com.udesc.myapplication.R;
import com.udesc.myapplication.network.RetrofitClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarTreinoActivity extends AppCompatActivity {

    public static final String EXTRA_TREINO_ID = "treino_id";
    public static final String EXTRA_TREINO_NOME = "treino_nome";

    private TextView titleView;
    private TextView timerView;
    private TextView exercisesCountView;
    private LinearLayout exercisesContainer;
    private Button btnFinalizar;
    private ProgressBar progressBar;

    private Handler handler = new Handler();
    private long startTime = 0;
    private boolean timerRunning = false;

    private Long treinoId;
    private String treinoNome;
    private TreinoDTO treino;
    private LocalDateTime dataHoraInicio;
    
    // Armazenar dados de execução
    private final Map<Long, Map<Integer, SerieData>> execucaoData = new HashMap<>();

    private class SerieData {
        int repeticoes;
        BigDecimal carga;
        
        SerieData(int repeticoes, BigDecimal carga) {
            this.repeticoes = repeticoes;
            this.carga = carga;
        }
    }

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (timerRunning) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                timerView.setText(formatTime(elapsedTime));
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_iniciar_treino);

        titleView = findViewById(R.id.treino_title);
        timerView = findViewById(R.id.timer_view);
        exercisesCountView = findViewById(R.id.exercises_count);
        exercisesContainer = findViewById(R.id.exercises_container);
        btnFinalizar = findViewById(R.id.btn_finalizar);
        progressBar = findViewById(R.id.progress_bar);

        treinoId = getIntent().getLongExtra(EXTRA_TREINO_ID, -1);
        treinoNome = getIntent().getStringExtra(EXTRA_TREINO_NOME);

        if (treinoId == -1 || treinoNome == null) {
            Toast.makeText(this, "Erro ao carregar treino", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        titleView.setText(treinoNome);
        dataHoraInicio = LocalDateTime.now();

        // Iniciar timer simples
        startTimer();

        btnFinalizar.setOnClickListener(v -> finalizarTreino());

        carregarDadosTreino();
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerRunning = true;
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        timerRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private String formatTime(long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void carregarDadosTreino() {
        progressBar.setVisibility(View.VISIBLE);
        exercisesContainer.setVisibility(View.GONE);

        RetrofitClient.getApiService().treinoPorId(treinoId).enqueue(new Callback<TreinoDTO>() {
            @Override
            public void onResponse(@NonNull Call<TreinoDTO> call, @NonNull Response<TreinoDTO> response) {
                progressBar.setVisibility(View.GONE);
                exercisesContainer.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    treino = response.body();
                    exibirExercicios();
                } else {
                    Toast.makeText(IniciarTreinoActivity.this, "Erro ao carregar treino", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TreinoDTO> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("IniciarTreino", "Erro: " + t.getMessage(), t);
                Toast.makeText(IniciarTreinoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void exibirExercicios() {
        exercisesContainer.removeAllViews();

        if (treino.getExercicios() != null && !treino.getExercicios().isEmpty()) {
            exercisesCountView.setText(String.valueOf(treino.getExercicios().size()));
            
            for (TreinoExercicioDTO exercicio : treino.getExercicios()) {
                View exercicioCard = criarCardExercicio(exercicio);
                exercisesContainer.addView(exercicioCard);
            }
        } else {
            exercisesCountView.setText("0");
        }
    }

    private View criarCardExercicio(TreinoExercicioDTO exercicio) {
        LinearLayout cardLayout = new LinearLayout(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.bottomMargin = 16;
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
        if (exercicio.getNomeGrupoMuscular() != null) {
            TextView grupoMuscular = new TextView(this);
            grupoMuscular.setText(exercicio.getNomeGrupoMuscular());
            grupoMuscular.setTextSize(14);
            grupoMuscular.setPadding(0, 4, 0, 12);
            cardLayout.addView(grupoMuscular);
        }

        // Séries
        if (exercicio.getSeries() != null && !exercicio.getSeries().isEmpty()) {
            TextView seriesLabel = new TextView(this);
            seriesLabel.setText("Séries:");
            seriesLabel.setTextSize(16);
            seriesLabel.setTypeface(seriesLabel.getTypeface(), Typeface.BOLD);
            seriesLabel.setPadding(0, 12, 0, 8);
            cardLayout.addView(seriesLabel);

            if (!execucaoData.containsKey(exercicio.getIdExercicio())) {
                execucaoData.put(exercicio.getIdExercicio(), new HashMap<>());
            }

            for (ExercicioSerieDTO serie : exercicio.getSeries()) {
                View serieView = criarViewSerieEditavel(exercicio.getIdExercicio(), serie);
                cardLayout.addView(serieView);
            }
        }

        return cardLayout;
    }

    private View criarViewSerieEditavel(Long idExercicio, ExercicioSerieDTO serie) {
        LinearLayout serieLayout = new LinearLayout(this);
        serieLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = 8;
        serieLayout.setLayoutParams(params);
        serieLayout.setPadding(8, 8, 8, 8);

        // Número da série
        TextView numSerie = new TextView(this);
        numSerie.setText("Série " + serie.getNumSerie());
        numSerie.setTextSize(14);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        numSerie.setLayoutParams(labelParams);
        serieLayout.addView(numSerie);

        // Campo repetições
        EditText editRepeticoes = new EditText(this);
        editRepeticoes.setHint(serie.getRepeticoes() + " reps");
        editRepeticoes.setInputType(InputType.TYPE_CLASS_NUMBER);
        editRepeticoes.setTextSize(14);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        editRepeticoes.setLayoutParams(inputParams);
        serieLayout.addView(editRepeticoes);

        // Campo carga
        EditText editCarga = new EditText(this);
        editCarga.setHint(serie.getCarga() + " kg");
        editCarga.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editCarga.setTextSize(14);
        editCarga.setLayoutParams(inputParams);
        serieLayout.addView(editCarga);

        // Salvar dados quando o usuário edita
        editRepeticoes.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                salvarDadosSerie(idExercicio, serie.getNumSerie(), editRepeticoes, editCarga, serie);
            }
        });

        editCarga.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                salvarDadosSerie(idExercicio, serie.getNumSerie(), editRepeticoes, editCarga, serie);
            }
        });

        return serieLayout;
    }

    private void salvarDadosSerie(Long idExercicio, Integer numSerie, EditText editRepeticoes, EditText editCarga, ExercicioSerieDTO serieOriginal) {
        try {
            String repText = editRepeticoes.getText().toString().trim();
            String cargaText = editCarga.getText().toString().trim();

            int repeticoes = repText.isEmpty() ? serieOriginal.getRepeticoes() : Integer.parseInt(repText);
            BigDecimal carga = cargaText.isEmpty() ? serieOriginal.getCarga() : new BigDecimal(cargaText);

            Map<Integer, SerieData> seriesExercicio = execucaoData.get(idExercicio);
            if (seriesExercicio != null) {
                seriesExercicio.put(numSerie, new SerieData(repeticoes, carga));
            }
        } catch (NumberFormatException e) {
            Log.e("IniciarTreino", "Erro ao salvar dados da série", e);
        }
    }

    private void finalizarTreino() {
        new AlertDialog.Builder(this)
                .setTitle("Finalizar Treino")
                .setMessage("Deseja finalizar o treino?")
                .setPositiveButton("Sim", (dialog, which) -> enviarExecucao())
                .setNegativeButton("Não", null)
                .show();
    }

    private void enviarExecucao() {
        progressBar.setVisibility(View.VISIBLE);
        btnFinalizar.setEnabled(false);

        SharedPreferences sp = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        Long userId = sp.getLong("id", -1);

        ExecucaoTreinoDTO execucao = new ExecucaoTreinoDTO();
        execucao.setIdUsuario(userId);
        execucao.setNomeExecucaoTreino(treinoNome);
        execucao.setDataHoraExecucao(LocalDateTime.now());

        List<ExecucaoTreinoExercicioDTO> exercicios = new ArrayList<>();
        
        int ordem = 1;
        for (TreinoExercicioDTO exercicio : treino.getExercicios()) {
            ExecucaoTreinoExercicioDTO execExercicio = new ExecucaoTreinoExercicioDTO();
            execExercicio.setIdExercicio(exercicio.getIdExercicio());
            execExercicio.setNomeExercicio(exercicio.getNomeExercicio());
            execExercicio.setNomeGrupoMuscular(exercicio.getNomeGrupoMuscular());
            execExercicio.setOrdem(ordem++);

            List<ExecucaoExercicioSerieDTO> series = new ArrayList<>();
            Map<Integer, SerieData> seriesData = execucaoData.get(exercicio.getIdExercicio());

            if (exercicio.getSeries() != null) {
                for (ExercicioSerieDTO serie : exercicio.getSeries()) {
                    ExecucaoExercicioSerieDTO execSerie = new ExecucaoExercicioSerieDTO();
                    execSerie.setNumSerie(serie.getNumSerie());
                    
                    SerieData data = seriesData != null ? seriesData.get(serie.getNumSerie()) : null;
                    if (data != null) {
                        execSerie.setRepeticoes(data.repeticoes);
                        execSerie.setCarga(data.carga);
                    } else {
                        execSerie.setRepeticoes(serie.getRepeticoes());
                        execSerie.setCarga(serie.getCarga());
                    }
                    
                    series.add(execSerie);
                }
            }

            execExercicio.setSeries(series);
            exercicios.add(execExercicio);
        }

        execucao.setExercicios(exercicios);

        RetrofitClient.getApiService().criarExecucaoTreino(execucao).enqueue(new Callback<ExecucaoTreinoDTO>() {
            @Override
            public void onResponse(@NonNull Call<ExecucaoTreinoDTO> call, @NonNull Response<ExecucaoTreinoDTO> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful()) {
                    Toast.makeText(IniciarTreinoActivity.this, "Treino finalizado com sucesso!", Toast.LENGTH_SHORT).show();
                    stopTimer();
                    finish();
                } else {
                    Toast.makeText(IniciarTreinoActivity.this, "Erro ao finalizar treino", Toast.LENGTH_SHORT).show();
                    btnFinalizar.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ExecucaoTreinoDTO> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnFinalizar.setEnabled(true);
                Toast.makeText(IniciarTreinoActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("IniciarTreino", "Erro", t);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar timer quando a tela não estiver visível
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retomar timer quando voltar para a tela
        if (startTime > 0) {
            timerRunning = true;
            handler.post(timerRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
