package com.example.submissions.DTO;

public class SubmissionVerdict {

    private final int id;
    private final String verdict;

    public SubmissionVerdict(int id, String verdict) {
        this.id = id;
        this.verdict = verdict;
    }

    public int getId() {
        return id;
    }

    public String getVerdict() {
        return verdict;
    }
}
