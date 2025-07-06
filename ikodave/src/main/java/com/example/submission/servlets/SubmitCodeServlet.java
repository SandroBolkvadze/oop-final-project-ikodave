package com.example.submission.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.registration.model.User;
import com.example.submission.DAO.CodeLanguageDAO;
import com.example.submission.DAO.SubmissionDAO;
import com.example.submission.DAO.TestDAO;
import com.example.submission.DAO.VerdictDAO;
import com.example.submission.DTO.Submission;
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
import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.submission.Utils.Language.ToCodeLanguage.toCodeLanguage;
import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER;

public class SubmitCodeServlet extends HttpServlet {

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(10);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(USER);
        TestDAO testDAO = (TestDAO) request.getAttribute(TEST_DAO_KEY);
        ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        SubmissionDAO submissionDAO = (SubmissionDAO) request.getAttribute(SUBMISSION_DAO_KEY);
        CodeLanguageDAO codeLanguageDAO = (CodeLanguageDAO) request.getAttribute(CODE_LANGUAGE_DAO_KEY);
        VerdictDAO verdictDAO = (VerdictDAO) request.getAttribute(VERDICT_DAO_KEY);
        DockerCodeRunner dockerCodeRunner = (DockerCodeRunner) request.getAttribute(DOCKER_CODE_RUNNER_KEY);


        Gson gson = new Gson();
        UserSubmission userSubmission = gson.fromJson(request.getReader(), UserSubmission.class);

        Problem problem = problemDAO.getProblemByTitle(userSubmission.getProblemTitle());
        CodeLanguage codeLanguage = toCodeLanguage(userSubmission.getCodeLanguage());
        String solutionCode = userSubmission.getSolutionCode();
        List<TestCase> testCases = testDAO.getTestCasesByProblemId(problem.getId());

        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblemId(problem.getId());
        submission.setVerdictId(verdictDAO.getVerdictIdByName("Running"));
        submission.setSolutionCode(solutionCode);
        submission.setCodeLanguageId(codeLanguageDAO.getCodeLanguageIdByName(userSubmission.getCodeLanguage()));
        submission.setTime(0);
        submission.setMemory(0);
        submission.setSubmitDate(new Date(System.currentTimeMillis()));

        int submissionId = submissionDAO.insertSubmission(submission);

        executor.submit(() -> {
            try {
                SubmissionResult submissionResult =
                        dockerCodeRunner.testCodeMultipleTests(codeLanguage, solutionCode, problem.getTimeLimit(), testCases);
                // TODO: update userSubmission

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
