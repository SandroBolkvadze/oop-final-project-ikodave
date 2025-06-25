package com.example.submission.Utils.TestCaseResult;

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

}
