package com.example.arxcel.ft_hangouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ContactDatabaseAdapter contactDatabaseAdapter;

    ListView list;
    ArrayList<ListItem> items = new ArrayList<>();
    List<Contact> contacts  = new ArrayList<>();
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.mainList);

        contactDatabaseAdapter.open();

        contacts = contactDatabaseAdapter.getAllContacts();
        for (Contact contact : contacts)
        {
            items.add(new ListItem(contact.getFirstName()));
        }

        adapter = new ListAdapter(MainActivity.this, items);
        list.setAdapter(adapter);


        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = list.getItemAtPosition(position);

            }
        });
    }

    public void createContact(View view)
    {
        Intent i = new Intent(MainActivity.this, ContactEditActivity.class);
        startActivity(i);
    }
}
