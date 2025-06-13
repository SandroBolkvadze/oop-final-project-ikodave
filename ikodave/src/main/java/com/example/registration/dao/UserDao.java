package com.example.registration.dao;

import com.example.registration.model.User;

public interface UserDao {
    void addUser(User user);

    boolean authenticate(User user);

    void deleteUser(String username);

    boolean userExists(String username);
}
