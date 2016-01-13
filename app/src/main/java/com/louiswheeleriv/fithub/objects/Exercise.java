package com.louiswheeleriv.fithub.objects;

import java.util.List;

public class Exercise {

    private int id;
    private String name;
    private String exerciseType;

    public Exercise() {}

    public Exercise(int id, String name, String exerciseType) {
        this.id = id;
        this.name = name;
        this.exerciseType = exerciseType;
    }

    public Exercise(String name, String exerciseType) {
        this.name = name;
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

    public String getExerciseType() {
        return this.exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    @Override
    public String toString() {
        return (name + " - " + exerciseType);
    }

}
