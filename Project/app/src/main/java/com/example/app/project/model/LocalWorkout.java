package com.example.app.project.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Tom on 6/22/2015.
 */

@ParseClassName("workout")
public class LocalWorkout extends ParseObject {

    public String getDayOfWeek()
    {
        return getString("dayOfWeek");
    }

    public void setDayOfWeek(String dow)
    {
        put("dayOfWeek",dow);
    }


    public String getMuscleGroup()
    {
        return getString("muscleGroup");
    }

    public void setMuscleGroup(String muscleGroup)
    {
        put("muscleGroup",muscleGroup);
    }

    public boolean getPublic()
    {
        return getBoolean("public");
    }

    public void setPublic(boolean p)
    {
        put("public",p);
    }

    public ParseUser getUser()
    {
        return getParseUser("users");
    }

    public  void setUser(ParseUser user)
    {
        put("users",user);
    }

    public String getWorkoutName()
    {
        return getString("workoutName");
    }

    public void setWorkoutName(String workoutName)
    {
        put("workoutName",workoutName);
    }

    public List<ParseUser> getWorkoutUsers()
    {
        return getList("workoutUsers");
    }

    public void setWorkoutUsers(List<ParseUser> workoutUsers)
    {
        put("workoutUsers",workoutUsers);
    }

    public static ParseQuery<LocalWorkout> getQuery() {
        return ParseQuery.getQuery(LocalWorkout.class);
    }







}
