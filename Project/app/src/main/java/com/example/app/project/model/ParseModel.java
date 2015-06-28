package com.example.app.project.model;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class ParseModel {
    final public boolean login = false;
    final static int VERSION = 1;
    private int limit = 0;
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
                            boolean isPublic = po.getBoolean("public");
                            Workout w = new Workout(day, muscleGroup, name, isPublic);
                            w.setId(po.getObjectId());
                            List<Exercise> exercises = new LinkedList<Exercise>();

                            List<ParseObject> exFromWorkout = po.getList("exercises");
                            ParseQuery exQuery = ParseQuery.getQuery("Exercise");

                            try {
                                List<ParseObject> parseEx = exQuery.find();
                                for (ParseObject p : parseEx) {
                                    for (ParseObject pEx : exFromWorkout) {
                                        if (p.hasSameId(pEx)) {
                                            String mg = p.getString("muscleGroup");
                                            String youTube = p.getString("linkToYouTube");
                                            String exName = p.getString("exerciseName");
                                            Exercise ex = new Exercise(exName, mg, youTube);
                                            exercises.add(ex);
                                        }
                                    }
                                }
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            // = po.getParseObject("exercises").fetch();


                            w.set_exercises(exercises);
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
                        ex.setExId(p.getObjectId());
                        exercises.add(ex);
                    }
                }

                exercisesListener.onResult(exercises);
            }
        });
    }

    public void getAllExercisesAsync(final GetExerciseListener exercisesListener) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Exercise");
        query.orderByAscending("createdAt");
        // Set the limit of objects to show
        query.setLimit(limit += 10);
        List<ParseObject> parseObjects;
        try {
            parseObjects = query.find();// InBackground(new FindCallback<ParseObject>() {

//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
            List<Exercise> exercises = new ArrayList<Exercise>();
//                if (e == null) {
            for (ParseObject exercise : parseObjects) {
                String exerciseName = exercise.getString("exerciseName");
                String muscleGroup = exercise.getString("muscleGroup");
                String linkToYouTube = exercise.getString("linkToYouTube");
                Exercise ex = new Exercise(exerciseName, muscleGroup, linkToYouTube);
                ex.setExId(exercise.getObjectId());
                exercises.add(ex);
            }
//                }
            exercisesListener.onResult(exercises);
//            }
//        });
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        List<ParseObject> exList = getExercisesNewWorkout(workout);
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

    public List<ParseObject> getExercisesNewWorkout(final Workout workout) {
        ParseQuery query = ParseQuery.getQuery("Exercise");
        List<Exercise> exercises = workout.get_exercises();
        List<ParseObject> exFromParse = new LinkedList<ParseObject>();
        try {
            List<ParseObject> exObj = query.find();
            for (ParseObject p : exObj) {
                for (Exercise ex : exercises) {
                    String parseExName = p.getString("exerciseName");
                    String exName = ex.getExerciseName();
                    if (parseExName.equals(exName)) {
                        exFromParse.add(p);
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exFromParse;
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    for (ParseObject p : list) {
//
//                        if(p.getString("exerciseName")== )
//                    }
//                }
//
//            }
//
//        });
    }


    public void getWorkoutByName(final String workoutName, final GetWorkoutsListener listener) {
        ParseQuery query = ParseQuery.getQuery("Workout");
        final List<Workout> workouts = new LinkedList<Workout>();
        try {
            List<ParseObject> parseObjects = query.find();
            for (ParseObject p : parseObjects) {
                String wName = p.getString("workoutName");
                if (workoutName.equals(wName)) {
                    String muscleGroup = p.getString("muscleGroup");
                    String day = p.getString("dayOfWeek");
                    boolean isPublic = p.getBoolean("public");
                    Workout w = new Workout(day, muscleGroup, workoutName, isPublic);
                    w.setId(p.getObjectId());
                    List<Exercise> exercises = new LinkedList<Exercise>();

                    List<ParseObject> exFromWorkout = p.getList("exercises");
                    ParseQuery exQuery = ParseQuery.getQuery("Exercise");

                    try {
                        List<ParseObject> parseEx = exQuery.find();
                        for (ParseObject parseObject : parseEx) {
                            for (ParseObject pEx : exFromWorkout) {
                                if (parseObject.hasSameId(pEx)) {
                                    String mg = parseObject.getString("muscleGroup");
                                    String youTube = parseObject.getString("linkToYouTube");
                                    String exName = parseObject.getString("exerciseName");
                                    Exercise ex = new Exercise(exName, mg, youTube);
                                    ex.setExId(parseObject.getObjectId());
                                    exercises.add(ex);
                                }
                            }
                        }
                        w.set_exercises(exercises);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    workouts.add(w);
                }
            }

            listener.onResult(workouts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void updateWorkout(final Workout workout) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workout");
        query.getInBackground(workout.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    parseObject.put("workoutName", workout.getWorkoutName());
                    parseObject.put("muscleGroup", workout.getMuscleGroup());
                    parseObject.put("dayOfWeek", workout.getDayOfWeek());
                    parseObject.put("public", workout.is_isPublic());
                    parseObject.saveInBackground();


                }
            }
        });
    }

    public void removeWorkout(String id) {
        ParseQuery query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject p : list) {
                        p.deleteInBackground();
                    }
                }
            }
        });

    }

    public void removeExerciseFromWorkout(final List<Exercise> exercises, String workoutId) {

        ParseQuery query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("objectId", workoutId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject p : list) {

                        List<ParseObject> exercisesList = p.getList("exercises");
                        if (exercisesList != null) {
                            for (ParseObject parseExercise : exercisesList) {
                                for (Exercise exercise : exercises) {
                                    if (exercise.getExId().equals(parseExercise.getObjectId())) {
                                        exercisesList.remove(parseExercise);
                                    }
                                }
                            }
                            p.put("exercises", exercisesList);
                            p.saveInBackground();
                        }
                    }
                }
            }
        });
    }

    public void addExercisesToWorkout(Workout workout) {

        final List<Exercise> exercises = workout.get_exercises();

        final List<ParseObject> allExerciseList = new LinkedList<ParseObject>();
        List<ParseObject> tmpList = new LinkedList<ParseObject>();
        ParseQuery exQuery = ParseQuery.getQuery("Exercise");
        try {
            tmpList = exQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(tmpList != null){
            for (ParseObject p : tmpList){
                allExerciseList.add(p);
            }
        }
//        exQuery.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> exListFromParse, ParseException e) {
//                if (e == null) {
//                    for (ParseObject ex: allExerciseList){
//                        allExerciseList.add(ex);
//                    }
//                }
//            }
//        });

        final ParseQuery query = ParseQuery.getQuery("Workout");
        query.whereEqualTo("objectId", workout.getId());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject p : list) {

                        List<ParseObject> exercisesList = p.getList("exercises");
                        if (allExerciseList != null) {
                            for (ParseObject parseExercise : allExerciseList) {
                                for (Exercise exercise : exercises) {
                                    if (exercise.getExId().equals(parseExercise.getObjectId())) {
                                        exercisesList.add(parseExercise);
                                    }
                                }
                            }
                            p.put("exercises", exercisesList);
                            p.saveInBackground();
                        }
                    }
                }
            }
        });
    }
}


