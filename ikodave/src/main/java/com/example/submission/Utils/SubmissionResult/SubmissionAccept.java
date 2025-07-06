package com.example.submission.Utils.SubmissionResult;

public abstract class SubmissionAccept extends SubmissionResult {
    @Override
    boolean isAccept() {
        return true;
    }

    @Override
    String getLog() {
        return "All Tests Passed";
    }
}
