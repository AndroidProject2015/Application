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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;


public class NewWorkoutActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        EditText workoutName = (EditText) findViewById(R.id.workoutName);
        EditText muscleGroup = (EditText) findViewById(R.id.muscleGroup);
        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        final Workout workout = new Workout("1",muscleGroup.getText().toString(), workoutName.getText().toString());

        workout.setDayOfWeek("1");
        workout.setMuscleGroup(muscleGroup.getText().toString());
        workout.setWorkoutName(workoutName.getText().toString());
//        final ExFragment fragment = new ExFragment();
//        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.hide(fragment);
//        fragmentTransaction.commit();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ParseModel.getInstance().addWorkoutToUser(workout);
//                fragmentTransaction.show(fragment);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dow, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        spinner.setOnClickListener();
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
