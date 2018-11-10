package com.example.arxcel.ft_hangouts;

public class Message {
    private String date;
    private String message;
    private String from;
    private boolean isOwn;

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }

    public Message(String date, String message, String from, boolean isOwn) {
        this.isOwn = isOwn;

        this.date = date;
        this.message = message;
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
