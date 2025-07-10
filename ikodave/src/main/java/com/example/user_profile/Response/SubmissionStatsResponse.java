package com.example.user_profile.Response;

public class SubmissionStatsResponse {

    private int easyAcceptedProblemsCount;
    private int easyNotAcceptedProblemsCount;

    private int mediumAcceptedProblemsCount;
    private int mediumNotAcceptedProblemsCount;

    private int hardAcceptedProblemsCount;
    private int hardNotAcceptedProblemsCount;

    private int notAcceptedSubmissionsCount;

    private int acceptedProblemsCountCurrentWeek;

    private int userScore;

    public SubmissionStatsResponse(int easyAcceptedProblemsCount, int easyNotAcceptedProblemsCount, int mediumAcceptedProblemsCount, int mediumNotAcceptedProblemsCount, int hardAcceptedProblemsCount, int hardNotAcceptedProblemsCount, int notAcceptedSubmissionsCount, int acceptedProblemsCountCurrentWeek, int userScore) {
        this.easyAcceptedProblemsCount = easyAcceptedProblemsCount;
        this.easyNotAcceptedProblemsCount = easyNotAcceptedProblemsCount;
        this.mediumAcceptedProblemsCount = mediumAcceptedProblemsCount;
        this.mediumNotAcceptedProblemsCount = mediumNotAcceptedProblemsCount;
        this.hardAcceptedProblemsCount = hardAcceptedProblemsCount;
        this.hardNotAcceptedProblemsCount = hardNotAcceptedProblemsCount;
        this.notAcceptedSubmissionsCount = notAcceptedSubmissionsCount;
        this.acceptedProblemsCountCurrentWeek = acceptedProblemsCountCurrentWeek;
        this.userScore = userScore;
    }

    public int getEasyAcceptedProblemsCount() {
        return easyAcceptedProblemsCount;
    }

    public void setEasyAcceptedProblemsCount(int easyAcceptedProblemsCount) {
        this.easyAcceptedProblemsCount = easyAcceptedProblemsCount;
    }

    public int getEasyNotAcceptedProblemsCount() {
        return easyNotAcceptedProblemsCount;
    }

    public void setEasyNotAcceptedProblemsCount(int easyNotAcceptedProblemsCount) {
        this.easyNotAcceptedProblemsCount = easyNotAcceptedProblemsCount;
    }

    public int getMediumAcceptedProblemsCount() {
        return mediumAcceptedProblemsCount;
    }

    public void setMediumAcceptedProblemsCount(int mediumAcceptedProblemsCount) {
        this.mediumAcceptedProblemsCount = mediumAcceptedProblemsCount;
    }

    public int getMediumNotAcceptedProblemsCount() {
        return mediumNotAcceptedProblemsCount;
    }

    public void setMediumNotAcceptedProblemsCount(int mediumNotAcceptedProblemsCount) {
        this.mediumNotAcceptedProblemsCount = mediumNotAcceptedProblemsCount;
    }

    public int getHardAcceptedProblemsCount() {
        return hardAcceptedProblemsCount;
    }

    public void setHardAcceptedProblemsCount(int hardAcceptedProblemsCount) {
        this.hardAcceptedProblemsCount = hardAcceptedProblemsCount;
    }

    public int getHardNotAcceptedProblemsCount() {
        return hardNotAcceptedProblemsCount;
    }

    public void setHardNotAcceptedProblemsCount(int hardNotAcceptedProblemsCount) {
        this.hardNotAcceptedProblemsCount = hardNotAcceptedProblemsCount;
    }

    public int getNotAcceptedSubmissionsCount() {
        return notAcceptedSubmissionsCount;
    }

    public void setNotAcceptedSubmissionsCount(int notAcceptedSubmissionsCount) {
        this.notAcceptedSubmissionsCount = notAcceptedSubmissionsCount;
    }

    public int getAcceptedProblemsCountCurrentWeek() {
        return acceptedProblemsCountCurrentWeek;
    }

    public void setAcceptedProblemsCountCurrentWeek(int acceptedProblemsCountCurrentWeek) {
        this.acceptedProblemsCountCurrentWeek = acceptedProblemsCountCurrentWeek;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}