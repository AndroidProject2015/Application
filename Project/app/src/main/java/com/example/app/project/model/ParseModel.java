package com.example.app.project.model;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
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

    public interface GetExerciseListener {
        public void onResult(List<Exercise> e);
    }


    public static ParseModel getInstance() {
        return instance;
    }

    public void init(Context context) {
        Parse.initialize(context, "Y3IoszVq3My4l97JfvWeonOfaAcqmwDAWmPopEWT", "jyvKepSR1A6BkZX21GsITJAEi6fnoCCUC3vSCg3F");
//        ParseUser u = ParseUser.getCurrentUser();
//        //Initialization of Workouts
//        ParseObject workout = new ParseObject("Workout");
//        workout.put("dayOfWeek", "2");
//        workout.put("workoutName","TESTTTTTTTT");
//        workout.put("muscleGroup", "LEG");
//        workout.put("public", false);
//        workout.put("users", u);
//        workout.saveInBackground();

//        Initialization of Execrices
//        ParseObject exercise;
//        for(int i = 1; i<=50; i++) {
//            exercise = new ParseObject("Exercise");
//            exercise.put("exerciseName", "Ex"+i);
//            exercise.put("muscleGroup", "MG"+(i%10)+1);
//            exercise.put("linkToYouTube", "https://www.youtube.com/watch?v=_EtwJJSLfMc");
//            exercise.saveInBackground();
//        }
    }

