package com.udesc.myapplication.ui.treino;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.udesc.myapplication.R;

public class DetalhesTreinoActivity extends AppCompatActivity {

    public static final String EXTRA_TREINO_ID = "treino_id";
    public static final String EXTRA_TREINO_NOME = "treino_nome";
    public static final String EXTRA_TREINO_DATA = "treino_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalhes_treino);

        Long treinoId = getIntent().getLongExtra(EXTRA_TREINO_ID, -1);
        String treinoNome = getIntent().getStringExtra(EXTRA_TREINO_NOME);
        String treinoData = getIntent().getStringExtra(EXTRA_TREINO_DATA);

        TextView titleView = findViewById(R.id.treino_title);
        TextView dateView = findViewById(R.id.treino_date);

        titleView.setText(treinoNome);
        dateView.setText(treinoData);

        // Aqui você pode carregar mais detalhes do treino usando o treinoId
    }
}
