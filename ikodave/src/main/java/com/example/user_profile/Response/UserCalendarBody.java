package com.example.user_profile.Response;

public class UserCalendarBody {
    private String username;
    private int month;
    private int year;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UserCalendarBody(String username, int month, int year) {
        this.username = username;
        this.month = month;
        this.year = year;
    }
}
