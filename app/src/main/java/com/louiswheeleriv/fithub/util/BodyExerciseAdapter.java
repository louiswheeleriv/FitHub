package com.louiswheeleriv.fithub.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.objects.BodyExercise;

import java.util.List;

public class BodyExerciseAdapter extends BaseAdapter {

    Context context;
    List<BodyExercise> data;
    private static LayoutInflater inflater = null;

    public BodyExerciseAdapter(Context context, List<BodyExercise> data) {
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
        TextView textViewNumReps = (TextView) vi.findViewById(R.id.list_item_num_reps);
        TextView textViewDuration = (TextView) vi.findViewById(R.id.list_item_duration);
        textViewNumReps.setText(data.get(position).getNumReps());
        textViewDuration.setText(data.get(position).getDuration());
        return vi;
    }

}
