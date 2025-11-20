package com.udesc.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Usuario {

    @SerializedName("id")
    private Long id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("dataNascimento")
    private LocalDate dataNascimento;

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
