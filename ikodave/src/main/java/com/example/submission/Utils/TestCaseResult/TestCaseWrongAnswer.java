package com.example.submission.Utils.TestCaseResult;

import static java.lang.String.format;

public class TestCaseWrongAnswer implements TestCaseResult {

    private final int testIndex;

    private final String expected;

    private final String actual;

    private final String log;

    public TestCaseWrongAnswer(int testIndex, String expected, String actual, String log) {
        this.testIndex = testIndex;
        this.expected = expected;
        this.actual = actual;
        this.log = log;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String submissionInfo() {
        return format("Wrong answer on test %d", testIndex);
    }
}
