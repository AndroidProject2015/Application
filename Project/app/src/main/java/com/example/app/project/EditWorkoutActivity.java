package com.example.app.project;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.List;


public class EditWorkoutActivity extends ActionBarActivity {

    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    ExFragment fragment;
    ExFragment frag;
    String day;
    String mGroup;
    String name;
    boolean isPublic;
    List<Exercise> _exercises;
    Spinner spinner;
    List<Workout> workouts;
    Workout workout;
    Button nextBtn;
    Button cancelBtn;
    boolean editExerciseListFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        Intent intent = getIntent();
        String w = intent.getExtras().getString("workout");
        ParseModel.getInstance().getWorkoutByName(w, new ParseModel.GetWorkoutsListener() {
            @Override
            public void onResult(List<Workout> w) {
                workout = w.get(0);
            }
        });

        fragment = new ExFragment();
        fragment.showExercise(workout, "edit");
//        fragment.setFlagForEditExList(true);

        frag = new ExFragment();
        frag.showExercise(workout, "add");

        nextBtn = (Button) findViewById(R.id.nextBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        CheckBox isPublicBox = (CheckBox) findViewById(R.id.isPublicBox);
        final EditText workoutName = (EditText) findViewById(R.id.workoutName);
        final EditText muscleGroup = (EditText) findViewById(R.id.muscleGroup);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dow, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        workoutName.setText(workout.getWorkoutName());
        muscleGroup.setText(workout.getMuscleGroup());
        isPublicBox.setChecked(workout.is_isPublic());
        int pos = Integer.parseInt(workout.getDayOfWeek()) - 1;
        spinner.setSelection(pos);

        isPublicBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPublic = ((CheckBox) view).isChecked();
            }
        });

        linearLayout2 = (LinearLayout) findViewById(R.id.exerciseFragmentContainer2);
        linearLayout = (LinearLayout) findViewById(R.id.exerciseFragmentContainer);
        linearLayout.setVisibility(LinearLayout.GONE);
        linearLayout2.setVisibility(LinearLayout.GONE);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(LinearLayout.VISIBLE);
                editExerciseListFlag = true;
                day = spinner.getSelectedItem().toString();
                mGroup = muscleGroup.getText().toString();
                name = workoutName.getText().toString();
                Workout w = new Workout(day, mGroup, name, isPublic);
                w.setId(workout.getId());
                ParseModel.getInstance().updateWorkout(w);
                if (!fragment.isHidden()) {
                    ft.add(R.id.exerciseFragmentContainer, fragment);
                } else {
                    ft.show(fragment);
                }
                ft.commit();

                nextBtn.setVisibility(view.INVISIBLE);
                cancelBtn.setVisibility(view.INVISIBLE);
            }
        });

        fragment.setListener(new ExFragment.Listener() {
            @Override
            public void onFinish(List<Exercise> exercises) {
                workout.set_exercises(exercises);
            }

            @Override
            public void onEdit() {

            }

            @Override
            public void onCancel() {
                linearLayout.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.GONE);
                nextBtn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.VISIBLE);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(fragment);
                ft.commit();

            }

            @Override
            public void onRemove(List<Exercise> exercises) {
                linearLayout.setVisibility(LinearLayout.GONE);
                nextBtn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(fragment);
                ft.commit();
                ParseModel.getInstance().removeExerciseFromWorkout(exercises, workout.getId());
                Intent intent1 = new Intent(getApplication(), MainActivity.class);
                startActivity(intent1);

            }

            @Override
            public void addExercises(List<Exercise> exercises) {
                linearLayout.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(fragment);
                ft.add(R.id.exerciseFragmentContainer2, frag);
                ft.commit();

            }
        });


        frag.setListener(new ExFragment.Listener() {
            @Override
            public void onFinish(List<Exercise> exercises) {
                workout.set_exercises(exercises);

                ParseModel.getInstance().addExercisesToWorkout(workout);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onEdit() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onRemove(List<Exercise> exercises) {

            }

            @Override
            public void addExercises(List<Exercise> exercises) {

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
            ft.commit();
            fragment = null;
            nextBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(LinearLayout.INVISIBLE);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_workout, menu);
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
}
