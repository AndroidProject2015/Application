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
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.LinkedList;
import java.util.List;


public class ExFragment extends ListFragment {

    CustomAdapter adapter;
    List<Exercise> exData = new LinkedList<Exercise>();
    Context context;
    ListView exListView;
    List<Exercise> exercises = new LinkedList<Exercise>();
    boolean newWorkout = false;
    boolean editExWorkout = false;
    boolean addPressed = false;
    boolean workoutExList = false;

//    public void setFlagForEditExList(boolean f) {
//        editExWorkout = f;
//    }

    public interface Listener {
        void onFinish(List<Exercise> exercises);

        void onEdit();

        void onCancel();

        void onRemove(List<Exercise> exercises);

        void addExercises(List<Exercise> exercises);
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
        if (!exercises.contains(exData.get(position))) {
            exercises.add(exData.get(position));
            l.getChildAt(position).setBackgroundColor(Color.GRAY);
        } else {
            exercises.remove(exData.get(position));
            l.getChildAt(position).setBackgroundColor(Color.GREEN);

        }

        Log.i("FragmentList", "Item clicked: " + id);

    }

    public void showExercise(final Workout workout, String option) {
        switch (option) {
            case "workoutExercises":
                workoutExList = true;
                exData = workout.get_exercises();
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
            case "add":
                addPressed = true;
                ParseModel.getInstance().getAllExercises(new ParseModel.GetExerciseListener() {
                    @Override
                    public void onResult(List<Exercise> e) {
                        exData = e;
                    }
                });
                break;
            case "edit":
                editExWorkout = true;
                exData = workout.get_exercises();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        //init all buttons
        Button finishBtn = (Button) view.findViewById(R.id.finishBtn);
        Button editBtn = (Button) view.findViewById(R.id.editBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        Button removeBtn = (Button) view.findViewById(R.id.removeBtn);
        Button addBtn = (Button) view.findViewById(R.id.addBtn);

        //remove all buttons from View
        addBtn.setVisibility(View.GONE);
        removeBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
        finishBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);

        //Button Listeners
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise(view);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment(view);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWorkoutExercises(view);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseToWorkout(view);
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeExercises(view);
            }
        });


        if (newWorkout) {
            cancelBtn.setVisibility(View.VISIBLE);
            finishBtn.setVisibility(View.VISIBLE);
        }

        if (workoutExList) {
            editBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);
        }

        if (editExWorkout) {
            removeBtn.setVisibility(View.VISIBLE);
            addBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);
        }

        if (addPressed) {
            finishBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);

        }

        return view;
    }

    private void addExerciseToWorkout(View view) {
        listener.addExercises(exercises);
    }

    private void removeExercises(View view) {
        listener.onRemove(exercises);
    }

    private void closeFragment(View view) {
        listener.onCancel();
    }

    private void editWorkoutExercises(View view) {
        listener.onEdit();
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
