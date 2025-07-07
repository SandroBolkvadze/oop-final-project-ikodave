package com.example.submissions.Utils.SubmissionResult;

public class SubmissionAccept extends SubmissionResult {

    private final long time;
    private final long memory;

    public SubmissionAccept(long time, long memory) {
        this.time = time;
        this.memory = memory;
    }

    @Override
    public boolean isAccept() {
        return true;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public long getMemory() {
        return memory;
    }

    @Override
    public String getVerdict() {
        return "Accepted";
    }

    @Override
    public String getLog() {
        return "All Tests Passed";
    }
}
