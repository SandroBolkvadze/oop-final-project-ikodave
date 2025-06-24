package com.example.problems.DTO;

import java.util.Date;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;

    private Date createDate;

    private int time_limit;

    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId, Date createDate, int time_limit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficultyId = difficultyId;
        this.createDate = createDate;
        this.time_limit = time_limit;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }
}
