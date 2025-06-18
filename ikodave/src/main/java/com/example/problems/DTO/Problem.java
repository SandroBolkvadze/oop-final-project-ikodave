package com.example.problems.DTO;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;

    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficultyId = difficultyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }
}
