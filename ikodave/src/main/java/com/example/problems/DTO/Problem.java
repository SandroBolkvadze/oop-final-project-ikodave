package com.example.problems.DTO;

import java.sql.Timestamp;

public class Problem {
    private int id;
    private String title;
    private String description;
    private int difficultyId;
    private Timestamp createDate;
    private String inputSpec;
    private String outputSpec;
    private long timeLimit;
    private long memoryLimit;

    public Problem() {

    }

    public Problem(int id, String title, String description, int difficultyId, Timestamp createDate, String inputSpec, String outputSpec, long timeLimit, long memoryLimit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficultyId = difficultyId;
        this.createDate = createDate;
        this.inputSpec = inputSpec;
        this.outputSpec = outputSpec;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    public void setInputSpec(String inputSpec) {
        this.inputSpec = inputSpec;
    }

    public void setOutputSpec(String outputSpec) {
        this.outputSpec = outputSpec;
    }

    public String getInputSpec() {
        return inputSpec;
    }

    public String getOutputSpec() {
        return outputSpec;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
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
