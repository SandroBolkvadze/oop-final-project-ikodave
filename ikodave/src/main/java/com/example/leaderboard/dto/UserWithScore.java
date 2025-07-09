package com.example.leaderboard.dto;

import com.example.registration.model.User;

public class UserWithScore {
    private User user;
    private int score;

    public UserWithScore(User user, int score) {
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }
}
