package com.example.problems.DTO;

import java.time.LocalDate;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;
    private LocalDate createDate;
    private int timeLimit;
    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId, LocalDate createDate, int timeLimit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficultyId = difficultyId;
        this.createDate = createDate;
        this.timeLimit = timeLimit;
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

    public void setDifficultyId(int difficultyId) {this.difficultyId = difficultyId;}

    public LocalDate getCreateDate() {return createDate;}

    public void setCreateDate(LocalDate createDate) {this.createDate = createDate;}

    public int getTimeLimit() {return timeLimit;}

    public void setTimeLimit(int timeLimit) {this.timeLimit = timeLimit;}
}
