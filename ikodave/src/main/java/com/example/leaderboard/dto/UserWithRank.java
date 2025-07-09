package com.example.leaderboard.dto;

import com.example.registration.model.User;

public class UserWithRank {
    private User user;
    private int rank;

    public UserWithRank(User user, int rank) {
        this.user = user;
        this.rank = rank;
    }

    public User getUser() {
        return user;
    }

    public int getRank() {
        return rank;
    }
}
