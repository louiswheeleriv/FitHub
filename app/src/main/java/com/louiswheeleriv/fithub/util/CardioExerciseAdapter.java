package com.louiswheeleriv.fithub.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.CardioExercise;

import java.util.List;

public class CardioExerciseAdapter extends BaseAdapter {

    Context context;
    List<CardioExercise> data;
    private static LayoutInflater inflater = null;

    public CardioExerciseAdapter(Context context, List<CardioExercise> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_item_cardio, null);
        }
        TextView textViewDistance = (TextView) vi.findViewById(R.id.list_item_distance);
        TextView textViewDuration = (TextView) vi.findViewById(R.id.list_item_duration);
        TextView textViewInclination = (TextView) vi.findViewById(R.id.list_item_inclination);
        TextView textViewResistance = (TextView) vi.findViewById(R.id.list_item_resistance);

        textViewDistance.setText(String.valueOf(data.get(position).getDistance()) + " mi");

        int duration = data.get(position).getDuration();
        int hours = (duration / 3600);
        int minutes = ((duration % 3600) / 60);
        int seconds = (duration % 60);
        String hoursString = String.valueOf(hours);
        String minutesString = (hours > 0 && minutes < 10) ? ("0"+String.valueOf(minutes)) : String.valueOf(minutes);
        String secondsString = ((hours > 0 || minutes > 0) && seconds < 10) ? ("0"+String.valueOf(seconds)) : String.valueOf(seconds);

        if (hours > 0) {
            textViewDuration.setText(hoursString + ":" + minutesString + ":" + secondsString);
        } else if (minutes > 0) {
            textViewDuration.setText(minutesString + ":" + secondsString);
        } else {
            textViewDuration.setText(secondsString + " sec");
        }
        textViewInclination.setText(String.valueOf(data.get(position).getInclination()));
        textViewResistance.setText(String.valueOf(data.get(position).getResistance()));

        return vi;
    }

}
