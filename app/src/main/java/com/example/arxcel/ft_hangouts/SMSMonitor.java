package com.example.arxcel.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

public class SMSMonitor extends BroadcastReceiver {
    ContactDatabaseAdapter contactDatabaseAdapter;

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {

        contactDatabaseAdapter = new ContactDatabaseAdapter(App.getAppContext());
        contactDatabaseAdapter.open();

        if (intent.getAction().equals(ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                if (!contactDatabaseAdapter.isContains(sender))
                {
                    Contact contact = new Contact(sender, "", "", sender, null);
                    contactDatabaseAdapter.insertEntry(contact);
                }
                Toast.makeText(context,  "Received message from: " + sender, Toast.LENGTH_SHORT).show();
                abortBroadcast();
            }
        }
    }
}
