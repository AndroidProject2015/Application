package com.example.app.project;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;
import com.parse.ParseUser;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    static final int BACK_FROM_NEW_USER_ACTIVITY = 1;
    ListView workoutList;
    public List<Workout> workoutData = new LinkedList<Workout>();
    ProgressBar progressBar;
    CustomAdapter adapter;
    ExFragment exFragment;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        workoutList = (ListView) findViewById(R.id.workoutList);
        adapter = new CustomAdapter();
        workoutList.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);


        ParseModel.getInstance().getUserWorkouts(new ParseModel.GetWorkoutsListener() {
            @Override
            public void onResult(List<Workout> w) {
                progressBar.setVisibility(View.GONE);
                workoutData = w;
                adapter.notifyDataSetChanged();
            }
        });

        final Button removeWorkout = (Button) findViewById(R.id.removeWorkout);
        ImageButton addWorkoutBtn = (ImageButton) findViewById(R.id.addWorkout);
        ImageButton workoutsBtn = (ImageButton) findViewById(R.id.workouts);
        final ImageButton exerciseListBtn = (ImageButton) findViewById(R.id.exercises);
        ImageButton userPhysBtn = (ImageButton) findViewById(R.id.userPhys);
        removeWorkout.setVisibility(View.GONE);
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newWorkoutIntent = new Intent(getApplicationContext(), NewWorkoutActivity.class);
                startActivity(newWorkoutIntent);
            }
        });
        workoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutsIntent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(workoutsIntent);
            }
        });
        exerciseListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(exerciseIntent);
            }
        });

        userPhysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(getApplicationContext(), UserDataActivity.class);
                startActivity(userIntent);
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.mainFragmentContainer);
        linearLayout.setVisibility(LinearLayout.GONE);
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                linearLayout.setVisibility(LinearLayout.VISIBLE);
                removeWorkout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "item click " + i, Toast.LENGTH_LONG).show();
                exFragment = new ExFragment();
                exFragment.showExercise(workoutData.get(i), "workoutExercises");
                exFragment.setListener(new ExFragment.Listener() {
                    @Override
                    public void onFinish(List<Exercise> exercises) {

                    }

                    @Override
                    public void onEdit() {
                        Intent intent = new Intent(getApplication(), EditWorkoutActivity.class);
                        intent.putExtra("workout", workoutData.get(i).getWorkoutName());
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.hide(exFragment);
                        ft.commit();
                        linearLayout.setVisibility(LinearLayout.GONE);
                    }

                    @Override
                    public void onRemove(List<Exercise> exercises) {

                    }

                    @Override
                    public void addExercises(List<Exercise> exercises) {

                    }
                });
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.mainFragmentContainer, exFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exFragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(exFragment);
            ft.commit();
            exFragment = null;
            linearLayout.setVisibility(LinearLayout.GONE);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.logout) {
            ParseUser.logOutInBackground();
            Intent loginAvtyvity = new Intent(this, LoginActivity.class);
            startActivity(loginAvtyvity);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);


        ParseModel.getInstance().getUserWorkouts(new ParseModel.GetWorkoutsListener() {
            @Override
            public void onResult(List<Workout> w) {
                progressBar.setVisibility(View.GONE);
                workoutData = w;
                adapter.notifyDataSetChanged();
            }
        });
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
                view = inflater.inflate(R.layout.row_layout, null);

            }

            TextView day = (TextView) view.findViewById(R.id.dayOfWeek);
            TextView name = (TextView) view.findViewById(R.id.workoutName);
            TextView muscle = (TextView) view.findViewById(R.id.muscleGroup);
            Workout workout = workoutData.get(i);
            day.setText(workout.getDayOfWeek());
            name.setText(workout.getWorkoutName());
            muscle.setText(workout.getMuscleGroup());
            return view;
        }
    }

    public void WorkOutsBtn(View v) {
        Intent wo = new Intent(this, WorkoutActivity.class);
        startActivity(wo);
    }


}
