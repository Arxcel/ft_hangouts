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

public class MessageListAdapter extends ArrayAdapter<Message> {

    public MessageListAdapter(@NonNull Context context, ArrayList<Message> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
        Message c = getItem(position);
        TextView message = convertView.findViewById(R.id.mMessage);
        TextView date = convertView.findViewById(R.id.mDate);
        TextView sender = convertView.findViewById(R.id.mSender);
        message.setText(c.getMessage());
        date.setText(c.getDate());
//        if (c.isOwn())
//            sender.setText("Me");
//        else
//            sender.setText(c.getFrom());
        return convertView;
    }
}
