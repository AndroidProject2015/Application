package com.example.app.project;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.List;


public class NewWorkoutActivity extends ActionBarActivity {

    ExFragment fragment;
    String day;
    String mGroup;
    String name;
    boolean isPublic;
    List<Exercise> _exercises;
    Spinner spinner;
    Workout workout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        fragment = new ExFragment();
        fragment.showExercise(workout, "allExercises");
        final EditText workoutName = (EditText) findViewById(R.id.workoutName);
        final EditText muscleGroup = (EditText) findViewById(R.id.muscleGroup);
        final Button nextBtn = (Button) findViewById(R.id.nextBtn);
        final Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        CheckBox isPublicBox = (CheckBox) findViewById(R.id.isPublicBox);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dow, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        isPublicBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPublic = ((CheckBox)view).isChecked();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = spinner.getSelectedItem().toString();
                mGroup = muscleGroup.getText().toString();
                name = workoutName.getText().toString();
                workout = new Workout(day, mGroup, name, isPublic);
                ParseModel.getInstance().createWorkout(workout);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.exerciseFragmentContainer, fragment);
                ft.commit();
                nextBtn.setVisibility(view.INVISIBLE);
                cancelBtn.setVisibility(view.INVISIBLE);
            }
        });

        fragment.setListener(new ExFragment.Listener() {
            @Override
            public void onFinish(List<Exercise> exercises) {
                workout.set_exercises(exercises);
                ParseModel.getInstance().setExerciseToWorkout(workout);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onEdit() {

            }

            @Override
            public void onCancel() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(fragment);
                ft.commit();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(fragment);
//            ft.addToBackStack(null);
            ft.commit();
            fragment = null;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_workout, menu);
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

    public class SpinnerActivity extends Activity implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }
}
