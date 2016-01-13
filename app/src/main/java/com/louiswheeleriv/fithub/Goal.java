package com.louiswheeleriv.fithub;

public class Goal {

    private int id;
    private int exerciseId;
    private int targetReps;
    private int targetWeight;
    private int targetDistance;
    private int targetDuration;
    private boolean isGoalUp;

    public Goal() {}

    public Goal(int id, int exerciseId, int targetReps, int targetWeight,
                int targetDistance, int targetDuration, boolean isGoalUp) {

        this.id = id;
        this.exerciseId = exerciseId;
        this.targetReps = targetReps;
        this.targetWeight = targetWeight;
        this.targetDistance = targetDistance;
        this.targetDuration = targetDuration;
        this.isGoalUp = isGoalUp;
    }

    public Goal(int exerciseId, int targetReps, int targetWeight,
                int targetDistance, int targetDuration, boolean isGoalUp) {

        this.exerciseId = exerciseId;
        this.targetReps = targetReps;
        this.targetWeight = targetWeight;
        this.targetDistance = targetDistance;
        this.targetDuration = targetDuration;
        this.isGoalUp = isGoalUp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(int targetDistance) {
        this.targetDistance = targetDistance;
    }

    public int getTargetDuration() {
        return targetDuration;
    }

    public void setTargetDuration(int targetDuration) {
        this.targetDuration = targetDuration;
    }

    public boolean isGoalUp() {
        return isGoalUp;
    }

    public void setIsGoalUp(boolean isGoalUp) {
        this.isGoalUp = isGoalUp;
    }
}
