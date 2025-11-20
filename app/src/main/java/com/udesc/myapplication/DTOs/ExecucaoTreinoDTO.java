package com.udesc.myapplication.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class ExecucaoTreinoDTO {
    private Long idExecucaoTreino;
    private Long idUsuario;
    private String nomeUsuario;
    private String nomeExecucaoTreino;
    private LocalDateTime dataHoraExecucao;
    private List<ExecucaoTreinoExercicioDTO> exercicios;

    public Long getIdExecucaoTreino() {
        return idExecucaoTreino;
    }

    public void setIdExecucaoTreino(Long idExecucaoTreino) {
        this.idExecucaoTreino = idExecucaoTreino;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeExecucaoTreino() {
        return nomeExecucaoTreino;
    }

    public void setNomeExecucaoTreino(String nomeExecucaoTreino) {
        this.nomeExecucaoTreino = nomeExecucaoTreino;
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
