package com.example.submissions.Utils.TestCaseResult;

public class TestCaseReject extends TestCaseResult {

    private final long time;
    private final long memory;
    private final String verdict;
    private final String log;

    public TestCaseReject(long time, long memory, String verdict, String log) {
        this.time = time;
        this.memory = memory;
        this.verdict = verdict;
        this.log = log;
    }

    @Override
    public boolean isAccept() {
        return false;
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
        return verdict;
    }

    @Override
    public String getLog() {
        return log;
    }
}
