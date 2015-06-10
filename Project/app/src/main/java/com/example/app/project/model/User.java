package com.example.app.project.model;

import java.util.List;

/**
 * Created by Pavel on 07/06/2015.
 */
public class User {

    String userName;
    String password;
    List<Workout> workouts;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
