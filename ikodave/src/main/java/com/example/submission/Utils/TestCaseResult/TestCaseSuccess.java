package com.example.submission.Utils.TestCaseResult;

import static java.lang.String.format;

public class TestCaseSuccess implements TestCaseResult {

    private final int testIndex;

    private final long time;

    public TestCaseSuccess(int testIndex, long time) {
        this.testIndex = testIndex;
        this.time = time;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String submissionInfo() {
        return format("Test %d passed. Execution time: %d", testIndex, time);
    }

}
