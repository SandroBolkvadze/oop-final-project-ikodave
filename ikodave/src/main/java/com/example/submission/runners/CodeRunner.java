package com.example.submission.runners;


import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.Language.CodeLanguage;
import com.example.submission.Utils.SubmissionResult.SubmissionResult;

import java.io.IOException;
import java.util.List;

public interface CodeRunner {

    SubmissionResult testCodeMultipleTests(CodeLanguage codeLanguage,
                                           String solutionCode,
                                           long executionTimeoutMillis,
                                           List<TestCase> testCases) throws IOException, InterruptedException;

}
