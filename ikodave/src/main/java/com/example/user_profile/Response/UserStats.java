package com.example.user_profile.Response;

import java.sql.Timestamp;
import java.util.List;

public class UserStats {

    private int easySolvedProblemsCount;
    private int easyNotSolvedProblemsCount;

    private int mediumSolvedProblemsCount;
    private int mediumNotSolvedProblemsCount;

    private int hardSolvedProblemsCount;
    private int hardNotSolvedProblemsCount;

    private int submissionsTotalCount;

    private int notAcceptedSubmissionsCount;

    private int acceptedProblemsCountToday;

    private int userRank;

    private List<Timestamp> submissionDates;

    public UserStats() {

    }

    public UserStats(int easySolvedProblemsCount, int easyNotSolvedProblemsCount, int mediumSolvedProblemsCount, int mediumNotSolvedProblemsCount, int hardSolvedProblemsCount, int hardNotSolvedProblemsCount, int submissionsTotalCount, int notAcceptedSubmissionsCount, int acceptedProblemsCountToday, int userRank, List<Timestamp> submissionDates) {
        this.easySolvedProblemsCount = easySolvedProblemsCount;
        this.easyNotSolvedProblemsCount = easyNotSolvedProblemsCount;
        this.mediumSolvedProblemsCount = mediumSolvedProblemsCount;
        this.mediumNotSolvedProblemsCount = mediumNotSolvedProblemsCount;
        this.hardSolvedProblemsCount = hardSolvedProblemsCount;
        this.hardNotSolvedProblemsCount = hardNotSolvedProblemsCount;
        this.submissionsTotalCount = submissionsTotalCount;
        this.notAcceptedSubmissionsCount = notAcceptedSubmissionsCount;
        this.acceptedProblemsCountToday = acceptedProblemsCountToday;
        this.userRank = userRank;
        this.submissionDates = submissionDates;
    }

    public List<Timestamp> getSubmissionDates() {
        return submissionDates;
    }

    public void setSubmissionDates(List<Timestamp> submissionDates) {
        this.submissionDates = submissionDates;
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

    public int getAcceptedProblemsCountToday() {
        return acceptedProblemsCountToday;
    }

    public void setAcceptedProblemsCountToday(int acceptedProblemsCountToday) {
        this.acceptedProblemsCountToday = acceptedProblemsCountToday;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }
}