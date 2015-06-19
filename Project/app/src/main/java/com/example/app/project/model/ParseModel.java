package com.example.app.project.model;

import android.content.Context;
import android.widget.ListView;

import com.parse.FindCallback;
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

    public interface GetWorkoutsListener {
        public void onResult(List<Workout> w);
    }

    public interface GetExerciseLitener {
        public void onResult(List<Exercise> e);
    }


    public static ParseModel getInstance() {
        return instance;
    }

    public void init(Context context) {
        Parse.initialize(context, "Y3IoszVq3My4l97JfvWeonOfaAcqmwDAWmPopEWT", "jyvKepSR1A6BkZX21GsITJAEi6fnoCCUC3vSCg3F");
//        ParseUser u = ParseUser.getCurrentUser();
//        ParseObject exercise = new ParseObject("Workout");
//        exercise.put("dayOfWeek", "2");
//        exercise.put("workoutName","testRelation");
//        exercise.put("muscleGroup", "chest");
//        exercise.put("public", false);
//        exercise.put("users", u);
//        exercise.saveInBackground();
    }

//    public interface GetWorkoutListener{
//        public void onResult(List<Workout> workouts);
//    }

    public void getUserWorkouts(final GetWorkoutsListener listener) {
        ParseQuery query = new ParseQuery("Workout");
        ParseUser currentUser = ParseUser.getCurrentUser();
        query.whereEqualTo("users", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                List<Workout> userWorkouts = new LinkedList<>();
                if (e == null) {
                    for (ParseObject po : parseObjects) {
                        String name = po.getString("workoutName");
                        String muscleGroup = po.getString("muscleGroup");
                        String day = po.getString("dayOfWeek");
                        Workout w = new Workout(day, name, muscleGroup);
                        userWorkouts.add(w);
                    }
                }
                listener.onResult(userWorkouts);
            }
        });
    }

    public void addWorkoutToUser(Workout workout) {
        ParseUser curUser = ParseUser.getCurrentUser();

    }


    public void getAllWorkouts(final GetWorkoutsListener listener) {

        ParseQuery query = new ParseQuery("Workout");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> data, ParseException e) {
                List<Workout> workouts = new LinkedList<>();
                if (e == null) {
                    for (ParseObject p : data) {
                        if(p.getBoolean("public")) {
                            String workoutName = p.getString("workoutName");
                            String dayOfWeek = p.getString("dayOfWeek");
                            String muscleGroup = p.getString("muscleGroup");
                            Workout w = new Workout(dayOfWeek, muscleGroup, workoutName);
                            workouts.add(w);
                        }
                    }
                }

                listener.onResult(workouts);
            }
        });

    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new LinkedList<>();
        ParseQuery query = new ParseQuery("Exercise");
        return exercises;
    }


    public static String login(String email, String pass) {
        final String[] msg = new String[1];
        ParseUser.logInInBackground(email, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    msg[0] = "Success";

                } else {
                    msg[0] = e.getMessage();

                }
            }

        });
        return msg[0];
    }


    public void addExersiceToWorkout(Exercise exercises) {
//        ParseObject
    }
}
