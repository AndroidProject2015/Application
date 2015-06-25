package com.example.app.project;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavel on 19/06/2015.
 */
public class ExFragment extends ListFragment {

    CustomAdapter adapter;
    List<Exercise> exData = new LinkedList<Exercise>();
    Context context;
    ListView exListView;
    List<Exercise> exercises = new LinkedList<Exercise>();
    boolean newWorkout = false;


    public interface Listener {
        public void onFinish(List<Exercise> exercises);
        public void onEdit();
        public void onCancel();
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomAdapter();
        setListAdapter(adapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        exercises.add(exData.get(position));
        l.getChildAt(position).setBackgroundColor(Color.GRAY);
        Log.i("FragmentList", "Item clicked: " + id);

    }

    public void showExercise(final Workout workout, String option) {
        switch (option) {
            case "workoutExercises":
//                ParseModel.getInstance().getWorkoutExercises(workout, new ParseModel.GetExerciseListener() {
//                    @Override
//                    public void onResult(List<Exercise> e) {
                exData = workout.get_exercises();

//                    }
//                });
                break;
            case "allExercises":
                newWorkout = true;
                ParseModel.getInstance().getAllExercises(new ParseModel.GetExerciseListener() {
                    @Override
                    public void onResult(List<Exercise> e) {
                        exData = e;
                    }
                });
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        Button finishBtn = (Button) view.findViewById(R.id.finishBtn);
//        Button editBtn = (Button) view.findViewById(R.id.editBtn);
//        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

        if (newWorkout) {
//            editBtn.setVisibility(View.GONE);
//            cancelBtn.setVisibility(View.GONE);

            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addExercise(view);
                }
            });
        }
        else{
            finishBtn.setVisibility(View.GONE);
//            editBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            cancelBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

        }
        return view;
    }

    private void addExercise(View view) {
        listener.onFinish(exercises);
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_ex_layout, null);

            }

            TextView link2You = (TextView) view.findViewById(R.id.linkToYoutube);
            TextView name = (TextView) view.findViewById(R.id.exName);
            TextView muscle = (TextView) view.findViewById(R.id.muscleGroup);
            Exercise exercise = exData.get(i);
            link2You.setText(exercise.getLinkToYouTube());
            name.setText(exercise.getExerciseName());
            muscle.setText(exercise.getMuscleGroup());
            return view;
        }
    }
}
