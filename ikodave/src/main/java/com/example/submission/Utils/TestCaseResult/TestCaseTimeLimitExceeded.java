package com.example.submission.Utils.TestCaseResult;

public class TestCaseTimeLimitExceeded implements TestCaseResult {

    private final int testIndex;

    private final long time;

    private String log;

    public TestCaseTimeLimitExceeded(int testIndex, long time, String log) {
        this.testIndex = testIndex;
        this.time = time;
        this.log = log;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
