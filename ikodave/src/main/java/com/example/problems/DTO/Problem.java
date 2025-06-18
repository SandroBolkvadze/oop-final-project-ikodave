package com.example.problems.DTO;

public class Problem {
    private int id;
    private String title;
    private String description;
    private ProblemDifficulty problemDifficulty;

    public Problem() {

    }

    public Problem(int id, String title, String description, ProblemDifficulty problemDifficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.problemDifficulty = problemDifficulty;
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

    public ProblemDifficulty getProblemDifficulty() {
        return problemDifficulty;
    }

    public void setProblemDifficulty(ProblemDifficulty problemDifficulty) {
        this.problemDifficulty = problemDifficulty;
    }
}
