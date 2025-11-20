package com.udesc.myapplication.ui.cadastrousuario;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.udesc.myapplication.MainActivity;
import com.udesc.myapplication.R;
import com.udesc.myapplication.helpers.DateHelpers;
import com.udesc.myapplication.helpers.Navigator;
import com.udesc.myapplication.model.CadastroUsuario;
import com.udesc.myapplication.model.Usuario;
import com.udesc.myapplication.network.ApiService;
import com.udesc.myapplication.network.RetrofitClient;
import com.udesc.myapplication.ui.login.Login;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextDataNascimento;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private Button buttonCadastrar;
    private ApiService apiService;

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
        apiService = RetrofitClient.getClient().create(ApiService.class);
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

        CadastroUsuario usuarioCadastro = new CadastroUsuario();
        usuarioCadastro.setNome(nome);
        usuarioCadastro.setDataNascimento(DateHelpers.parse(dataNascimento));
        usuarioCadastro.setEmail(email);
        usuarioCadastro.setSenha(senha);
        setLoading(true);

        String a = new Gson().toJson(usuarioCadastro);
        Log.d("tag", a);

        apiService.cadastroUsuario(usuarioCadastro).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                // Reabilitar UI
                setLoading(false);
                Log.d("tag", "hmmmm");

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("tag", "hmmmm2");
                    // Sucesso no login
                    Usuario usuario = response.body();
                    Log.d("tag", usuario.toString());

                    SharedPreferences sp = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putLong("id", usuario.getId());
                    editor.putString("nome", usuario.getNome());
                    editor.putString("email", usuario.getEmail());
                    editor.putString("dataNascimento", usuario.getDataNascimento().toString());
                    editor.apply();

                    Toast.makeText(CadastroActivity.this, "Bem-vindo, " + usuario.getNome(), Toast.LENGTH_SHORT).show();

                    // Navegar para a MainActivity
                    Navigator.setActivity(CadastroActivity.this, MainActivity.class);
                    finish(); // Fecha a atividade de Login
                } else {
                    // Falha no login (ex: 401 Não Autorizado)
                    Toast.makeText(CadastroActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                // Reabilitar UI
                setLoading(false);

                // Erro de rede (sem conexão, servidor offline, etc)
                Toast.makeText(CadastroActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Exemplo de mensagem de sucesso:
        Toast.makeText(this, "Usuário " + nome + " cadastrado com sucesso!", Toast.LENGTH_LONG).show();

        // Opcional: Após o cadastro, você pode limpar os campos ou fechar a tela
        // finish();
    }

    // Método auxiliar para controlar o estado de carregamento da UI
    private void setLoading(boolean isLoading) {
        buttonCadastrar.setText(isLoading ? "Carregando..." : "Entrar");
        buttonCadastrar.setEnabled(!isLoading);
        editTextNome.setEnabled(!isLoading);
        editTextEmail.setEnabled(!isLoading);
        editTextDataNascimento.setEnabled(!isLoading);
        editTextSenha.setEnabled(!isLoading);
        editTextConfirmarSenha.setEnabled(!isLoading);
    }
}