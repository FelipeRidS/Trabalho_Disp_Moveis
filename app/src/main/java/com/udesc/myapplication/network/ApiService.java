package com.udesc.myapplication.network;

import com.udesc.myapplication.model.CadastroUsuario;
import com.udesc.myapplication.model.LoginRequest;
import com.udesc.myapplication.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("usuario/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @POST("usuario")
    Call<Usuario> cadastroUsuario(@Body CadastroUsuario loginRequest);

    // Adicione outros endpoints aqui (ex: criar conta)
    // @POST("usuario")
    // Call<Usuario> criarConta(@Body Usuario novoUsuario);
}
