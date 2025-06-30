package com.example.submission.Utils.TestCaseResult;

import static java.lang.String.format;

public class TestCaseRuntimeError implements TestCaseResult {

    private int testIndex;

    private final String log;

    public TestCaseRuntimeError(int testIndex, String log) {
        this.testIndex = testIndex;
        this.log = log;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String submissionInfo() {
        return format("Test %d Failed.\n %s", testIndex, log);
    }
}
