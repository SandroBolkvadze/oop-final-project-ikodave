package com.example.submission.Utils.SubmissionResult;

public class SubmissionSuccess implements SubmissionResult {
    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String submissionInfo() {
        return "All tests passed";
    }
}
