package com.udesc.myapplication.DTOs;

import java.util.List;

public class TreinoExercicioDTO {
    private Long id;
    private Long idTreino;
    private Long idExercicio;
    private String nomeExercicio;
    private String nomeGrupoMuscular;
    private Integer ordem;
    private Integer descansoSegundos;
    private String observacao;
    private List<ExercicioSerieDTO> series;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(Long idTreino) {
        this.idTreino = idTreino;
    }

    public Long getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Long idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public String getNomeGrupoMuscular() {
        return nomeGrupoMuscular;
    }

    public void setNomeGrupoMuscular(String nomeGrupoMuscular) {
        this.nomeGrupoMuscular = nomeGrupoMuscular;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getDescansoSegundos() {
        return descansoSegundos;
    }

    public void setDescansoSegundos(Integer descansoSegundos) {
        this.descansoSegundos = descansoSegundos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<ExercicioSerieDTO> getSeries() {
        return series;
    }

    public void setSeries(List<ExercicioSerieDTO> series) {
        this.series = series;
    }
}
