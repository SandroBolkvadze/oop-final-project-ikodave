package com.example.submission.runners;


import com.example.submission.DTO.TestCase;
import java.io.IOException;
import java.util.List;

public interface CodeRunner {

    boolean testCodeMultipleTests(String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException;

}
