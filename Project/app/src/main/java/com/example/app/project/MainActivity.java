package com.example.app.project;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.project.model.ParseModel;
import com.example.app.project.model.User;
import com.example.app.project.model.Workout;
import com.parse.ParseUser;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    static final int BACK_FROM_NEW_USER_ACTIVITY = 1;
    ListView workoutList;
    public List<Workout> workoutData = new LinkedList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseModel.getInstance().init(getApplicationContext());
        workoutList = (ListView)findViewById(R.id.workoutList);
        workoutData = ParseModel.getInstance().getUserWorkouts();
        CustomAdapter adapter = new CustomAdapter();
        workoutList.setAdapter(adapter);


        ImageButton addWorkout = (ImageButton)findViewById(R.id.addWorkout);
        ImageButton workouts = (ImageButton)findViewById(R.id.workouts);
        ImageButton exerciseList = (ImageButton)findViewById(R.id.exercises);
        ImageButton userPhys = (ImageButton)findViewById(R.id.userPhys);

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newWorkoutIntent = new Intent(getApplicationContext(), NewWorkoutActivity.class);
                startActivity(newWorkoutIntent);
            }
        });
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutsIntent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(workoutsIntent);
            }
        });
        exerciseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(exerciseIntent);
            }
        });

        userPhys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(userIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            if(view == null){
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.row_layout,null);

            }

            TextView day = (TextView)view.findViewById(R.id.dayOfWeek);
            TextView name = (TextView)view.findViewById(R.id.workoutName);
            TextView muscle = (TextView)view.findViewById(R.id.muscleGroup);
            Workout workout = workoutData.get(i);
            day.setText(workout.getDayOfWeek());
            name.setText(workout.getWorkoutName());
            muscle.setText(workout.getMuscleGroup());
            return null;
        }
    }

    public void WorkOutsBtn(View v)
    {
        Intent wo = new Intent(this,WorkoutActivity.class);
        startActivity(wo);
    }
}
