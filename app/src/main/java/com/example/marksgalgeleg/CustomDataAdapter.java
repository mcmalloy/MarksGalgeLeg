package com.example.marksgalgeleg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomDataAdapter extends ArrayAdapter<Data> {
    public CustomDataAdapter(Context context, ArrayList<Data> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_data, parent, false);
        }

        // Get the data item for this position
        Data data = getItem(position);


        // Return the completed view to render on screen
        return convertView;
    }
}
