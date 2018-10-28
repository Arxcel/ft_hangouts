package com.example.arxcel.ft_hangouts;

import com.example.arxcel.ft_hangouts.data_saver.Contact;

/**
 * Created by arxcel on 10/24/18.
 */

public class ListItem {
    private String firstName;
    private String lastName;
    private String email;
    private int id = -1;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListItem(Contact c) {

        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.email = c.getEmail();
        this.id = c.getId();

    }
}
