package com.example.app.project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.LinkedList;
import java.util.List;


public class WorkoutActivity extends ActionBarActivity {

    ListView workoutList;
    public List<Workout> workoutData = new LinkedList<Workout>();
    ProgressBar progressBar;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);



        progressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        workoutList = (ListView) findViewById(R.id.execiseList);
        adapter = new CustomAdapter();
        workoutList.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);


        ParseModel.getInstance().getGlobalWorkOut(new ParseModel.GetWorkoutsListener() {
            @Override
            public void onResult(List<Workout> w) {
                progressBar.setVisibility(View.GONE);
                workoutData = w;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return workoutData.size();
        }

        @Override
        public Object getItem(int i) {
            return workoutData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.workout_layout, null);

            }

            TextView day = (TextView) view.findViewById(R.id.dayOfWeek);
            TextView name = (TextView) view.findViewById(R.id.workoutName);
            TextView muscle = (TextView) view.findViewById(R.id.muscleGroup);
            final Workout workout = workoutData.get(i);

            ImageButton add = (ImageButton) view.findViewById(R.id.addWorkout);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseModel.getInstance().addUserToWorkout(workout.getParseWorkout());
                    (view.findViewById(R.id.addWorkout)).setVisibility(View.INVISIBLE);

                }
            });

            day.setText(workout.getDayOfWeek());
            name.setText(workout.getWorkoutName());
            muscle.setText(workout.getMuscleGroup());
            return view;
        }
    }

    public void searchBtn(View v)
    {
        progressBar.setVisibility(View.VISIBLE);

        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String wName = ((EditText)findViewById(R.id.wName)).getText().toString();
        String mGroup = ((EditText)findViewById(R.id.mGroup)).getText().toString();

        ParseModel.getInstance().getSearchWorkOut(new String[]{wName, email, mGroup},new ParseModel.GetWorkoutsListener() {
            @Override
            public void onResult(List<Workout> w) {
                progressBar.setVisibility(View.GONE);
                workoutData = w;
                adapter.notifyDataSetChanged();
            }
        });



    }
}
