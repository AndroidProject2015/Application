package com.example.app.project.model;

import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class Workout {

    String dayOfWeek;
    String workoutName;
    String muscleGroup;
    List<Exercise> exercises;

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Workout(String dayOfWeek, String workoutName) {
        this.dayOfWeek = dayOfWeek;
        this.workoutName = workoutName;
    }

    public void setDayOfWeek(String day){
        this.dayOfWeek = day;
    }

    public void setWorkoutName(String name){
        this.workoutName = name;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public String getWorkoutName() {
        return this.workoutName;
    }

}
