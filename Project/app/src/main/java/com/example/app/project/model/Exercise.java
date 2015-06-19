package com.example.app.project.model;

/**
 * Created by Pavel on 07/06/2015.
 */
public class Exercise {

    String exerciseName;
    String muscleGroup;
    String linkToYouTube;

    public Exercise(String exerciseName, String muscleGroup, String linkToYouTube) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.linkToYouTube = linkToYouTube;
    }

    public String getLinkToYouTube() {
        return linkToYouTube;
    }

    public void setLinkToYouTube(String linkToYouTube) {
        this.linkToYouTube = linkToYouTube;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exeiricseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

}
