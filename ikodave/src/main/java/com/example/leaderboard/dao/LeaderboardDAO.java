package com.example.leaderboard.dao;

import com.example.registration.model.User;

import java.util.List;

public interface LeaderboardDAO {
    public List<User> getUsersByRank();
}