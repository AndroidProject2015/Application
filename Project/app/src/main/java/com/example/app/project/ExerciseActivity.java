package com.example.app.project;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.app.project.model.Exercise;
import com.example.app.project.model.ParseModel;
import com.example.app.project.model.Workout;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ExerciseActivity extends ActionBarActivity {

    ListView exerciseList;
    public List<Exercise> exerciseData = new LinkedList<Exercise>();
    ProgressBar progressBar;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        progressBar = (ProgressBar) findViewById(R.id.exProgressBar);
        exerciseList = (ListView) findViewById(R.id.exerciseList);
        adapter = new CustomAdapter();
        exerciseList.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);

        ParseModel.getInstance().getAllExercises(new ParseModel.GetExerciseListener() {
            @Override
            public void onResult(List<Exercise> e) {
                progressBar.setVisibility(View.GONE);
                exerciseData = e;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
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
            return exerciseData.size();
        }

        @Override
        public Object getItem(int i) {
            return exerciseData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.row_ex_layout, null);

            }

            TextView exName = (TextView) view.findViewById(R.id.exName);
            TextView muscleGroup = (TextView) view.findViewById(R.id.muscleGroup);
            TextView linkToYouTube = (TextView) view.findViewById(R.id.linkToYoutube);
            linkToYouTube.setMovementMethod(LinkMovementMethod.getInstance());
            Exercise ex = exerciseData.get(i);
            exName.setText(ex.getExerciseName());
            muscleGroup.setText(ex.getMuscleGroup());
            linkToYouTube.setText(ex.getLinkToYouTube());
            return view;
        }
    }
}
