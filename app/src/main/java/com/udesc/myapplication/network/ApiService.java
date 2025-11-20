package com.udesc.myapplication.network;

import com.udesc.myapplication.DTOs.ExecucaoTreinoDTO;
import com.udesc.myapplication.model.CadastroUsuario;
import com.udesc.myapplication.DTOs.ExercicioDTO;
import com.udesc.myapplication.DTOs.TreinoDTO;
import com.udesc.myapplication.model.LoginRequest;
import com.udesc.myapplication.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("usuario/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @POST("usuario")
    Call<Usuario> cadastroUsuario(@Body CadastroUsuario loginRequest);

    @GET("execucao-treino")
    Call<List<ExecucaoTreinoDTO>> treinos();

    @GET("exercicio")
    Call<List<ExercicioDTO>> exercicios();
}
