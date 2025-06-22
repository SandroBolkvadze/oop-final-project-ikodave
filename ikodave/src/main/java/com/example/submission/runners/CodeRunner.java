package com.example.submission.runners;


public interface CodeRunner {

    TestResult runTest(String sourceCode, long timeoutMillis);

}
