package com.louiswheeleriv.fithub;

import java.util.Date;

public class BodyExercise {

    private int id;
    private Exercise exercise;
    private Date date;
    private int numReps;
    private int duration;

    public BodyExercise() {}

    public BodyExercise(int id, Exercise exercise, Date date, int numReps, int duration) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
        this.numReps = numReps;
        this.duration = duration;
    }

    public BodyExercise(Exercise exercise, Date date, int numReps, int duration) {
        this.exercise = exercise;
        this.date = date;
        this.numReps = numReps;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
