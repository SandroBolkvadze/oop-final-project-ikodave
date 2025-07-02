package com.example.submission.servlets;

import com.example.problems.DTO.Problem;
import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.Language.CodeLanguage;
import com.example.submission.Utils.SubmissionResult.SubmissionResult;
import com.example.submission.runners.DockerCodeRunner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubmitCodeServlet extends HttpServlet {

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(10);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        DockerCodeRunner dockerCodeRunner = (DockerCodeRunner) request.getAttribute("dockerCodeRunner");

        Problem problem = null;
        String solutionCode = null;
        CodeLanguage codeLanguage = null;
        List<TestCase> testcases = new ArrayList<>();

        executor.submit(() -> {
            try {
                SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(codeLanguage, solutionCode, problem.getTimeLimit(), testcases);
                // TODO: update submission
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
