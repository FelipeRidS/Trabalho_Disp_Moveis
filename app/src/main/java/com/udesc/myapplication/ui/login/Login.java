package com.udesc.myapplication.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.udesc.myapplication.MainActivity;
import com.udesc.myapplication.R;
import com.udesc.myapplication.helpers.Navigator;
import com.udesc.myapplication.ui.cadastrousuario.CadastroActivity;
import com.udesc.myapplication.model.LoginRequest;
import com.udesc.myapplication.model.Usuario;
import com.udesc.myapplication.network.ApiService;
import com.udesc.myapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText usuarioInptView;
    private EditText senhaInptView;
    private Button loginButton;
    private ApiService apiService;

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

        // Inicializar views e serviço
        usuarioInptView = findViewById(R.id.editTextText4);
        senhaInptView = findViewById(R.id.editTextTextPassword4);
        loginButton = findViewById(R.id.button5);

        // Obter instância do ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public void entrar(View v) {
        String email = usuarioInptView.getText().toString(); // O backend espera um email
        String senha = senhaInptView.getText().toString();

        // Validação simples
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha o e-mail e a senha", Toast.LENGTH_SHORT).show();
            return;
        }

        // Desabilitar UI
        setLoading(true);

        // Criar objeto de requisição
        LoginRequest loginRequest = new LoginRequest(email, senha);

        // Fazer a chamada de rede assíncrona
        apiService.login(loginRequest).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                // Reabilitar UI
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    // Sucesso no login
                    Usuario usuario = response.body();
                    Toast.makeText(Login.this, "Bem-vindo, " + usuario.getNome(), Toast.LENGTH_SHORT).show();

                    // Navegar para a MainActivity
                    Navigator.setActivity(Login.this, MainActivity.class);
                    finish(); // Fecha a atividade de Login
                } else {
                    // Falha no login (ex: 401 Não Autorizado)
                    Toast.makeText(Login.this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                // Reabilitar UI
                setLoading(false);

                // Erro de rede (sem conexão, servidor offline, etc)
                Toast.makeText(Login.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método auxiliar para controlar o estado de carregamento da UI
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            loginButton.setText("Carregando...");
            loginButton.setEnabled(false);
            usuarioInptView.setEnabled(false);
            senhaInptView.setEnabled(false);
        } else {
            loginButton.setText("Entrar");
            loginButton.setEnabled(true);
            usuarioInptView.setEnabled(true);
            senhaInptView.setEnabled(true);
        }
    }

    public void entrarTeste(View v) {
        // Apenas navega para a tela principal, sem fazer login
        Navigator.callActivity(this, MainActivity.class);

        // Fecha a atividade de Login para que o usuário não volte para ela
        finish();
    }

    public void cadastroUsuario(View v) {
        Navigator.callActivity(this, CadastroActivity.class);
    }
}
