package com.example.arxcel.ft_hangouts;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

public class ContactActivity extends AppCompatActivity {
    SharedPreferences mPrefs;

    ContactDatabaseAdapter contactDatabaseAdapter;

    TextView mFirstName;
    TextView mLastName;
    TextView mEmail;
    TextView mPhone;

    String firstName;
    String lastName;
    String email;
    String phone;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        switch (mPrefs.getString("color", "red"))
        {
            case "red":
                setTheme(R.style.RedTheme);
                break;
            case "blue":
                setTheme(R.style.BlueTheme);
                break;
            default:
                setTheme(R.style.GreenTheme);
        }
        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        contactDatabaseAdapter.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mFirstName = findViewById(R.id.mFirstName);
        mLastName = findViewById(R.id.mLastName);
        mEmail = findViewById(R.id.mEmail);
        mPhone = findViewById(R.id.mPhone);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            email = extras.getString("email");
            id = extras.getInt("id");
            phone = extras.getString("phone");

            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            mEmail.setText(email);
            mPhone.setText(phone);
        }
    }

    public void editContact(View view)
    {
        Intent i = new Intent(ContactActivity.this, ContactEditActivity.class);
        i.putExtra("newContact", false);
        i.putExtra("firstName", firstName);
        i.putExtra("lastName", lastName);
        i.putExtra("email", email);
        i.putExtra("id", id);
        i.putExtra("phone", phone);
        startActivity(i);
    }
    public void deleteContact(View view)
    {
        try
        {
            contactDatabaseAdapter.deleteEntry(Integer.toString(id));
        } catch (Exception e)
        {
            Toast.makeText(this, "Deleting contact failed", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(ContactActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void callContact(View view)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        else
        {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }
    }

    public void sendSMS(View view)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.READ_SMS}, 4);
        else
        {
            Intent i = new Intent(ContactActivity.this, Chat.class);
            i.putExtra("receiverNumber", phone);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        SharedPreferences.Editor editor = mPrefs.edit();
        switch (id) {
            case R.id.action_red:
                editor.putString("color", "red");
                editor.apply();
                recreate();
                return true;
            case R.id.action_green:
                editor.putString("color", "green");
                editor.apply();
                recreate();
                return true;
            case R.id.action_blue:
                editor.putString("color", "blue");
                editor.apply();
                recreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
