package com.example.arxcel.ft_hangouts.data_saver;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.Cursor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.Toast;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;

public class ContactDatabaseAdapter {
    String ok = "OK";

    static final String DATABASE_NAME = "contactsDatabase.db";
    static final String TABLE_NAME = "CONTACTS";
    static final String COL1 = "ID integer primary key autoincrement";
    static final String COL2 = "FIRSTNAME text";
    static final String COL3 = "LASTNAME text";
    static final String COL4 = "EMAIL text";
    static final String COL5 = "PHONE text";

    static final int DATABASE_VERSION = 8;

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
        return new String("create table " + TABLE_NAME + "(" + COL1 + "," + COL2 + "," + COL3 + "," + COL4 + "," + COL5 + ");");
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
            newValues.put("PHONE", contact.getPhone());
//            newValues.put("AVATAR", Base64.encodeToString(image, 0));

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
        return numberOFEntriesDeleted;
    }
    public Contact getSinlgeEntry(String id)
    {
        Contact contact = new Contact("", "", "", "", null);
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "ID=?", new String[]{ id }, null, null, null);
        if(cursor.getCount() >= 1) // UserName Not Exist
        {
            cursor.moveToFirst();
            contact.setFirstName(cursor.getString(cursor.getColumnIndex("FIRSTNAME")));
            contact.setLastName(cursor.getString(cursor.getColumnIndex("LASTNAME")));
            contact.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
            contact.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            contact.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
//            byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("AVATAR"));
//            contact.setAvatar(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
        }
        return contact;
    }
    // Method to Update an Existing
    public void  updateEntry(Contact contact)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();

        // Assign values for each Column.
        updatedValues.put("FIRSTNAME", contact.getFirstName());
        updatedValues.put("LASTNAME", contact.getLastName());
        updatedValues.put("EMAIL", contact.getEmail());
        updatedValues.put("PHONE", contact.getPhone());
//        updatedValues.put("AVATAR",  getBitmapAsByteArray(contact.getAvatar()));
        String where="ID = ?";
        db.update(TABLE_NAME, updatedValues, where, new String[]{String.valueOf(contact.getId())});
    }


    public ArrayList<Contact> getAllContacts()
    {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        String sql = new String("select * from " + TABLE_NAME);
        Cursor  cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Contact contact = new Contact("", "", "", "", null);
                contact.setFirstName(cursor.getString(cursor.getColumnIndex("FIRSTNAME")));
                contact.setLastName(cursor.getString(cursor.getColumnIndex("LASTNAME")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                contact.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                contact.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
//                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("AVATAR"));
//                contact.setAvatar(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                contacts.add(contact);
                cursor.moveToNext();
            }
        }
        return contacts;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}


