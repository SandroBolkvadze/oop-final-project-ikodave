package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.FrontResponse.ProblemResponse;
import com.example.problems.utils.ProblemTitle;
import com.example.registration.model.User;
import com.example.submissions.DAO.TestDAO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_KEY;

public class ProblemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) getServletContext().getAttribute(USER_KEY);
        ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        TestDAO testDAO = (TestDAO) getServletContext().getAttribute(TEST_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        ProblemTitle problemTitle = gson.fromJson(request.getReader(), ProblemTitle.class);

        String title = problemTitle.getTitle();
        if (title == null || title.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty 'title' field.");
            return;
        }

        Problem problem = problemDAO.getProblemByTitle(title);

        ProblemResponse problemResponse = new ProblemResponse(
                problem.getTitle(),
                problem.getDescription(),
                problemDAO.getProblemStatus(problem.getId(), user.getId()).getStatus(),
                problemDAO.getProblemTopics(problem.getId()),
                problemDAO.getProblemDifficulty(problem.getId()),
                testDAO.getTestCasesByProblemId(problem.getId()).subList(0, 2),
                problem.getTimeLimit(),
                problem.getMemoryLimit()
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(problemResponse));
    }

}
