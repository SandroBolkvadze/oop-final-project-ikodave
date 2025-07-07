package com.example.registration.utils;

public class UserInput {

    private final String username;

    private final String password;

    public UserInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
