package com.example.login1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Helpful tutorial found at
// https://medium.com/@mas2017annascott/clickable-listview-items-with-clickable-buttons-e52fa6030d36
public class MedButtonAdapter extends ArrayAdapter<Medication> {

    private static final String LOG_TAG = MedButtonAdapter.class.getSimpleName();

    public MedButtonAdapter(Activity context, ArrayList<Medication> medications) {
        super(context, 0, medications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Medication currMed = getItem(position);

        ImageView img= (ImageView) listItemView.findViewById(R.id.list_item_icon);
        img.setImageResource(R.drawable.ic_med);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.med_name);
        nameTextView.setText(currMed.GetName());

        return listItemView;
    }
}