package com.louiswheeleriv.fithub;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.List;
import android.graphics.Typeface;



public class WorkoutFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    private DatabaseHandler db;

    public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        return fragment;
    }

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflatedView = inflater.inflate(R.layout.fragment_workout, container, false);

        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(inflatedView.findViewById(R.id.button_create_exercise), iconFont);

        // Populate listview with exercises
        db = new DatabaseHandler(getActivity());
        List<Exercise> allExercises = db.getAllExercises();
        ArrayAdapter<Exercise> allExercisesAdapter = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_1, allExercises);
        setListAdapter(allExercisesAdapter);

        // Create exercise button listener
        Button createExerciseButton = (Button) inflatedView.findViewById(R.id.button_create_exercise);
        createExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CreateExerciseFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("fragment").commit();
            }
        });

        // Return the inflated view
        return inflatedView;
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
