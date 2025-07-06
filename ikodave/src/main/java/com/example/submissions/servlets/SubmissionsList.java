package com.example.submissions.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.submissions.DAO.SubmissionDAO;
import com.example.submissions.DTO.Submission;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.example.util.AttributeConstants.PROBLEM_DAO_KEY;
import static com.example.util.AttributeConstants.SUBMISSION_DAO_KEY;


public class SubmissionsList extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        SubmissionDAO submissionDAO = (SubmissionDAO) request.getAttribute(SUBMISSION_DAO_KEY);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        String problemTitle = pathParts[1];

        Problem problem = problemDAO.getProblemByTitle(problemTitle);

        List<Submission> submissions = new ArrayList<>();

    }

}
