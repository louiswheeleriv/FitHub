package com.louiswheeleriv.fithub.objects;

import java.util.Date;

public class CardioExercise extends ExerciseInstance {

    private int distance;
    private int duration;
    private int inclination;
    private int resistance;

    public CardioExercise(int id, Exercise exercise, Date date, int distance, int duration, int inclination, int resistance) {
        super(id, exercise, date);
        this.distance = distance;
        this.duration = duration;
        this.inclination = inclination;
        this.resistance = resistance;
    }

    public CardioExercise(Exercise exercise, Date date, int distance, int duration, int inclination, int resistance) {
        super(exercise, date);
        this.distance = distance;
        this.duration = duration;
        this.inclination = inclination;
        this.resistance = resistance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getInclination() {
        return inclination;
    }

    public void setInclination(int inclination) {
        this.inclination = inclination;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
}
