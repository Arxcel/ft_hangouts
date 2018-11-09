package com.example.arxcel.ft_hangouts;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ContactDatabaseAdapter contactDatabaseAdapter;
    private boolean isReturnPressed = false;
    ListView list;
    ArrayList<Contact> contacts  = new ArrayList<>();
    ContactListAdapter adapter;

    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        switch (mPrefs.getString("color", "red")) {
            case "red":
                setTheme(R.style.RedTheme);
                break;
            case "blue":
                setTheme(R.style.BlueTheme);
                break;
            default:
                setTheme(R.style.GreenTheme);
        }

        isReturnPressed = false;
        super.onCreate(savedInstanceState);
        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        setContentView(R.layout.activity_main);


        list = findViewById(R.id.mainList);

        contactDatabaseAdapter.open();

        contacts = contactDatabaseAdapter.getAllContacts();

        contacts.sort(Comparator.comparing(Contact::getFirstName));
        adapter = new ContactListAdapter(MainActivity.this, contacts);
        list.setAdapter(adapter);

        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Contact item = (Contact)list.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, ContactActivity.class);
                i.putExtra("newContact", false);
                i.putExtra("firstName", item.getFirstName());
                i.putExtra("lastName", item.getLastName());
                i.putExtra("email", item.getEmail());
                i.putExtra("id", item.getId());
                i.putExtra("phone", item.getPhone());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void createContact(View view)
    {
        Intent i = new Intent(MainActivity.this, ContactEditActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (isReturnPressed)
            {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                isReturnPressed = false;
            }
            else
            {
//                getAllSms(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Press one more time to exit", Toast.LENGTH_LONG).show();
                isReturnPressed = true;
            }
            return true;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
