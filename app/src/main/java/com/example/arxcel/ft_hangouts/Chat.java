package com.example.arxcel.ft_hangouts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arxcel.ft_hangouts.data_saver.Contact;

import java.util.ArrayList;
import java.util.Date;

public class Chat extends AppCompatActivity {

    String receiverNumber;
    ArrayList<Message> messages  = new ArrayList<>();
    MessageListAdapter adapter;
    ListView list;
    EditText message;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiverNumber = extras.getString("receiverNumber");
        }
        getSMS(getApplicationContext());
        list = findViewById(R.id.chat);
        adapter = new MessageListAdapter(Chat.this, messages);
        list.setAdapter(adapter);
        message = findViewById(R.id.mMessage);
    }

    public void sendMessage(View view)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Chat.this, new String[]{Manifest.permission.SEND_SMS}, 1);

        SmsManager smsManager = SmsManager.getDefault();
        String mBody = message.getText().toString();
        smsManager.sendTextMessage(receiverNumber, null, mBody, null, null);
        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
    }

    public void getSMS(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    String type;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }
                    c.moveToNext();
                    if (number.equals(receiverNumber))
                    {
                        messages.add(new Message(dateFormat.toString(), body, number));
                    }
                }
            }
            c.close();
        }
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
}
