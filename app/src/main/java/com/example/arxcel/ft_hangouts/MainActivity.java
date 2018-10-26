package com.example.arxcel.ft_hangouts;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ArrayList<ListItem> items = new ArrayList<>();
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.mainList);
//        items.add(new ListItem("John"));
//        items.add(new ListItem("Bill"));
//        items.add(new ListItem("Jack"));
//        items.add(new ListItem("Raz"));
//        items.add(new ListItem("Ben"));
//        items.add(new ListItem("Bob"));
//        items.add(new ListItem("Crest"));
//        items.add(new ListItem("Jeriko"));

        adapter = new ListAdapter(MainActivity.this, items);
        list.setAdapter(adapter);
    }

    public void foo(View view)
    {
        items.add(new ListItem("John"));
        adapter.notifyDataSetChanged();
    }
}