//    public interface GetWorkoutListener{
//        public void onResult(List<Workout> workouts);
//    }

    public void getUserWorkouts(final GetWorkoutsListener listener) {
        ParseQuery query = new ParseQuery("Workout");
        final ParseUser currentUser = ParseUser.getCurrentUser();
        //query.whereEqualTo("workoutUsers", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                List<Workout> userWorkouts = new LinkedList<>();
                if (e == null) {
                    for (ParseObject po : parseObjects) {
                        Boolean conlains = false;

                        List<ParseUser> l = po.getList("workoutUsers");
                        if (l != null) {
                            for (ParseUser p : l) {
                                if (p.hasSameId(currentUser)) {
                                    conlains = true;
                                    break;
                                }
                            }
                        }

                        if (conlains) {
                            String name = po.getString("workoutName");
                            String muscleGroup = po.getString("muscleGroup");
                            String day = po.getString("dayOfWeek");
                            Workout w = new Workout(day, name, muscleGroup, false);
                            w.setParseWorkout(po);
                            userWorkouts.add(w);
                        }

                    }
                }
                listener.onResult(userWorkouts);
            }
        });
    }


    public void getAllWorkouts(final GetWorkoutsListener listener) {

        ParseQuery query = new ParseQuery("Workout");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> data, ParseException e) {
                List<Workout> workouts = new LinkedList<>();
                if (e == null) {
                    for (ParseObject p : data) {
                        if (p.getBoolean("public")) {
                            String workoutName = p.getString("workoutName");
                            String dayOfWeek = p.getString("dayOfWeek");
                            String muscleGroup = p.getString("muscleGroup");
                            boolean isPublic = p.getBoolean("public");
                            Workout w = new Workout(dayOfWeek, muscleGroup, workoutName, isPublic);
                            w.setParseWorkout(p);
                            workouts.add(w);
                        }
                    }
                }

                listener.onResult(workouts);
            }
        });

    }

    public void getAllExercises(final GetExerciseListener exercisesListener) {
        ParseQuery query = new ParseQuery("Exercise");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> data, ParseException e) {
                List<Exercise> exercises = new LinkedList<Exercise>();
                if (e == null) {
                    for (ParseObject p : data) {
                        String exerciseName = p.getString("exerciseName");
                        String muscleGroup = p.getString("muscleGroup");
                        String linkToYouTube = p.getString("linkToYouTube");
                        Exercise ex = new Exercise(exerciseName, muscleGroup, linkToYouTube);
                        exercises.add(ex);
                    }
                }

                exercisesListener.onResult(exercises);
            }
        });
    }

    public void getGlobalWorkOut(final GetWorkoutsListener listener) {

        ParseQuery query = new ParseQuery("Workout");
        //query.whereNotEqualTo("workoutUsers", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> data, ParseException e) {
                List<Workout> workouts = new LinkedList<>();
                if (e == null) {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    for (ParseObject p : data) {

                        List<ParseUser> l = p.getList("workoutUsers");
                        Boolean conlains = false;

                        for (ParseUser po : l) {
                            if (po.hasSameId(currentUser)) {
                                conlains = true;
                                break;
                            }
                        }


                        if (p.getBoolean("public") && !conlains) {
                            String workoutName = p.getString("workoutName");
                            String dayOfWeek = p.getString("dayOfWeek");
                            String muscleGroup = p.getString("muscleGroup");
                            boolean isPublic = p.getBoolean("public");
                            Workout w = new Workout(dayOfWeek, muscleGroup, workoutName, isPublic);
                            w.setParseWorkout(p);
                            workouts.add(w);
                        }
                    }
                }

                listener.onResult(workouts);
            }
        });
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

    public void getSearchWorkOut(final String[] searchCredentials, final GetWorkoutsListener listener) {


        ParseQuery q = ParseQuery.getQuery("_User");

        if (searchCredentials[1].length() != 0) {
            q.whereEqualTo("email", searchCredentials[1]);
        }


        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> data, ParseException e) {

                if (e == null) {
                    for (ParseObject p : data) {

                        ParseQuery query = new ParseQuery("Workout");
                        //query.whereNotEqualTo("workoutUsers", ParseUser.getCurrentUser());


                        if (searchCredentials[1].length() != 0) {
                            query.whereEqualTo("users", p);
                        }


                        if (searchCredentials[0].length() != 0) {

                            query.whereEqualTo("workoutName", searchCredentials[0]);
                        }


                        if (searchCredentials[2].length() != 0) {
                            query.whereEqualTo("muscleGroup", searchCredentials[2]);
                        }


                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> data, ParseException e) {
                                List<Workout> workouts = new LinkedList<>();
                                if (e == null) {
                                    ParseUser currentUser = ParseUser.getCurrentUser();
                                    for (ParseObject p : data) {
                                        {
                                            List<ParseUser> l = p.getList("workoutUsers");
                                            Boolean conlains = false;

                                            for (ParseUser po : l) {
                                                if (po.hasSameId(currentUser)) {
                                                    conlains = true;
                                                    break;
                                                }
                                            }

                                            if (p.getBoolean("public") && !conlains) {
                                                String workoutName = p.getString("workoutName");
                                                String dayOfWeek = p.getString("dayOfWeek");
                                                String muscleGroup = p.getString("muscleGroup");
                                                boolean isPublic = p.getBoolean("public");
                                                Workout w = new Workout(dayOfWeek, muscleGroup, workoutName, isPublic);
                                                w.setParseWorkout(p);
                                                workouts.add(w);
                                            }
                                        }
                                    }
                                }

                                listener.onResult(workouts);
                            }
                        });
                    }
                    ;
                } else {
                    Log.d("App", e.getMessage());
                }
            }
        });


    }

    public void addUserToWorkout(ParseObject w) {
        List<ParseUser> workoutUsers = w.getList("workoutUsers");
        workoutUsers.add(ParseUser.getCurrentUser());
        w.put("workoutUsers", workoutUsers);
        w.saveInBackground();
    }

    public void createWorkout(Workout workout) {
        List<ParseUser> users = new LinkedList<ParseUser>();
        List<ParseObject> exList = new LinkedList<ParseObject>();
        ParseUser u = ParseUser.getCurrentUser();
        users.add(u);
        ParseObject w = new ParseObject("Workout");
        w.put("dayOfWeek", workout.getDayOfWeek());
        w.put("workoutName", workout.getWorkoutName());
        w.put("muscleGroup", workout.getMuscleGroup());
        w.put("public", workout.is_isPublic());
        w.put("users", u);
        w.put("workoutUsers", users);
        w.put("exercises", exList);
        w.saveInBackground();
    }

    public void setExerciseToWorkout(final Workout workout) {
        ParseQuery query = ParseQuery.getQuery("Exersice");
        final List<ParseObject> ex = new LinkedList<ParseObject>();

    }

    public void getWorkoutExercises(final Workout workout, GetExerciseListener listener) {
        ParseQuery query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("workoutName", workout.getWorkoutName());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject p : list) {
                        if (p.get("user") == ParseUser.getCurrentUser()) {

                            p.put("exercises", workout.get_exercises());

                        }
                    }
                }

            }

        });
    }
}
