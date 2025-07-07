package com.example.submissions.Utils.SubmissionResult;

public abstract class SubmissionReject extends SubmissionResult {

    @Override
    public boolean isAccept() {
        return false;
    }

}
