package com.udesc.myapplication.ui.treino;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.udesc.myapplication.R;

public class IniciarTreinoActivity extends AppCompatActivity {

    public static final String EXTRA_TREINO_ID = "treino_id";
    public static final String EXTRA_TREINO_NOME = "treino_nome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_iniciar_treino);

        Long treinoId = getIntent().getLongExtra(EXTRA_TREINO_ID, -1);
        String treinoNome = getIntent().getStringExtra(EXTRA_TREINO_NOME);

        TextView titleView = findViewById(R.id.treino_title);
        titleView.setText(treinoNome);

        // Aqui você pode implementar a lógica para iniciar e registrar o treino
    }
}
