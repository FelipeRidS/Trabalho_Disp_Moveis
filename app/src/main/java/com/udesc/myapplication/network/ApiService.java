package com.udesc.myapplication.network;

import com.udesc.myapplication.model.LoginRequest;
import com.udesc.myapplication.model.Usuario;
import com.udesc.myapplication.model.UsuarioCreateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("usuario/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @POST("usuario")
    Call<Usuario> registrar(@Body UsuarioCreateRequest usuarioCreateRequest);
}
