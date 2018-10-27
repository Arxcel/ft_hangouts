package com.example.arxcel.ft_hangouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

public class ContactEditActivity extends AppCompatActivity {

ContactDatabaseAdapter contactDatabaseAdapter;


    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;

    String firstName;
    String lastName;
    String email;
    int id;

    boolean isNew = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        contactDatabaseAdapter.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        mFirstName = findViewById(R.id.mFirstNameEdit);
        mLastName = findViewById(R.id.mLastNameEdit);
        mEmail = findViewById(R.id.mEmailEdit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            email = extras.getString("email");
            isNew = extras.getBoolean("newContact");
            id = extras.getInt("id");

            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            mEmail.setText(email);
        }
    }

    public void saveContact(View view)
    {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        email = mEmail.getText().toString();
        Contact newContact = new Contact(firstName, lastName, email);
        newContact.setId(id);
        if (isNew)
        {
            contactDatabaseAdapter.insertEntry(newContact);
        }
        else
        {
           contactDatabaseAdapter.updateEntry(newContact);
        }
        Intent i = new Intent(ContactEditActivity.this, MainActivity.class);
        startActivity(i);
    }
}
