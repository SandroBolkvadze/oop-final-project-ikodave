package com.example.user_profile.Response;

public class UserStats {

    private int easySolvedProblemsCount;
    private int easyNotSolvedProblemsCount;

    private int mediumSolvedProblemsCount;
    private int mediumNotSolvedProblemsCount;

    private int hardSolvedProblemsCount;
    private int hardNotSolvedProblemsCount;

    private int submissionsTotalCount;

    private int notAcceptedSubmissionsCount;

    private int acceptedProblemsCountCurrentWeek;

    private int userScore;


    public UserStats() {

    }

    public UserStats(int easySolvedProblemsCount, int easyNotSolvedProblemsCount, int mediumSolvedProblemsCount, int mediumNotSolvedProblemsCount, int hardSolvedProblemsCount, int hardNotSolvedProblemsCount, int submissionsTotalCount, int notAcceptedSubmissionsCount, int acceptedProblemsCountCurrentWeek, int userScore) {
        this.easySolvedProblemsCount = easySolvedProblemsCount;
        this.easyNotSolvedProblemsCount = easyNotSolvedProblemsCount;
        this.mediumSolvedProblemsCount = mediumSolvedProblemsCount;
        this.mediumNotSolvedProblemsCount = mediumNotSolvedProblemsCount;
        this.hardSolvedProblemsCount = hardSolvedProblemsCount;
        this.hardNotSolvedProblemsCount = hardNotSolvedProblemsCount;
        this.submissionsTotalCount = submissionsTotalCount;
        this.notAcceptedSubmissionsCount = notAcceptedSubmissionsCount;
        this.acceptedProblemsCountCurrentWeek = acceptedProblemsCountCurrentWeek;
        this.userScore = userScore;
    }

    public int getEasySolvedProblemsCount() {
        return easySolvedProblemsCount;
    }

    public void setEasySolvedProblemsCount(int easySolvedProblemsCount) {
        this.easySolvedProblemsCount = easySolvedProblemsCount;
    }

    public int getEasyNotSolvedProblemsCount() {
        return easyNotSolvedProblemsCount;
    }

    public void setEasyNotSolvedProblemsCount(int easyNotSolvedProblemsCount) {
        this.easyNotSolvedProblemsCount = easyNotSolvedProblemsCount;
    }

    public int getMediumSolvedProblemsCount() {
        return mediumSolvedProblemsCount;
    }

    public void setMediumSolvedProblemsCount(int mediumSolvedProblemsCount) {
        this.mediumSolvedProblemsCount = mediumSolvedProblemsCount;
    }

    public int getMediumNotSolvedProblemsCount() {
        return mediumNotSolvedProblemsCount;
    }

    public void setMediumNotSolvedProblemsCount(int mediumNotSolvedProblemsCount) {
        this.mediumNotSolvedProblemsCount = mediumNotSolvedProblemsCount;
    }

    public int getHardSolvedProblemsCount() {
        return hardSolvedProblemsCount;
    }

    public void setHardSolvedProblemsCount(int hardSolvedProblemsCount) {
        this.hardSolvedProblemsCount = hardSolvedProblemsCount;
    }

    public int getHardNotSolvedProblemsCount() {
        return hardNotSolvedProblemsCount;
    }

    public void setHardNotSolvedProblemsCount(int hardNotSolvedProblemsCount) {
        this.hardNotSolvedProblemsCount = hardNotSolvedProblemsCount;
    }

    public int getSubmissionsTotalCount() {
        return submissionsTotalCount;
    }

    public void setSubmissionsTotalCount(int submissionsTotalCount) {
        this.submissionsTotalCount = submissionsTotalCount;
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