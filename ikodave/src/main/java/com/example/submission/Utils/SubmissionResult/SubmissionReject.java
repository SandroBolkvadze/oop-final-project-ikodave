package com.example.submission.Utils.SubmissionResult;

public abstract class SubmissionReject extends SubmissionResult {

    @Override
    boolean isAccept() {
        return false;
    }

}
