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
            vi = inflater.inflate(R.layout.list_item_weight, null);
        }
        TextView textViewDistance = (TextView) vi.findViewById(R.id.list_item_distance);
        TextView textViewDuration = (TextView) vi.findViewById(R.id.list_item_duration);
        TextView textViewInclination = (TextView) vi.findViewById(R.id.list_item_inclination);
        TextView textViewResistance = (TextView) vi.findViewById(R.id.list_item_resistance);
        textViewDistance.setText(data.get(position).getDistance());
        textViewDuration.setText(data.get(position).getDuration());
        textViewInclination.setText(data.get(position).getInclination());
        textViewResistance.setText(data.get(position).getResistance());
        return vi;
    }

}
