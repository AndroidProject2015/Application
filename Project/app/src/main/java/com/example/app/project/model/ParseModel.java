package com.example.app.project.model;

import android.content.Context;

import com.parse.Parse;

/**
 * Created by Pavel on 07/06/2015.
 */
public class ParseModel {

    private final static ParseModel instance = new ParseModel();

    public static ParseModel getInstance(){
        return instance;
    }

    public void init(Context context){
        Parse.initialize(context,"Y3IoszVq3My4l97JfvWeonOfaAcqmwDAWmPopEWT", "jyvKepSR1A6BkZX21GsITJAEi6fnoCCUC3vSCg3F");
    }
}
