package com.example.submissions.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.registration.model.User;
import com.example.submissions.DAO.CodeLanguageDAO;
import com.example.submissions.DAO.SubmissionDAO;
import com.example.submissions.DAO.VerdictDAO;
import com.example.submissions.DTO.Submission;
import com.example.submissions.FrontResponse.SubmissionResponse;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_KEY;


public class SubmissionsListServlet extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(USER_KEY);
        ProblemDAO problemDAO = (ProblemDAO) getServletContext().getAttribute(PROBLEM_DAO_KEY);
        SubmissionDAO submissionDAO = (SubmissionDAO) getServletContext().getAttribute(SUBMISSION_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);
        VerdictDAO verdictDAO = (VerdictDAO) getServletContext().getAttribute(VERDICT_DAO_KEY);
        CodeLanguageDAO codeLanguageDAO = (CodeLanguageDAO) getServletContext().getAttribute(CODE_LANGUAGE_DAO_KEY);

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String problemTitle = pathParts[1];

        Problem problem = problemDAO.getProblemByTitle(problemTitle);
        List<Submission> submissions = submissionDAO.getSubmissionsByOrder(2, problem.getId());

        List<SubmissionResponse> submissionResponses =
                submissions.stream().map((submission) -> {

                    System.out.println(codeLanguageDAO.getCodeLanguageById(submission.getCodeLanguageId()).getLanguage());
                    System.out.println(submission.getSubmitDate());

                    return new SubmissionResponse(
                            submission.getSubmitDate(),
                            /*user.getUsername()*/"sandro",
                            submission.getSolutionCode(),
                            problemTitle,
                            codeLanguageDAO.getCodeLanguageById(submission.getCodeLanguageId()).getLanguage(),
                            verdictDAO.getVerdictById(submission.getVerdictId()).getVerdict(),
                            submission.getTime(),
                            submission.getMemory()
                    );
                }
        ).toList();



        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(submissionResponses));
    }

}
