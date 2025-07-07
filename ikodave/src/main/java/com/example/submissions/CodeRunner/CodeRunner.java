package com.example.submissions.CodeRunner;


import com.example.submissions.DTO.TestCase;
import com.example.submissions.Utils.Language.CodeLang;
import com.example.submissions.Utils.SubmissionResult.SubmissionResult;

import java.io.IOException;
import java.util.List;

public interface CodeRunner {

    SubmissionResult testCodeMultipleTests(CodeLang codeLang,
                                           String solutionCode,
                                           long executionTimeoutMillis,
                                           List<TestCase> testCases) throws IOException, InterruptedException;

}
