package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import java.util.Date;

public class ExerciseDetailFragment extends Fragment {

    private static final String ARG_EXERCISE_ID = "exerciseId";
    private static final String ARG_DATE_SELECTED = "dateSelected";

    private int exerciseId;
    private Exercise exercise;

    private Date dateSelected;

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
            exerciseId = getArguments().getInt(ARG_EXERCISE_ID);
            dateSelected = (Date) getArguments().getSerializable(ARG_DATE_SELECTED);

            if (dateSelected == null) {
                dateSelected = new Date();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_detail, container, false);

        db = new DatabaseHandler(getActivity());

        // Populate the Exercise object using the exerciseId parameter
        exercise = db.getExercise(exerciseId);

        // Display the selected exercise name
        TextView textViewExerciseName = (TextView) rootView.findViewById(R.id.textview_exercise_name);
        textViewExerciseName.setText(exercise.getName());

        // Display the selected date on date selection button
        Button dateSelectionButton = (Button) rootView.findViewById(R.id.button_select_date);

        String formattedDate = (getShortMonthName(dateSelected.getMonth()) + " " + dateSelected.getDate() + ", " + (dateSelected.getYear()+1900));
        dateSelectionButton.setText(formattedDate);

        // Handle date selection button click
        dateSelectionButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment fragment = new DatePickerFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(ARG_EXERCISE_ID, exerciseId);
                bundle.putSerializable(ARG_DATE_SELECTED, dateSelected);
                fragment.setArguments(bundle);

                fragment.show(fm, "fragment_date_picker");
            }
        });

        return rootView;
    }

    private String getShortMonthName(int monthNum) {
        switch(monthNum) {
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "Jul";
            case 7: return "Aug";
            case 8: return "Sep";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
        }

        return "OH DIP";
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
