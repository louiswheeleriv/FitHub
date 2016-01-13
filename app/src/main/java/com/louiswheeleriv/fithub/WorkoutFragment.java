package com.louiswheeleriv.fithub;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;


public class WorkoutFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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

        // Create exercise button listener
        Button createExerciseButton = (Button) inflatedView.findViewById(R.id.button_create_exercise);
        createExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleCreateExerciseSection(inflatedView, true);
            }
        });

        // Handle EditText events
        final EditText exerciseNameEditText = (EditText) inflatedView.findViewById(R.id.edit_text_exercise_name);

        exerciseNameEditText.setOnClickListener(new EditText.OnClickListener() {
            // Show the cursor when the EditText is clicked
            public void onClick(View v) {
                exerciseNameEditText.setCursorVisible(true);
            }
        });

        exerciseNameEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            // Remove the cursor when done typing
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    exerciseNameEditText.setCursorVisible(false);
                }
                return false;
            }
        });

        // Finish and save exercise button listener
        Button finishExerciseButton = (Button) inflatedView.findViewById(R.id.button_create_exercise_finish);
        finishExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleCreateExerciseSection(inflatedView, false);
            }
        });

        // Log exercise button listener
        Button logExerciseButton = (Button) inflatedView.findViewById(R.id.button_log_exercise);
        logExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });

        // Apply custom spinner_item.xml layout to spinner
        Spinner exerciseTypeSpinner = (Spinner) inflatedView.findViewById(R.id.spinner_exercise_type);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.exercise_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        exerciseTypeSpinner.setAdapter(adapter);

        // Return the inflated view
        return inflatedView;
    }

    public void toggleCreateExerciseSection(View view, Boolean creatingExercise) {
        LinearLayout workoutBaseView = (LinearLayout) view.findViewById(R.id.linear_workout_base);
        LinearLayout workoutCreateExerciseView = (LinearLayout) view.findViewById(R.id.linear_workout_create_exercise);

        if (creatingExercise) {
            workoutBaseView.setVisibility(View.GONE);
            workoutCreateExerciseView.setVisibility(View.VISIBLE);
        } else {
            workoutCreateExerciseView.setVisibility(View.GONE);
            workoutBaseView.setVisibility(View.VISIBLE);
        }
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
