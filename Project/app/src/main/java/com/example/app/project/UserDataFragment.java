package com.example.app.project;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.zip.Inflater;

public class UserDataFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public static UserDataFragment newInstance(String param1, String param2) {
        UserDataFragment fragment = new UserDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UserDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_user_data, container, false);
        ParseUser user = ParseUser.getCurrentUser();
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView height = (TextView) v.findViewById(R.id.height);
        TextView bmi = (TextView) v.findViewById(R.id.bmi);

        //String n = user.getString("name");
        //String h = user.getNumber("height").toString();





        name.setText(user.getString("name"));
        height.setText(user.getNumber("height").toString());
        bmi.setText(user.getNumber("bmi").toString());

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//       try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
