package com.example.arxcel.ft_hangouts;

/**
 * Created by arxcel on 10/24/18.
 */

public class ListItem {
    private String firstName;

    public String getName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }


    public ListItem(String firstName) {

        this.firstName = firstName;
    }
}
