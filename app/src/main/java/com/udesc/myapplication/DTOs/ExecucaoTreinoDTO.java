package com.udesc.myapplication.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class ExecucaoTreinoDTO {
    private Long idTreino;
    private String nomeTreino;
    private LocalDateTime dataHoraExecucao;
    private List<ExecucaoTreinoExercicioDTO> exercicios;

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

    public LocalDateTime getDataHoraExecucao() {
        return dataHoraExecucao;
    }

    public void setDataHoraExecucao(LocalDateTime dataHoraExecucao) {
        this.dataHoraExecucao = dataHoraExecucao;
    }

    public List<ExecucaoTreinoExercicioDTO> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<ExecucaoTreinoExercicioDTO> exercicios) {
        this.exercicios = exercicios;
    }
}
