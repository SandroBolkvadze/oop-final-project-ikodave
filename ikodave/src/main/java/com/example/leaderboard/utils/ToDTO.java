package com.example.leaderboard.utils;

import com.example.leaderboard.dto.UserWithScore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDTO {
    public static UserWithScore toUserWithScore(ResultSet rs) {
        try {
            UserWithScore userWithScore = new UserWithScore();
            userWithScore.setUsername(rs.getString("USER"));
            userWithScore.setScore(rs.getInt("SCORE"));
            return userWithScore;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
