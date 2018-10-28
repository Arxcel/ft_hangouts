package com.example.arxcel.ft_hangouts.data_saver;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.Cursor;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactDatabaseAdapter {
    String ok = "OK";

    static final String DATABASE_NAME = "contactsDatabase.db";
    static final String TABLE_NAME = "CONTACTS";
    static final String COL1 = "ID integer primary key autoincrement";
    static final String COL2 = "FIRSTNAME text";
    static final String COL3 = "LASTNAME text";
    static final String COL4 = "EMAIL text";

    static final int DATABASE_VERSION = 2;

    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DatabaseHelper dbHelper;

    public ContactDatabaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @NonNull
    static String getCreationQuery()
    {
        return new String("create table " + TABLE_NAME + "(" + COL1 + "," + COL2 + "," + COL3 + "," + COL4 +");");
    }

    public ContactDatabaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void close()
    {
        db.close();
    }
    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public boolean insertEntry(Contact contact)
    {
        try {
            ContentValues newValues = new ContentValues();
//            // Assign values for each column.
            newValues.put("FIRSTNAME", contact.getFirstName());
            newValues.put("LASTNAME", contact.getLastName());
            newValues.put("EMAIL", contact.getEmail());

            long result = db.insert(TABLE_NAME, null, newValues);
            if (result != -1)
            {
                Toast.makeText(context, "Contact Saved", Toast.LENGTH_LONG).show();
                return true;
            }
            else
                return false;

        } catch (Exception ex) {
            System.out.println("Exceptions " + ex);
            Log.e("Note", "One row entered");
            return false;
        }
    }

    public int deleteEntry(String id)
    {
        int numberOFEntriesDeleted= db.delete(TABLE_NAME, "ID=?", new String[]{ id }) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : " + numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    // method to get the password  of userName
    public Contact getSinlgeEntry(String id)
    {
        Contact contact = new Contact("", "", "");
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "ID=?", new String[]{ id }, null, null, null);
        if(cursor.getCount() >= 1) // UserName Not Exist
        {
            cursor.moveToFirst();
            contact.setFirstName(cursor.getString(cursor.getColumnIndex("FIRSTNAME")));
            contact.setLastName(cursor.getString(cursor.getColumnIndex("LASTNAME")));
            contact.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
            contact.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        return contact;
    }

    // Method to Update an Existing
    public void  updateEntry(Contact contact)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();

        // Assign values for each Column.
        updatedValues.put("USERNAME", contact.getFirstName());
        updatedValues.put("LASTNAME", contact.getLastName());
        updatedValues.put("EMAIL", contact.getEmail());

        String where="ID = ?";
        db.update(TABLE_NAME, updatedValues, where, new String[]{String.valueOf(contact.getId())});
    }


    public List<Contact> getAllContacts()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        String sql = new String("select * from " + TABLE_NAME);
        Cursor  cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Contact contact = new Contact("", "", "");
                contact.setFirstName(cursor.getString(cursor.getColumnIndex("FIRSTNAME")));
                contact.setLastName(cursor.getString(cursor.getColumnIndex("LASTNAME")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                contact.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                contacts.add(contact);
                cursor.moveToNext();
            }
        }
        return contacts;
    }
}
