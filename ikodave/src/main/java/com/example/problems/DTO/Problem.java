package com.example.problems.DTO;

import java.sql.Timestamp;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;
    private Timestamp createDate;
    private long timeLimit;

    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId, Timestamp createDate, long timeLimit) {

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

}
