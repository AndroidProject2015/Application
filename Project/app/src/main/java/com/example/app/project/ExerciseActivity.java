package com.example.app.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.project.model.Exercise;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ExerciseActivity extends ActionBarActivity {

    ListView exerciseListView;
    //public List<Exercise> exerciseData = new LinkedList<Exercise>();
    //ProgressBar progressBar;
    ProgressDialog mProgressDialog;
    CustomAdapter adapter;

    private List<Exercise> exerciseList = null;
    private int limit = 10;
    private int total = 0;
    List<ParseObject> ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //Getting all exercises at once
        //progressBar = (ProgressBar) findViewById(R.id.exProgressBar);
//        exerciseListView = (ListView) findViewById(R.id.exerciseList);
//        adapter = new CustomAdapter(ExerciseActivity.this, exerciselist);
//        exerciseListView.setAdapter(adapter);
        //progressBar.setVisibility(View.VISIBLE);
//        ParseModel.getInstance().getAllExercises(new ParseModel.GetExerciseListener() {
//            @Override
//            public void onResult(List<Exercise> e) {
//                //progressBar.setVisibility(View.GONE);
//                exerciseData = e;
//                adapter.notifyDataSetChanged();
//            }
//        });

        //Getting exercises incrementally with spinner
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
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
        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<Exercise> exerciselist = null;
        private ArrayList<Exercise> arraylist;
        protected int count;

        public CustomAdapter(Context context, List<Exercise> exerciselist) {
            mContext = context;
            this.exerciselist = exerciselist;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Exercise>();
            this.arraylist.addAll(exerciselist);
        }

        @Override
        public int getCount() {
            return exerciselist.size();
        }

        @Override
        public Object getItem(int i) {
            return exerciselist.get(i);
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
            Exercise ex = exerciselist.get(i);
            exName.setText(ex.getExerciseName());
            muscleGroup.setText(ex.getMuscleGroup());
            linkToYouTube.setText(ex.getLinkToYouTube());
            return view;
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ExerciseActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("BioGYM");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            exerciseList = new ArrayList<Exercise>();
            try {
                // Locate the class table named "Exercise" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Exercise");
                query.orderByAscending("createdAt");
                // Set the limit of objects to show
                query.setLimit(limit);
                ob = query.find();
                for (ParseObject exercise : ob) {
                    String exerciseName = exercise.getString("exerciseName");
                    String muscleGroup = exercise.getString("muscleGroup");
                    String linkToYouTube = exercise.getString("linkToYouTube");
                    Exercise ex = new Exercise(exerciseName, muscleGroup, linkToYouTube);
                    exerciseList.add(ex);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the ListView in exerciseListView
            exerciseListView = (ListView) findViewById(R.id.exerciseList);
            // Pass the results into CustomAdapter
            adapter = new CustomAdapter(ExerciseActivity.this, exerciseList);
            // Binds the Adapter to the exerciseListView
            exerciseListView.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Create an OnScrollListener
            exerciseListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view,
                                                 int scrollState) { // TODO Auto-generated method stub
                    int threshold = 1;
                    int count = exerciseListView.getCount();

                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (exerciseListView.getLastVisiblePosition() >= count
                                - threshold) {
                            // Execute LoadMoreDataTask AsyncTask
                            new LoadMoreDataTask().execute();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub

                }

            });
        }

        private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(ExerciseActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("BioGYM");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading more exercises...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Create the array
                exerciseList = new ArrayList<Exercise>();
                try {
                    // Locate the class table named "Exercise" in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "Exercise");
                    query.orderByAscending("createdAt");
                    // Add 10 results to the default limit
                    query.setLimit(limit += 10);
                    ob = query.find();
                    if (ob.size() != 0) {
                        for (ParseObject exercise : ob) {
                            String exerciseName = exercise.getString("exerciseName");
                            String muscleGroup = exercise.getString("muscleGroup");
                            String linkToYouTube = exercise.getString("linkToYouTube");
                            Exercise ex = new Exercise(exerciseName, muscleGroup, linkToYouTube);
                            exerciseList.add(ex);
                        }
                    }
                } catch (ParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Locate exerciseListView last item
                int position = exerciseListView.getLastVisiblePosition();
                // Pass the results into CustomAdapter
                adapter = new CustomAdapter(ExerciseActivity.this, exerciseList);
                // Binds the Adapter to the exerciseListView
                exerciseListView.setAdapter(adapter);
                // Show the latest retrieved results on the top
                exerciseListView.setSelectionFromTop(position, 0);
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }

    }
}
