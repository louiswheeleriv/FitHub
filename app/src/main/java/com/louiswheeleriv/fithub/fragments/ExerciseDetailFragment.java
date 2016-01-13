package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.*;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import android.widget.TextView;

public class ExerciseDetailFragment extends Fragment {

    private static final String ARG_EXERCISE_ID = "exerciseId";
    private int exerciseId;
    private Exercise exercise;

    private DatabaseHandler db;

    private OnFragmentInteractionListener mListener;

    public static ExerciseDetailFragment newInstance() {
        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        return fragment;
    }

    public ExerciseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseId = Integer.parseInt(getArguments().getString(ARG_EXERCISE_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_detail, container, false);

        db = new DatabaseHandler(getActivity());

        // Populate the Exercise object using the exerciseId parameter
        exercise = db.getExercise(exerciseId);

        // Place the exercise name at the top
        TextView textViewExerciseName = (TextView) rootView.findViewById(R.id.textview_exercise_name);
        textViewExerciseName.setText(exercise.getName());

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
