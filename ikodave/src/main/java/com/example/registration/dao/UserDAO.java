package com.example.registration.dao;

import com.example.registration.model.User;

public interface UserDAO {

    User getUser(int userId);

    void addUser(User user);

    boolean authenticate(User user);

    void deleteUser(String username);

    boolean userExists(String username);

    User getUserByUsername(String username);
}
