package com.example.registration.Responce;

public class UserRegistrationInput {

    private final String mail;
    private final String username;
    private final String password;

    public UserRegistrationInput(String mail, String username, String password) {
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
