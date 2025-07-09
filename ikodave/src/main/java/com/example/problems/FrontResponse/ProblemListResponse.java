package com.example.problems.FrontResponse;

public class ProblemListResponse {

    private String title;
    private int difficultyId;
    private String difficultyName;
    private String status;

    public ProblemListResponse() {

    }

    public ProblemListResponse(String title, int difficultyId, String difficultyName, String status) {
        this.title = title;
        this.difficultyId = difficultyId;
        this.difficultyName = difficultyName;
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public String getStatus() {
        return status;
    }
}
