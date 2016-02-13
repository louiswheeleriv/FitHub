package com.louiswheeleriv.fithub.objects;

import java.util.Date;

public class CardioExercise extends ExerciseInstance {

    private double distance;
    private int duration;
    private int incline;
    private int resistance;

    public CardioExercise(int id, Exercise exercise, Date date, double distance, int duration, int incline, int resistance) {
        super(id, exercise, date);
        this.distance = distance;
        this.duration = duration;
        this.incline = incline;
        this.resistance = resistance;
    }

    public CardioExercise(Exercise exercise, Date date, double distance, int duration, int incline, int resistance) {
        super(exercise, date);
        this.distance = distance;
        this.duration = duration;
        this.incline = incline;
        this.resistance = resistance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = ((int) distance*10);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIncline() {
        return incline;
    }

    public void setIncline(int incline) {
        this.incline = incline;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
}
