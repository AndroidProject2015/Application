package com.example.app.project;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
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
    List<Exercise> exercises;


    public interface Listener{
        public void onFinish(List<Exercise> exercises);
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
        ParseModel.getInstance().getAllExercises(new ParseModel.GetExerciseListener() {
            @Override
            public void onResult(List<Exercise> e) {
                exData = e;
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.i("FragmentList", "Item clicked: " + id);
    }

    public void showExerecise(String w){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        Button finishBtn = (Button)view.findViewById(R.id.finishBtn);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise(view);
            }
        });
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
