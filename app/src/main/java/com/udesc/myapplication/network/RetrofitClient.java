package com.udesc.myapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udesc.myapplication.adapters.LocalDateAdapter;
import com.udesc.myapplication.adapters.LocalDateTimeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // URL base do backend.
    // Se estiver usando o emulador Android, '10.0.2.2' é o alias para 'localhost' da máquina.
    // Ajustar a porta se o backend estiver rodando em uma porta diferente de 8080.
    private static final String BASE_URL = "http://10.0.2.2:8081/gestao-de-treinos/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}
