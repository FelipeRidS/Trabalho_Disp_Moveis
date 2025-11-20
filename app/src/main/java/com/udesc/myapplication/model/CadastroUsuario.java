package com.udesc.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class CadastroUsuario {
    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("dataNascimento")
    private LocalDate dataNascimento;

    @SerializedName("senha")
    private String senha;

    // Getters
    public String getSenha() {
        return senha;
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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
