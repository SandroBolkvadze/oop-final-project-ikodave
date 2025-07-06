package com.example.submissions.Utils.TestCaseResult;

public class TestCaseAccept extends TestCaseResult {
    private final long time;
    private final long memory;
    private final String log;

    public TestCaseAccept(long time, long memory, String log) {
        this.time = time;
        this.memory = memory;
        this.log = log;
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
        return "Test Passed";
    }

    @Override
    public String getLog() {
        return log;
    }
}
