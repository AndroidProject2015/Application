package com.example.app.project.model;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class Workout {

    //TODO ad boolean public checkBOx
    String _dayOfWeek;
    String _workoutName;
    String _muscleGroup;

    List<Exercise> _exercises = new LinkedList<Exercise>();

    boolean _isPublic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    ParseObject _workout;

    public Workout(String dayOfWeek, String muscleGroup, String workoutName, boolean isPublic ){
        this._dayOfWeek = dayOfWeek;
        this._muscleGroup = muscleGroup;
        this._workoutName = workoutName;
        this._isPublic = isPublic;

    }

    public void set_exercises(List<Exercise> _exercises) {
        this._exercises=_exercises;
    }

    public List<Exercise> get_exercises() {
        return _exercises;
    }

    public boolean is_isPublic() {
        return _isPublic;
    }

    public void set_isPublic(boolean _isPublic) {
        this._isPublic = _isPublic;
    }
//    public void add_exercises(List<Exercise> exercises) {
//        ParseModel.getInstance().addExersiceToWorkout(exercises);
//    }

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

    public void  setParseWorkout(ParseObject workout) {this._workout = workout;}

    public ParseObject getParseWorkout(){return  this._workout;}

}
