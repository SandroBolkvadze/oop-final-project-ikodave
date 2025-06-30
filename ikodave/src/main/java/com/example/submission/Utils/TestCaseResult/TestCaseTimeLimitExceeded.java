package com.example.submission.Utils.TestCaseResult;

import static java.lang.String.format;

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

    @Override
    public String submissionInfo() {
        return format("Time limit exceeded on test %d.\n Execution time: %d ms", testIndex, time);
    }


}
