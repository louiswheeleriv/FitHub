package com.louiswheeleriv.fithub;

import java.util.List;

public class Exercise {

    private int id;
    private String name;
    private List<String> musclesUsed;
    private String exerciseType;

    public Exercise() {}

    public Exercise(int id, String name, List<String> musclesUsed, String exerciseType) {
        this.id = id;
        this.name = name;
        this.musclesUsed = musclesUsed;
        this.exerciseType = exerciseType;
    }

    public Exercise(String name, List<String> musclesUsed, String exerciseType) {
        this.name = name;
        this.musclesUsed = musclesUsed;
        this.exerciseType = exerciseType;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMusclesUsed() {
        return this.musclesUsed;
    }

    public void setMusclesUsed(List<String> musclesUsed) {
        this.musclesUsed = musclesUsed;
    }

    public String getExerciseType() {
        return this.exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

}
