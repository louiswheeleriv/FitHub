package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.objects.CardioExercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import java.util.Date;

public class AddCardioInstanceFragment extends DialogFragment {

    private static final String ARG_DATE_SELECTED = "dateSelected";
    private static final String ARG_EXERCISE_ID = "exerciseId";

    private Date dateSelected;
    private int exerciseId;

    private Exercise exercise;

    private DatabaseHandler db;

    private CardioInstanceCreatedListener mListener;

    public static AddCardioInstanceFragment newInstance() {
        AddCardioInstanceFragment fragment = new AddCardioInstanceFragment();
        return fragment;
    }

    public AddCardioInstanceFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_add_cardio_instance, container, false);

        db = new DatabaseHandler(getActivity());

        // Retrieve exercise variable
        exercise = db.getExercise(exerciseId);

        // Populate exercise name
        TextView textViewExerciseName = (TextView) rootView.findViewById(R.id.textView_create_cardio_instance_name);
        textViewExerciseName.setText(exercise.getName());

        // Handle click for log button
        Button logButton = (Button) rootView.findViewById(R.id.button_create_cardio_instance);
        logButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText editTextDistance = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_distance);
                EditText editTextDuration = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_duration);
                EditText editTextInclination = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_incline);
                EditText editTextResistance = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_resistance);

                double distance = Double.parseDouble(editTextDistance.getText().toString());
                int duration = Integer.parseInt(editTextDuration.getText().toString());
                int inclination = Integer.parseInt(editTextInclination.getText().toString());
                int resistance = Integer.parseInt(editTextResistance.getText().toString());

                exercise = db.getExercise(exerciseId);

                CardioExercise ce = new CardioExercise(exercise, dateSelected, distance, duration, inclination, resistance);
                db.addCardioExercise(ce);

                CardioInstanceCreatedListener activity = (CardioInstanceCreatedListener) getActivity();
                activity.onCardioInstanceCreated(ce, dateSelected, exerciseId);
                dismiss();
            }
        });

        // Handle back button
        rootView.setOnKeyListener(new View.OnKeyListener() {
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
        Button cancelButton = (Button) rootView.findViewById(R.id.button_create_cardio_instance_cancel);
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
            mListener = (CardioInstanceCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CardioInstanceCreatedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CardioInstanceCreatedListener {
        void onCardioInstanceCreated(CardioExercise ce, Date dateSelected, int exerciseId);
    }
}
