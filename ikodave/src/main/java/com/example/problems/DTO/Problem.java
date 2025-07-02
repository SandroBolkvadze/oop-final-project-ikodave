package com.example.problems.DTO;

import java.util.Date;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;
    private Date createDate;
    private long timeLimit;

    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId, Date createDate, long timeLimit) {
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public long getTimeLimit() {
        return timeLimit;
    }
}
