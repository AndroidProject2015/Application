package com.example.app.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
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
        TextView weight = (TextView) v.findViewById(R.id.weight);

        try {

            byte[] bytes = user.getParseFile("profilePicture").getData();
            Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView pic = (ImageView) v.findViewById(R.id.userPic);
            pic.setImageDrawable( new BitmapDrawable(getResources(),b));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        name.setText(user.getString("name"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//       try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        ParseUser user = ParseUser.getCurrentUser();
        TextView name = (TextView) getView().findViewById(R.id.name);
        TextView height = (TextView) getView().findViewById(R.id.height);
        TextView bmi = (TextView) getView().findViewById(R.id.bmi);
        TextView weight = (TextView) getView().findViewById(R.id.weight);

        name.setText(user.getString("name"));
        height.setText(user.getNumber("height").toString());
        weight.setText(user.getNumber("weight").toString());
        bmi.setText(user.getNumber("bmi").toString());
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
