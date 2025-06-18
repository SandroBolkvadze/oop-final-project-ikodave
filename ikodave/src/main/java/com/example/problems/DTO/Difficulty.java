package com.example.problems.DTO;

public class Difficulty {

    public int id;
    private String difficulty;

    public Difficulty(int id, String difficulty) {
        this.id = id;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
