package com.example.submissions.Utils.SubmissionResult;

public abstract class SubmissionResult {

    abstract public boolean isAccept();

    abstract public long getTime();

    abstract public long getMemory();

    abstract public String getVerdict();

    abstract public String getLog();

}
