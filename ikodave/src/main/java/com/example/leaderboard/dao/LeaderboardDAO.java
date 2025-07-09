package com.example.leaderboard.dao;

import com.example.leaderboard.dto.UserWithRank;
import com.example.registration.model.User;

import java.util.List;

public interface LeaderboardDAO {
    public List<UserWithRank> getUsersByRank();
}