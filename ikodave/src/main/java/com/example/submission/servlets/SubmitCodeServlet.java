package com.example.submission.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.registration.model.User;
import com.example.submission.DAO.TestDAO;
import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.Language.CodeLanguage;
import com.example.submission.Utils.SubmissionResult.SubmissionResult;
import com.example.submission.CodeRunner.DockerCodeRunner;
import com.example.submission.Utils.submit.UserSubmission;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.submission.Utils.Language.ToCodeLanguage.toCodeLanguage;
import static com.example.util.AttributeConstants.PROBLEM_DAO_KEY;
import static com.example.util.AttributeConstants.TEST_DAO_KEY;
import static com.example.util.SessionConstants.USER;

public class SubmitCodeServlet extends HttpServlet {

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(10);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(USER);
        TestDAO testDAO = (TestDAO) request.getAttribute(TEST_DAO_KEY);
        ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        DockerCodeRunner dockerCodeRunner = (DockerCodeRunner) request.getAttribute("dockerCodeRunner");

        Gson gson = new Gson();
        UserSubmission userSubmission = gson.fromJson(request.getReader(), UserSubmission.class);

        Problem problem = problemDAO.getProblemByTitle(userSubmission.getProblemTitle());
        CodeLanguage codeLanguage = toCodeLanguage(userSubmission.getSolutionCode());
        String solutionCode = userSubmission.getSolutionCode();
        List<TestCase> testCases = testDAO.getTestCasesByProblemId(problem.getId());

        executor.submit(() -> {
            try {
                SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(codeLanguage, solutionCode, problem.getTimeLimit(), testCases);
                // TODO: update userSubmission

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
