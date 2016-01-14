package com.louiswheeleriv.fithub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.louiswheeleriv.fithub.R;

import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE_SELECTED = "dateSelected";
    private static final String ARG_EXERCISE_ID = "exerciseId";

    private Date dateSelected;
    private int exerciseId;

    private DateSelectedListener mListener;

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
        return fragment;
    }

    public DatePickerFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_date_picker, container, false);

        // Set the selected date for the date picker
        DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        datePicker.updateDate(
                (dateSelected.getYear() + 1900),
                dateSelected.getMonth(),
                dateSelected.getDate()
        );

        // Handle click for close button
        Button closeButton = (Button) rootView.findViewById(R.id.button_close_dialog);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
                dateSelected = new Date(
                        datePicker.getYear()-1900,
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth()
                );

                DateSelectedListener activity = (DateSelectedListener) getActivity();
                activity.onDateSelected(dateSelected, exerciseId);
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DateSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DateSelectedListener {
        void onDateSelected(Date date, int exerciseId);
    }

}
