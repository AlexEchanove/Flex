package com.example.flex.model;
import java.util.ArrayList;
import java.util.Date;

public class Workout {


    public Date timeOfWorkout; //this is used as a key
    public ArrayList<String> exercise;

    public ArrayList<String> reps;
    public ArrayList<String> weight;
    public String user;

    public Workout() {
        timeOfWorkout = new Date();
        exercise = new ArrayList<String>();
        reps = new ArrayList<String>();
        weight = new ArrayList<String>();
    }

    public Workout(ArrayList<String> exercise, ArrayList<String> reps, ArrayList<String> weight) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        timeOfWorkout = new Date();

    }
    public Date getTimeOfWorkout() {
        return timeOfWorkout;
    }

    public void setTimeOfWorkout(Date timeOfWorkout) {
        this.timeOfWorkout = timeOfWorkout;
    }

    public ArrayList<String> getExercise() {
        return exercise;
    }

    public void setExercise(ArrayList<String> exercise) {
        this.exercise = exercise;
    }

    public ArrayList<String> getReps() {
        return reps;
    }

    public void setReps(ArrayList<String> reps) {
        this.reps = reps;
    }

    public ArrayList<String> getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<String> weight) {
        this.weight = weight;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
