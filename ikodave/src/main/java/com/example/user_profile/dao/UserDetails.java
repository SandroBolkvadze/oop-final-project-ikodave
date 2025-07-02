package com.example.user_profile.dao;

import com.example.problems.DTO.Status;
import com.example.registration.model.User;

public interface UserDetails {
    public int getStatusProblemsCount(User user, Status status);

}
