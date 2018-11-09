package com.example.arxcel.ft_hangouts.data_saver;

import android.graphics.Bitmap;

public class Contact  {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int id = -1;
    private Bitmap avatar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public Contact(String firstName, String lastName, String email, String phone, Bitmap avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
