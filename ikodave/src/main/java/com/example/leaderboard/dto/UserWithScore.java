package com.example.leaderboard.dto;


public class UserWithScore {
    private String username;
    private int score;

    public UserWithScore(){}

    public UserWithScore(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUser() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
