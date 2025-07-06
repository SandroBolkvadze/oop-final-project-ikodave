package com.example.submissions.Utils.TestCaseResult;

import static java.lang.String.format;

public class TestCaseRuntimeError extends TestCaseResult {

    private final long time;
    private final long memory;
    private final String log;

    public TestCaseRuntimeError(long time, long memory, String log) {
        this.time = time;
        this.memory = memory;
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
        return "Runtime Error";
    }

    @Override
    public String getLog() {
        return log;
    }
}
