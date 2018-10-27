package com.example.arxcel.ft_hangouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

public class ContactActivity extends AppCompatActivity {

    ContactDatabaseAdapter contactDatabaseAdapter;

    TextView mFirstName;
    TextView mLastName;
    TextView mEmail;

    String firstName;
    String lastName;
    String email;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        contactDatabaseAdapter.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mFirstName = findViewById(R.id.mFirstName);
        mLastName = findViewById(R.id.mLastName);
        mEmail = findViewById(R.id.mEmail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            email = extras.getString("email");
            id = extras.getInt("id");

            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            mEmail.setText(email);
        }
    }

    public void editContact(View view)
    {
//        contactDatabaseAdapter.insertEntry(new Contact("John", "lastName", "@example"));
        Intent i = new Intent(ContactActivity.this, ContactEditActivity.class);
        i.putExtra("newContact", false);
        i.putExtra("firstName", firstName);
        i.putExtra("lastName", lastName);
        i.putExtra("email", email);
        i.putExtra("id", id);
        startActivity(i);
    }
}
