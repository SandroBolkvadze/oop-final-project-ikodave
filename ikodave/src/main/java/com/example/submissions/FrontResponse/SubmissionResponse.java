package com.example.submissions.FrontResponse;

import java.sql.Timestamp;

public class SubmissionResponse {
    private final Timestamp submitDate;
    private final String username;
    private final String solutionCode;
    private final String problemTitle;
    private final String codeLanguage;
    private final String verdict;
    private final long time;
    private final long memory;

    public SubmissionResponse(Timestamp date, String username, String solutionCode, String problemTitle, String codeLanguage, String verdict, long time, long memory) {
        this.submitDate = date;
        this.username = username;
        this.solutionCode = solutionCode;
        this.problemTitle = problemTitle;
        this.codeLanguage = codeLanguage;
        this.verdict = verdict;
        this.time = time;
        this.memory = memory;
    }

    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public String getUsername() {
        return username;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public String getVerdict() {
        return verdict;
    }

    public long getTime() {
        return time;
    }

    public long getMemory() {
        return memory;
    }
}
