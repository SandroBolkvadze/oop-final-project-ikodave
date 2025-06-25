package com.example.submission.Utils.TestCaseResult;

public class TestCaseWrongAnswer implements TestCaseResult {

    private int testIndex;

    private String expected;

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
}
