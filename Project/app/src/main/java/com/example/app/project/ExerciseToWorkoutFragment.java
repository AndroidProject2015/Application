package com.example.app.project;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseToWorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseToWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseToWorkoutFragment extends Fragment {


    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static ExerciseToWorkoutFragment newInstance(String param1, String param2) {
        ExerciseToWorkoutFragment fragment = new ExerciseToWorkoutFragment();
        return fragment;
    }

    public ExerciseToWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_to_workout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
