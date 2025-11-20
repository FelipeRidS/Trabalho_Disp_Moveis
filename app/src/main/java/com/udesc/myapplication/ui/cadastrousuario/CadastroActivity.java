package com.udesc.myapplication.ui.cadastrousuario;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udesc.myapplication.R;

import java.util.Calendar;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextDataNascimento;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // 1. Vincular os componentes do XML com as variáveis Java
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        // 2. Configurar o seletor de data para o campo de data de nascimento
        editTextDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSeletorDeData();
            }
        });

        // 3. Configurar a ação do botão de cadastro
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void mostrarSeletorDeData() {
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // O mês retorna de 0 a 11, então somamos 1
                        String dataFormatada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                        editTextDataNascimento.setText(dataFormatada);
                    }
                }, ano, mes, dia);

        datePickerDialog.show();
    }

    private void cadastrarUsuario() {
        // 4. Capturar os textos dos campos
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dataNascimento = editTextDataNascimento.getText().toString().trim();
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfirmarSenha.getText().toString();

        // 5. Realizar as validações
        if (nome.isEmpty() || email.isEmpty() || dataNascimento.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            editTextConfirmarSenha.requestFocus(); // Foca no campo para correção
            return;
        }

        // Validação básica de senha (ex: mínimo 6 caracteres)
        if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve ter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- LÓGICA DE CADASTRO ---
        // Aqui você implementaria a lógica para salvar o usuário.
        // Por exemplo, enviar para um servidor web, salvar em um banco de dados local (SQLite),
        // ou usar o Firebase Authentication.

        boolean deuCerto = true;
        if (deuCerto) {
            SharedPreferences sp = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id", 345L); // TODO: vai vir do login
            editor.putString("nome", nome);
            editor.putString("email", email);
            editor.putString("dataNascimento", dataNascimento);
            editor.apply();
        }

        // Exemplo de mensagem de sucesso:
        Toast.makeText(this, "Usuário " + nome + " cadastrado com sucesso!", Toast.LENGTH_LONG).show();

        // Opcional: Após o cadastro, você pode limpar os campos ou fechar a tela
        // finish();
    }
}