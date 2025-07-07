package com.example.registration.model;

import java.util.Date;

public class User {
    int id;
    private String username;
    private String password;
    private int rankId;
    private Date registerDate;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rank_id) {
        this.rankId = rank_id;
    }

    public Date getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}