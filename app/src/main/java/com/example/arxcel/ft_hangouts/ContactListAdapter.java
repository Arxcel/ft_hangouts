package com.example.arxcel.ft_hangouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arxcel.ft_hangouts.data_saver.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    public ContactListAdapter(@NonNull Context context, ArrayList<Contact> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        Contact c = getItem(position);
        TextView name = convertView.findViewById(R.id.mDisplayName);
        TextView phone = convertView.findViewById(R.id.mPhone);
        ImageView avatar = convertView.findViewById(R.id.mAvatar);

        String displayName = c.getFirstName();
        displayName = displayName.isEmpty() ? c.getPhone() : displayName;
        name.setText(displayName);
        phone.setText(c.getPhone());
        if(c.getAvatar() != null)
            avatar.setImageBitmap(c.getAvatar());
        return convertView;
    }
}
