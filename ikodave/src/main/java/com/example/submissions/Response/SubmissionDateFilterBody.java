package com.example.submissions.Response;

public class SubmissionDateFilterBody {

    private String username;
    private int day;
    private int month;
    private int year;

    public SubmissionDateFilterBody() {
        // Default constructor for JSON deserialization
    }

    public SubmissionDateFilterBody(String username, int day, int month, int year) {
        this.username = username;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
}
