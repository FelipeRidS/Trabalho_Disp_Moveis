package com.udesc.myapplication.ui.perfil; // Ajuste o pacote conforme sua estrutura

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        Activity c = getActivity();
        SharedPreferences sp = c.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String nomeUsuario = sp.getString("nome", "");
        String emailUsuario = sp.getString("email", "");
        String dataNascUsuario = sp.getString("dataNascimento", "");

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

        Activity c = getActivity();
        SharedPreferences sp = c.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("id");
        editor.remove("nome");
        editor.remove("email");
        editor.remove("dataNascimento");
        editor.apply();

        // Redireciona para a tela de Login
        Navigator.setActivity(getActivity(), Login.class);
    }
}
