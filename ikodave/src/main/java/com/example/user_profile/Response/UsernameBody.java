package com.example.user_profile.Response;

public class UsernameBody {

    private String username;

    public UsernameBody(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}