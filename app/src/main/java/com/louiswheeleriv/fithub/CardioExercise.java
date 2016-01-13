package com.louiswheeleriv.fithub;

import java.util.Date;

public class CardioExercise {

    private int id;
    private Exercise exercise;
    private Date date;
    private int distance;
    private int duration;
    private int inclination;
    private int resistance;

    public CardioExercise() {}

    public CardioExercise(int id, Exercise exercise, Date date, int distance, int duration, int inclination, int resistance) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
        this.distance = distance;
        this.duration = duration;
        this.inclination = inclination;
        this.resistance = resistance;
    }

    public CardioExercise(Exercise exercise, Date date, int distance, int duration, int inclination, int resistance) {
        this.exercise = exercise;
        this.date = date;
        this.distance = distance;
        this.duration = duration;
        this.inclination = inclination;
        this.resistance = resistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
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
}
