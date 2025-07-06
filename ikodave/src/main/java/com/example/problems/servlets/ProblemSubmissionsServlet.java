package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.submission.DAO.SubmissionDAO;
import com.example.submission.DTO.Submission;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.util.AttributeConstants.*;

@WebServlet("/problems/*/submissions")
public class ProblemSubmissionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Extract problem ID from URL path
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Problem ID is required");
                return;
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length < 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid problem ID");
                return;
            }

            int problemId = Integer.parseInt(pathParts[1]);

            // Get DAOs from request attributes (set by your ContextListener)
            ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
            SubmissionDAO submissionDAO = (SubmissionDAO) request.getAttribute(SUBMISSION_DAO_KEY);

            // Check if problem exists
            String problemTitle = problemDAO.getProblemTitle(problemId);
            if (problemTitle == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Problem not found");
                return;
            }

            // Get submissions for this problem
            List<Submission> submissions = submissionDAO.getSubmissionsByProblemId(problemId);

            // Return JSON response
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(submissions));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid problem ID format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            e.printStackTrace();
        }
    }
} 