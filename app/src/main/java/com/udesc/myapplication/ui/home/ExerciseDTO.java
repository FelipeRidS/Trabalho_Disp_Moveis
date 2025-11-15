package com.udesc.myapplication.ui.home;

public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
    private Long muscularGroupId;
    private String muscularGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMuscularGroupId() {
        return muscularGroupId;
    }

    public void setMuscularGroupId(Long muscularGroupId) {
        this.muscularGroupId = muscularGroupId;
    }

    public String getMuscularGroupName() {
        return muscularGroupName;
    }

    public void setMuscularGroupName(String muscularGroupName) {
        this.muscularGroupName = muscularGroupName;
    }
}
