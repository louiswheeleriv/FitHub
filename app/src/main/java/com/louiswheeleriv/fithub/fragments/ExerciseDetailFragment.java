package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.util.*;

import java.util.Date;
import java.util.List;

import com.louiswheeleriv.fithub.objects.*;

public class ExerciseDetailFragment extends ListFragment {

    private String ARG_EXERCISE_ID;
    private String ARG_DATE_SELECTED;

    private int exerciseId;
    private Exercise exercise;
    private String exerciseType;

    private List<WeightExercise> weightExerciseList;
    private List<CardioExercise> cardioExerciseList;
    private List<BodyExercise> bodyExerciseList;

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

        ARG_EXERCISE_ID = getResources().getString(R.string.arg_exercise_id);
        ARG_DATE_SELECTED = getResources().getString(R.string.arg_date_selected);

        // Get exercise type id and date selected
        if (getArguments() != null) {
            exerciseId = getArguments().getInt(ARG_EXERCISE_ID);
            dateSelected = (Date) getArguments().getSerializable(ARG_DATE_SELECTED);
        }

        // If dateSelected not provided, default to today
        if (dateSelected == null) {
            dateSelected = new Date();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_detail, container, false);

        db = new DatabaseHandler(getActivity());

        // Populate the Exercise object using the exerciseId parameter
        exercise = db.getExercise(exerciseId);

        Resources resources = getResources();
        final String weightType = resources.getString(R.string.const_weight_exercise);
        final String cardioType = resources.getString(R.string.const_cardio_exercise);
        final String bodyType = resources.getString(R.string.const_body_exercise);

        boolean hasData = false;

        // Get the list of existing ExerciseInstance objects for this type/date
        if (exercise.getExerciseType().equals(weightType)) {

            weightExerciseList = db.getWeightExercisesByExerciseIdDate(exerciseId, dateSelected);
            exerciseType = weightType;
            hasData = (!weightExerciseList.isEmpty());

        } else if (exercise.getExerciseType().equals(cardioType)) {

            cardioExerciseList = db.getCardioExercisesByExerciseIdDate(exerciseId, dateSelected);
            exerciseType = cardioType;
            hasData = (!cardioExerciseList.isEmpty());

        } else if (exercise.getExerciseType().equals(bodyType)) {

            bodyExerciseList = db.getBodyExercisesByExerciseIdDate(exerciseId, dateSelected);
            exerciseType = bodyType;
            hasData = (!bodyExerciseList.isEmpty());

        }

        // If no data, display no data text
        if (!hasData) {
            TextView textViewNoData = (TextView) rootView.findViewById(R.id.textview_no_data);
            textViewNoData.setVisibility(View.VISIBLE);
        }

        // Display the selected exercise name
        TextView textViewExerciseName = (TextView) rootView.findViewById(R.id.textview_exercise_name);
        textViewExerciseName.setText(exercise.getName());

        // Populate ListView with exercise instances
        if (exerciseType == weightType) {
            WeightExerciseAdapter listAdapter = new WeightExerciseAdapter(getActivity(), weightExerciseList);
            setListAdapter(listAdapter);

            // Display weight headers
            ((TextView) rootView.findViewById(R.id.textview_header_weight_weight)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.textview_header_weight_numReps)).setVisibility(View.VISIBLE);
        } else if (exerciseType == cardioType) {
            CardioExerciseAdapter listAdapter = new CardioExerciseAdapter(getActivity(), cardioExerciseList);
            setListAdapter(listAdapter);

            // Display cardio headers
            ((TextView) rootView.findViewById(R.id.textview_header_cardio_distance)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.textview_header_cardio_duration)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.textview_header_cardio_incline)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.textview_header_cardio_resistance)).setVisibility(View.VISIBLE);
        } else if (exerciseType == bodyType){
            BodyExerciseAdapter listAdapter = new BodyExerciseAdapter(getActivity(), bodyExerciseList);
            setListAdapter(listAdapter);

            // Display body headers
            ((TextView) rootView.findViewById(R.id.textview_header_body_numReps)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.textview_header_body_duration)).setVisibility(View.VISIBLE);
        }

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

        // Handle log button click
        Button logButton = (Button) rootView.findViewById(R.id.button_log_data);
        logButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putInt(ARG_EXERCISE_ID, exerciseId);
                bundle.putSerializable(ARG_DATE_SELECTED, dateSelected);

                if (exerciseType == weightType) {
                    AddWeightInstanceFragment fragment = new AddWeightInstanceFragment();
                    fragment.setArguments(bundle);
                    fragment.show(fm, "fragment_add_weight_instance");
                } else if (exerciseType == cardioType) {
                    AddCardioInstanceFragment fragment = new AddCardioInstanceFragment();
                    fragment.setArguments(bundle);
                    fragment.show(fm, "fragment_add_cardio_instance");
                } else {
                    AddBodyInstanceFragment fragment = new AddBodyInstanceFragment();
                    fragment.setArguments(bundle);
                    fragment.show(fm, "fragment_add_body_instance");
                }
            }
        });

        // Handle delete button click
        Button deleteButton = (Button) rootView.findViewById(R.id.button_delete_exercise);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                db.deleteExercise(exercise);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new WorkoutFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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

        return "ERROR";
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
