package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.objects.WeightExercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import java.util.Date;

public class AddWeightInstanceFragment extends DialogFragment {

    private static final String ARG_DATE_SELECTED = "dateSelected";
    private static final String ARG_EXERCISE_ID = "exerciseId";

    private Date dateSelected;
    private int exerciseId;

    private Exercise exercise;

    private DatabaseHandler db;

    private WeightInstanceCreatedListener mListener;

    public static AddWeightInstanceFragment newInstance() {
        AddWeightInstanceFragment fragment = new AddWeightInstanceFragment();
        return fragment;
    }

    public AddWeightInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateSelected = (Date) getArguments().getSerializable(ARG_DATE_SELECTED);
            exerciseId = getArguments().getInt(ARG_EXERCISE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_weight_instance, container, false);

        db = new DatabaseHandler(getActivity());

        // Populate the exercise variable
        exercise = db.getExercise(exerciseId);

        // Populate textview with exercise name
        TextView textViewExerciseName = (TextView) rootView.findViewById(R.id.textView_create_weight_instance_name);
        textViewExerciseName.setText(exercise.getName());

        // Handle click for log button
        Button logButton = (Button) rootView.findViewById(R.id.button_create_weight_instance);
        logButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText editTextWeight = (EditText) rootView.findViewById(R.id.editText_create_weight_instance_weight);
                EditText editTextNumReps = (EditText) rootView.findViewById(R.id.editText_create_weight_instance_numReps);

                int weight = Integer.parseInt(editTextWeight.getText().toString());
                int numReps = Integer.parseInt(editTextNumReps.getText().toString());

                exercise = db.getExercise(exerciseId);

                WeightExercise we = new WeightExercise(exercise, dateSelected, numReps, weight);
                db.addWeightExercise(we);

                WeightInstanceCreatedListener activity = (WeightInstanceCreatedListener) getActivity();
                activity.onWeightInstanceCreated(we, dateSelected, exerciseId);
                dismiss();
            }
        });

        // Handle back button
        rootView.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, android.view.KeyEvent event) {
                if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK)) {
                    dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Handle click for cancel button
        Button cancelButton = (Button) rootView.findViewById(R.id.button_create_weight_instance_cancel);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WeightInstanceCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement WeightInstanceCreatedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface WeightInstanceCreatedListener {
        void onWeightInstanceCreated(WeightExercise we, Date dateSelected, int exerciseId);
    }
}
