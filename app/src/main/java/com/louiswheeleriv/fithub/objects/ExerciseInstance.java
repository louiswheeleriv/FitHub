package com.louiswheeleriv.fithub.objects;

import java.util.Date;

public class ExerciseInstance {

    private int id;
    private Exercise exercise;
    private Date date;

    public ExerciseInstance() {}

    public ExerciseInstance(int id, Exercise exercise, Date date) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
    }

    public ExerciseInstance(Exercise exercise, Date date) {
        this.exercise = exercise;
        this.date = date;
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

}
