package com.example.registration.model;

import java.util.Date;

public class User {
    int id;
    private String username;
    private String password;
    private int roleId;
    private Date registerDate;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        roleId = 1;
    }

    public User(int id, String username, String password, int roleId, Date registerDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.registerDate = registerDate;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}