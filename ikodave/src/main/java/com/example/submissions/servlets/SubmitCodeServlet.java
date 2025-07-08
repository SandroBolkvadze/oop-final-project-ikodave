package com.example.submissions.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.registration.model.User;
import com.example.submissions.DAO.CodeLanguageDAO;
import com.example.submissions.DAO.SubmissionDAO;
import com.example.submissions.DAO.TestDAO;
import com.example.submissions.DAO.VerdictDAO;
import com.example.submissions.DTO.Submission;
import com.example.submissions.DTO.TestCase;
import com.example.submissions.Utils.Language.CodeLang;
import com.example.submissions.Utils.SubmissionResult.SubmissionResult;
import com.example.submissions.CodeRunner.DockerCodeRunner;
import com.example.submissions.Utils.Submit.UserSubmission;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.submissions.Utils.Language.ToCodeLang.toCodeLang;
import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_KEY;

public class SubmitCodeServlet extends HttpServlet {

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(5);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute(USER_KEY);
//        System.out.println("user: " + user);

        if (user == null) {
            user = new User(2, "x", "y", 1, new java.util.Date());
        }

        TestDAO testDAO = (TestDAO) getServletContext().getAttribute(TEST_DAO_KEY);
        ProblemDAO problemDAO = (ProblemDAO) getServletContext().getAttribute(PROBLEM_DAO_KEY);
        SubmissionDAO submissionDAO = (SubmissionDAO) getServletContext().getAttribute(SUBMISSION_DAO_KEY);
        CodeLanguageDAO codeLanguageDAO = (CodeLanguageDAO) getServletContext().getAttribute(CODE_LANGUAGE_DAO_KEY);
        VerdictDAO verdictDAO = (VerdictDAO) getServletContext().getAttribute(VERDICT_DAO_KEY);

        DockerCodeRunner dockerCodeRunner = (DockerCodeRunner) getServletContext().getAttribute(DOCKER_CODE_RUNNER_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UserSubmission userSubmission = gson.fromJson(request.getReader(), UserSubmission.class);

        Problem problem = problemDAO.getProblemByTitle(userSubmission.getProblemTitle());
        CodeLang codeLang = toCodeLang(userSubmission.getCodeLanguage());
        System.out.println(codeLang);
        String solutionCode = userSubmission.getSolutionCode();
        List<TestCase> testCases = testDAO.getTestCasesByProblemId(problem.getId());


        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblemId(problem.getId());
        submission.setVerdictId(verdictDAO.getVerdictByName("Running").getId());
        submission.setSolutionCode(solutionCode);
        submission.setCodeLanguageId(codeLanguageDAO.getCodeLanguageByName(userSubmission.getCodeLanguage()).getId());
        submission.setTime(0);
        submission.setMemory(0);
        submission.setSubmitDate(new Timestamp(System.currentTimeMillis()));

        final int submissionId = submissionDAO.insertSubmission(submission);


        System.out.println(solutionCode);
        System.out.println(testCases);
        System.out.println(codeLang);
        System.out.println(problem.getTitle());

        executor.submit(() -> {
            try {
                System.out.println("start testing");
                SubmissionResult submissionResult =
                        dockerCodeRunner.testCodeMultipleTests(codeLang, solutionCode, problem.getTimeLimit(), testCases);

                System.out.println("log: " + submissionResult.getLog());

                Submission updatedSubmission =
                        new Submission(submissionId,
                                submission.getUserId(),
                                submission.getProblemId(),
                                verdictDAO.getVerdictByName(submissionResult.getVerdict()).getId(),
                                submission.getSolutionCode(),
                                submission.getCodeLanguageId(),
                                submissionResult.getTime(),
                                submissionResult.getMemory(),
                                submission.getSubmitDate(),
                                submissionResult.getLog()
                        );

                System.out.println(updatedSubmission.getTime());
                submissionDAO.updateSubmission(updatedSubmission);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        String redirectUrl = "/problems" + "/submissions/" +
                        URLEncoder.encode(userSubmission.getProblemTitle(), StandardCharsets.UTF_8);
        response.setContentType("application/json");
        response.getWriter().write("{\"redirect\":\"" + redirectUrl + "\"}");
    }
}
