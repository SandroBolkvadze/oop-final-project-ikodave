package com.example.leaderboard.dao;

import com.example.leaderboard.dto.UserWithScore;

import java.util.List;

public interface LeaderboardDAO {
    public List<UserWithScore> getUsersByScore();
}