package com.example.submission.Utils.SubmissionResult;

public abstract class SubmissionResult {

    abstract boolean isAccept();

    abstract long getTime();

    abstract long getMemory();

    abstract String getVerdict();

    abstract String getLog();

}
