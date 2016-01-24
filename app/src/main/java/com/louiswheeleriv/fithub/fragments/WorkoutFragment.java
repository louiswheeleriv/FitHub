package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;
import com.louiswheeleriv.fithub.util.FontManager;

import java.util.Date;
import java.util.List;

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
        final View rootView = inflater.inflate(R.layout.fragment_workout, container, false);

        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.button_create_exercise), iconFont);

        // Populate listview with exercises
        db = new DatabaseHandler(getActivity());
        List<Exercise> allExercises = db.getAllExercises();
        ArrayAdapter<Exercise> allExercisesAdapter = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_1, allExercises);
        setListAdapter(allExercisesAdapter);

        // Create exercise button listener
        Button createExerciseButton = (Button) rootView.findViewById(R.id.button_create_exercise);
        createExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CreateExerciseFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("fragment").commit();
            }
        });

        // Return the inflated view
        return rootView;
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        Fragment detailFragment = new ExerciseDetailFragment();
        Bundle bundle = new Bundle();

        Exercise selectedExercise = (Exercise) listView.getItemAtPosition(position);
        bundle.putInt("exerciseId", selectedExercise.getId());
        bundle.putSerializable("dateSelected", new Date());
        detailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, detailFragment).addToBackStack("fragment").commit();
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
