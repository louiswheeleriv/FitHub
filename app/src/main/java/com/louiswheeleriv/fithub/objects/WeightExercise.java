package com.louiswheeleriv.fithub.objects;

import java.util.Date;

public class WeightExercise {

    private int id;
    private Exercise exercise;
    private Date date;
    private int numReps;
    private int weight;

    public WeightExercise() {}

    public WeightExercise(int id, Exercise exercise, Date date, int numReps, int weight) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
        this.numReps = numReps;
        this.weight = weight;

    }

    public WeightExercise(Exercise exercise, Date date, int numReps, int weight) {
        this.exercise = exercise;
        this.date = date;
        this.numReps = numReps;
        this.weight = weight;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumReps() {
        return numReps;
    }

    public void setNumReps(int numReps) {
        this.numReps = numReps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
