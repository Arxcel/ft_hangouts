package com.example.arxcel.ft_hangouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arxcel on 10/25/18.
 */

public class ListAdapter extends ArrayAdapter<ListItem> {

    public ListAdapter(@NonNull Context context, ArrayList<ListItem> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        ListItem item = getItem(position);
        TextView name = convertView.findViewById(R.id.mName);
        name.setText(item.getFirstName());
        return convertView;
    }
}
