package com.example.app.project.model;

import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class Workout {

    String _dayOfWeek;
    String _workoutName;
    String _muscleGroup;
    List<Exercise> _exercises;

    public List<Exercise> get_exercises() {
        return _exercises;
    }

    public void add_exercises(Exercise exercises) {
        this._exercises.add(exercises);
    }

    public boolean deleteExercise(Exercise exercise){
        if(this._exercises.contains(exercise)){
            this._exercises.remove(exercise);
            return true;
        }
        return false;
    }

    public String getMuscleGroup() {
        return _muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this._muscleGroup = muscleGroup;
    }

    public Workout(String dayOfWeek, String workoutName, String muscleGroup) {
        this._dayOfWeek = dayOfWeek;
        this._workoutName = workoutName;
    }

    public void setDayOfWeek(String day){
        this._dayOfWeek = day;
    }

    public void setWorkoutName(String name){
        this._workoutName = name;
    }

    public String getDayOfWeek() {
        return this._dayOfWeek;
    }

    public String getWorkoutName() {
        return this._workoutName;
    }

}
