package com.louiswheeleriv.fithub.objects;

import java.util.Date;

public class BodyExercise extends ExerciseInstance {

    private int numReps;
    private int duration;

    public BodyExercise(int id, Exercise exercise, Date date, int numReps, int duration) {
        super(id, exercise, date);
        this.numReps = numReps;
        this.duration = duration;
    }

    public BodyExercise(Exercise exercise, Date date, int numReps, int duration) {
        super(exercise, date);
        this.numReps = numReps;
        this.duration = duration;
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
