package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        // Hide incline and resistance inputs if not used
        if (!exercise.includesIncline()) {
            LinearLayout linearLayoutIncline = (LinearLayout) rootView.findViewById(R.id.linearLayout_create_cardio_instance_incline);
            linearLayoutIncline.setVisibility(View.GONE);
        }
        if (!exercise.includesResistance()) {
            LinearLayout linearLayoutResistance = (LinearLayout) rootView.findViewById(R.id.linearLayout_create_cardio_instance_resistance);
            linearLayoutResistance.setVisibility(View.GONE);
        }

        final EditText editTextDistance = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_distance);
        final EditText editTextDuration = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_duration);
        final EditText editTextIncline = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_incline);
        final EditText editTextResistance = (EditText) rootView.findViewById(R.id.editText_create_cardio_instance_resistance);

        // Handle click for log button
        final Button logButton = (Button) rootView.findViewById(R.id.button_create_cardio_instance);
        logButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                double distance = Double.parseDouble(editTextDistance.getText().toString());
                int duration = Integer.parseInt(editTextDuration.getText().toString());

                int incline;
                if (exercise.includesIncline()) {
                    incline = Integer.parseInt(editTextIncline.getText().toString());
                } else {
                    incline = 0;
                }

                int resistance;
                if (exercise.includesResistance()) {
                    resistance = Integer.parseInt(editTextResistance.getText().toString());
                } else {
                    resistance = 0;
                }

                exercise = db.getExercise(exerciseId);

                CardioExercise ce = new CardioExercise(exercise, dateSelected, distance, duration, incline, resistance);
                db.addCardioExercise(ce);

                CardioInstanceCreatedListener activity = (CardioInstanceCreatedListener) getActivity();
                activity.onCardioInstanceCreated(ce, dateSelected, exerciseId);
                dismiss();
            }
        });

        // Ensure log button is only enabled if
        // appropriate fields have been filled out
        TextWatcher watcher= new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!editTextDistance.getText().toString().isEmpty() &&
                        !editTextDuration.getText().toString().isEmpty()) {

                    if ((!exercise.includesIncline() || !editTextIncline.getText().toString().isEmpty()) &&
                            (!exercise.includesResistance() || !editTextResistance.getText().toString().isEmpty())) {

                        logButton.setEnabled(true);
                        return;
                    }
                }

                if (logButton.isEnabled()) {
                    logButton.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        editTextDistance.addTextChangedListener(watcher);
        editTextDuration.addTextChangedListener(watcher);
        editTextIncline.addTextChangedListener(watcher);
        editTextResistance.addTextChangedListener(watcher);

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
