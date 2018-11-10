package com.example.arxcel.ft_hangouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arxcel.ft_hangouts.data_saver.Contact;
import com.example.arxcel.ft_hangouts.data_saver.ContactDatabaseAdapter;

import java.io.IOException;

public class ContactEditActivity extends AppCompatActivity {
    class RequestCode {
        static final int LOAD_IMAGE = 100;
    }
ContactDatabaseAdapter contactDatabaseAdapter;

    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mPhone;
    ImageView mAva;

    String firstName;
    String lastName;
    String email;
    String phone;
    Bitmap avatar;

    int id;
    SharedPreferences mPrefs;

    boolean isNew = true;
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
        contactDatabaseAdapter = new ContactDatabaseAdapter(getApplicationContext());
        contactDatabaseAdapter.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        mFirstName = findViewById(R.id.mFirstNameEdit);
        mLastName = findViewById(R.id.mLastNameEdit);
        mEmail = findViewById(R.id.mEmailEdit);
        mPhone = findViewById(R.id.mPhoneEdit);
        mAva = findViewById(R.id.mAvatar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            email = extras.getString("email");
            isNew = extras.getBoolean("newContact");
            id = extras.getInt("id");
            phone = extras.getString("phone");

            if(getIntent().hasExtra("avatar")) {
                avatar = BitmapFactory.decodeByteArray(
                        getIntent().getByteArrayExtra("avatar"),0,getIntent().getByteArrayExtra("avatar").length);
                mAva.setImageBitmap(avatar);
            }

            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            mEmail.setText(email);
            mPhone.setText(phone);
        }
    }

    public void saveContact(View view)
    {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        email = mEmail.getText().toString();
        phone = mPhone.getText().toString();

        avatar = getBitmapFromDrawable(mAva.getDrawable());

        if (phone.trim().equals(""))
        {
            mPhone.setHint("Please enter phone");
            mPhone.setError("Please enter phone");
        }
        else
        {
            Contact newContact = new Contact(firstName, lastName, email, phone, avatar);
            newContact.setId(id);
            if (isNew)
                contactDatabaseAdapter.insertEntry(newContact);
            else
                contactDatabaseAdapter.updateEntry(newContact);
            Intent i = new Intent(ContactEditActivity.this, MainActivity.class);
            startActivity(i);
        }
    }


    public void loadImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, RequestCode.LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RequestCode.LOAD_IMAGE && resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    mAva.setImageURI(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @NonNull
    static private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
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
