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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("usuario/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @POST("usuario")
    Call<Usuario> cadastroUsuario(@Body CadastroUsuario loginRequest);

    @GET("treino")
    Call<List<TreinoDTO>> treinos();

    @GET("treino/{id}")
    Call<TreinoDTO> treinoPorId(@Path("id") Long id);

    @GET("execucao-treino")
    Call<List<ExecucaoTreinoDTO>> execucaoTreinos();

    @POST("execucao-treino")
    Call<ExecucaoTreinoDTO> criarExecucaoTreino(@Body ExecucaoTreinoDTO execucao);

    @GET("exercicio")
    Call<List<ExercicioDTO>> exercicios();
}
