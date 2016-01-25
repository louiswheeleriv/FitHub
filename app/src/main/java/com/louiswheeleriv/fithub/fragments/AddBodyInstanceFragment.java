package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.objects.BodyExercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import java.util.Date;

public class AddBodyInstanceFragment extends DialogFragment {

    private static final String ARG_DATE_SELECTED = "dateSelected";
    private static final String ARG_EXERCISE_ID = "exerciseId";

    private Date dateSelected;
    private int exerciseId;

    private Exercise exercise;

    private DatabaseHandler db;

    private BodyInstanceCreatedListener mListener;

    public static AddBodyInstanceFragment newInstance() {
        AddBodyInstanceFragment fragment = new AddBodyInstanceFragment();
        return fragment;
    }

    public AddBodyInstanceFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_add_body_instance, container, false);

        db = new DatabaseHandler(getActivity());

        // Handle click for log button
        Button logButton = (Button) rootView.findViewById(R.id.button_create_body_instance);
        logButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText editTextNumReps = (EditText) rootView.findViewById(R.id.editText_create_body_instance_numReps);
                EditText editTextDuration = (EditText) rootView.findViewById(R.id.editText_create_body_instance_duration);

                int numReps = Integer.parseInt(editTextNumReps.getText().toString());
                int duration = Integer.parseInt(editTextDuration.getText().toString());

                exercise = db.getExercise(exerciseId);

                BodyExercise be = new BodyExercise(exercise, dateSelected, numReps, duration);
                db.addBodyExercise(be);

                BodyInstanceCreatedListener activity = (BodyInstanceCreatedListener) getActivity();
                activity.onBodyInstanceCreated(be, dateSelected, exerciseId);
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
        Button cancelButton = (Button) rootView.findViewById(R.id.button_create_body_instance_cancel);
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
            mListener = (BodyInstanceCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement BodyInstanceCreatedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface BodyInstanceCreatedListener {
        void onBodyInstanceCreated(BodyExercise be, Date dateSelected, int exerciseId);
    }
}
