package com.udesc.myapplication.ui.perfil; // Ajuste o pacote conforme sua estrutura

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.udesc.myapplication.R; // Importe seu Rn
import com.udesc.myapplication.helpers.Navigator;
import com.udesc.myapplication.ui.login.Login;

public class PerfilFragment extends Fragment {

    private TextView textViewNome, textViewEmail, textViewDataNascimento;
    private Button buttonLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Infla o layout do fragment. Isso é diferente da Activity.
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        // 2. Vincula os componentes da view. É preciso usar a 'root' para o findViewById.
        textViewNome = root.findViewById(R.id.textViewNome);
        textViewEmail = root.findViewById(R.id.textViewEmail);
        textViewDataNascimento = root.findViewById(R.id.textViewDataNascimento);
        buttonLogout = root.findViewById(R.id.buttonLogout);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 3. O código de lógica vai aqui, no onViewCreated.
        // Este método é chamado logo após o onCreateView, garantindo que a view já existe.
        carregarDadosDoUsuario();

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogout();
            }
        });
    }

    private void carregarDadosDoUsuario() {
        // --- LÓGICA PARA BUSCAR DADOS ---
        // A lógica aqui é a mesma: buscar de SharedPreferences, banco de dados, etc.
        // **Exemplo Fixo (substitua pela sua lógica real):**
        String nomeUsuario = "Felipe Nobre Gemini"; // buscarDados.getNome();
        String emailUsuario = "felipe.gemini@example.com"; // buscarDados.getEmail();
        String dataNascUsuario = "01/01/1990"; // buscarDados.getDataNascimento();

        // Exibir os dados
        textViewNome.setText(nomeUsuario);
        textViewEmail.setText(emailUsuario);
        textViewDataNascimento.setText(dataNascUsuario);
    }

    private void fazerLogout() {
        // --- LÓGICA DE LOGOUT ---
        // Limpe os dados da sessão (SharedPreferences, etc.)

        // Para mostrar um Toast, use getActivity() ou requireContext() para obter o contexto
        Toast.makeText(getActivity(), "Logout realizado com sucesso!", Toast.LENGTH_SHORT).show();

        // Redireciona para a tela de Login
        Navigator.setActivity(getActivity(), Login.class);
    }
}
