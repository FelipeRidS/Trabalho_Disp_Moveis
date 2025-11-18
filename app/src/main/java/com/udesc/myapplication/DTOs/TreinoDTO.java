package com.udesc.myapplication.DTOs;

import java.time.LocalDate;
import java.util.List;

public class TreinoDTO {
    private Long idTreino;
    private String nomeTreino;
    private LocalDate dataCriacao;
    private List<TreinoExercicioDTO> exercicios;

    public Long getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(Long idTreino) {
        this.idTreino = idTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<TreinoExercicioDTO> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<TreinoExercicioDTO> exercicios) {
        this.exercicios = exercicios;
    }
}