//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    for (ParseObject p : list) {
//                        if (workoutName.equals(p.getString("workoutName"))) {
//                            String muscleGroup = p.getString("muscleGroup");
//                            String day = p.getString("dayOfWeek");
//                            boolean isPublic = p.getBoolean("public");
//                            Workout w = new Workout(day, workoutName, muscleGroup, isPublic);
//                            List<Exercise> exercises = new LinkedList<Exercise>();
//
//                            List<ParseObject> exFromWorkout = p.getList("exercises");
//                            ParseQuery exQuery = ParseQuery.getQuery("Exercise");
//
//                            try {
//                                List<ParseObject> parseEx = exQuery.find();
//                                for (ParseObject parseObject : parseEx) {
//                                    for (ParseObject pEx : exFromWorkout) {
//                                        if (p.hasSameId(pEx)) {
//                                            String mg = parseObject.getString("muscleGroup");
//                                            String youTube = parseObject.getString("linkToYouTube");
//                                            String exName = parseObject.getString("exerciseName");
//                                            Exercise ex = new Exercise(exName, mg, youTube);
//                                            exercises.add(ex);
//                                        }
//                                    }
//                                }
//                                w.set_exercises(exercises);
//                            } catch (ParseException e1) {
//                                e1.printStackTrace();
//                            }
//                            workouts.add(w);
//                        }
//                    }
//                }
//                listener.onResult(workouts);
//            }
//        });
