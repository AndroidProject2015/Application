package com.example.app.project.model;

import android.content.Context;
import android.widget.ListView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class ParseModel {
    final public boolean login = false;
    final static int VERSION = 1;
    private final static ParseModel instance = new ParseModel();

    public static ParseModel getInstance() {
        return instance;
    }

    public void init(Context context) {
        Parse.initialize(context, "Y3IoszVq3My4l97JfvWeonOfaAcqmwDAWmPopEWT", "jyvKepSR1A6BkZX21GsITJAEi6fnoCCUC3vSCg3F");
//        ParseObject exercise = new ParseObject("Exercise");
//        exercise.put("exerciseName", "banchpress");
//        exercise.put("muscleGroup", "chest");
//        exercise.saveInBackground();
    }


    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new LinkedList<>();
        ParseQuery query = new ParseQuery("Workout");
        try {
            List<ParseObject> data = query.find();
            for (ParseObject p : data) {
                String workoutName = p.getString("workoutName");
                String dayOfWeek = p.getString("dayOfWeek");
                String muscleGroup = p.getString("muscleGroup");
//                User user = p.getParseUser("user");
//                Exercise exercise = (Exercise)p.getString("exercise");
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            return workouts;
        }
        return workouts;
    }

//public List<Exercise> getAllExercises(){
//    ParseObject
//}


//    public void login(String userName, String password){
//        User user = new User();
//        boolean login;
//        ParseUser.logInInBackground(userName, password, new LogInCallback() {
//            @Override
//            public void done(ParseUser parseUser, ParseException e) {
//                if(parseUser != null){
//                    //TODO
//                }
//                else {
//                    //TODO
//                }
//            }
//        });
//    }


}
