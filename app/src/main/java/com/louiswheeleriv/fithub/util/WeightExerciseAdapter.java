package com.louiswheeleriv.fithub.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.WeightExercise;

import java.util.List;

public class WeightExerciseAdapter extends BaseAdapter {

    Context context;
    List<WeightExercise> data;
    private static LayoutInflater inflater = null;

    public WeightExerciseAdapter(Context context, List<WeightExercise> data) {
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
        TextView textViewWeightAndNumReps = (TextView) vi.findViewById(R.id.list_item_weight_and_numReps);
        String textViewLabel = (
                String.valueOf(data.get(position).getNumReps() + " x "
                + String.valueOf(data.get(position).getWeight() + " lbs"))
        );
        textViewWeightAndNumReps.setText(textViewLabel);
        textViewWeightAndNumReps.setTypeface(null, Typeface.BOLD);
        return vi;
    }

}
