package com.louiswheeleriv.fithub.objects;

public class Exercise {

    private int id;
    private String name;
    private String exerciseType;
    private boolean includesIncline;
    private boolean includesResistance;

    public Exercise() {}

    public Exercise(int id, String name, String exerciseType) {
        this.id = id;
        this.name = name;
        this.exerciseType = exerciseType;
        this.includesIncline = false;
        this.includesResistance = false;
    }

    public Exercise(String name, String exerciseType) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.includesIncline = false;
        this.includesResistance = false;
    }

    public Exercise(int id, String name, String exerciseType, boolean includesIncline, boolean includesResistance) {
        this.id = id;
        this.name = name;
        this.exerciseType = exerciseType;
        this.includesIncline = includesIncline;
        this.includesResistance = includesResistance;
    }

    public Exercise(String name, String exerciseType, boolean includesIncline, boolean includesResistance) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.includesIncline = includesIncline;
        this.includesResistance = includesResistance;
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

    public boolean includesIncline() {
        return this.includesIncline;
    }

    public void setIncludesIncline(boolean includesIncline) {
        this.includesIncline = includesIncline;
    }

    public boolean includesResistance() {
        return this.includesResistance;
    }

    public void setIncludesResistance(boolean includesResistance) {
        this.includesResistance = includesResistance;
    }

    @Override
    public String toString() {
        return (name + " - " + exerciseType);
    }

}
