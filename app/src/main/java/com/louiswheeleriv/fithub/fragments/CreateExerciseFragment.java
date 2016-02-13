package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.Exercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

public class CreateExerciseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private DatabaseHandler db;

    public static CreateExerciseFragment newInstance() {
        CreateExerciseFragment fragment = new CreateExerciseFragment();
        return fragment;
    }

    public CreateExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        db = new DatabaseHandler(getActivity());

        // Handle EditText events
        final EditText exerciseNameEditText = (EditText) rootView.findViewById(R.id.edit_text_exercise_name);

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

        // Apply custom spinner_item.xml layout to spinner
        final Spinner exerciseTypeSpinner = (Spinner) rootView.findViewById(R.id.spinner_exercise_type);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.exercise_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        exerciseTypeSpinner.setAdapter(adapter);

        // If "Cardio" spinner option selected, display extra options
        final LinearLayout linearLayoutIncludeInclineResistance = (LinearLayout) rootView.findViewById(R.id.linearLayout_include_incline_resistance);
        String[] listOptions = getResources().getStringArray(R.array.exercise_types);
        int pos = -1;
        for (int i = 0; i < listOptions.length; i++) {
            if (listOptions[i].equals("Cardio")) {
                pos = i;
                break;
            }
        }
        final int CARDIO_POS_IN_LIST = pos;

        if (CARDIO_POS_IN_LIST > -1) {
            exerciseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                    if (pos == CARDIO_POS_IN_LIST) {
                        linearLayoutIncludeInclineResistance.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutIncludeInclineResistance.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        }

        // Finish and save exercise button listener
        Button finishExerciseButton = (Button) rootView.findViewById(R.id.button_create_exercise_finish);
        finishExerciseButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String exName = exerciseNameEditText.getText().toString();
                String exType = exerciseTypeSpinner.getSelectedItem().toString();
                boolean includesIncline;
                boolean includesResistance;
                if (exType.equals("Cardio")) {
                    CheckBox checkboxIncludeIncline = (CheckBox) rootView.findViewById(R.id.checkbox_includes_incline);
                    CheckBox checkboxIncludeResistance = (CheckBox) rootView.findViewById(R.id.checkbox_includes_resistance);
                    includesIncline = checkboxIncludeIncline.isChecked();
                    includesResistance = checkboxIncludeResistance.isChecked();
                } else {
                    includesIncline = false;
                    includesResistance = false;
                }

                String[] types = getActivity().getResources().getStringArray(R.array.exercise_types);

                if (exType.equals(types[0])) {
                    exType = getActivity().getResources().getString(R.string.const_weight_exercise);
                } else if (exType.equals(types[1])) {
                    exType = getActivity().getResources().getString(R.string.const_cardio_exercise);
                } else {
                    exType = getActivity().getResources().getString(R.string.const_body_exercise);
                }

                Exercise exercise = new Exercise(exName, exType, includesIncline, includesResistance);
                db.addExercise(exercise);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new WorkoutFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

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
