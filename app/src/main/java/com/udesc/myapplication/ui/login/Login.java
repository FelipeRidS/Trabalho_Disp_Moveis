package com.udesc.myapplication.ui.login;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.udesc.myapplication.MainActivity;
import com.udesc.myapplication.R;
import com.udesc.myapplication.helpers.Navigator;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void entrar(View v) {
        Button b = (Button) v;
        b.setText("Carregando");
        b.setEnabled(false);

        EditText usuarioInptView = findViewById(R.id.editTextText4);
        usuarioInptView.setEnabled(false);
        String usuario = usuarioInptView.getText().toString();

        EditText senhaInptView = findViewById(R.id.editTextTextPassword4);
        senhaInptView.setEnabled(false);
        String senha = senhaInptView.getText().toString();


        /// ---------------------
        /// Lógica de login
        /// ---------------------

        // Função em testes, talvez não seja necessário.
        // É uma abstração da lógica dos intents, se a gente precisar de lógicas
        // mais avançadas a gente deleta essa parte e usa puro mesmo
        Navigator.callActivity(this, MainActivity.class);
    }
}