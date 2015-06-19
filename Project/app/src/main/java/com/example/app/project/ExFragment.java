package com.example.app.project;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.Workout;

import java.util.List;

/**
 * Created by Pavel on 19/06/2015.
 */
public class ExFragment extends ListFragment {

    CustomAdapter adapter;
    List<Exercise> exData;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return exData.size();
        }

        @Override
        public Object getItem(int i) {
            return exData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.row_layout, null);

            }

            TextView link2You = (TextView) view.findViewById(R.id.linkToYoutube);
            TextView name = (TextView) view.findViewById(R.id.exName);
            TextView muscle = (TextView) view.findViewById(R.id.muscleGroup);
            Exercise exercise = exData.get(i);
            day.setText(exercise.get());
            name.setText(exercise.getWorkoutName());
            muscle.setText(exercise.getMuscleGroup());
            return view;
        }
    }
}
