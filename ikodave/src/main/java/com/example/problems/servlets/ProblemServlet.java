package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Topic;
import com.example.problems.FrontResponse.ProblemSpecificResponse;
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
        User user = (User) request.getSession().getAttribute(USER_KEY);
        ProblemDAO problemDAO = (ProblemDAO) getServletContext().getAttribute(PROBLEM_DAO_KEY);
        TestDAO testDAO = (TestDAO) getServletContext().getAttribute(TEST_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        ProblemTitle problemTitle = gson.fromJson(request.getReader(), ProblemTitle.class);

        String title = problemTitle.getProblemTitle();

        if (user == null) {
            user = new User(2, "x", "y", 1, new java.util.Date());
        }

        System.out.println(title);
        System.out.println(user.getUsername());

        if (title == null || title.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty 'title' field.");
            return;
        }

        Problem problem = problemDAO.getProblemByTitle(title);

        ProblemSpecificResponse problemSpecificResponse = new ProblemSpecificResponse(
                problem.getTitle(),
                problem.getDescription(),
                /*problemDAO.getProblemStatus(problem.getId(), user.getId()).getStatus()*/ "solved",
                problemDAO.getProblemTopics(problem.getId()).stream().map(Topic::getTopic).toList(),
                problemDAO.getProblemDifficulty(problem.getId()).getDifficulty(),
                testDAO.getTestCasesByProblemId(problem.getId()).subList(0, 2),
                problem.getInputSpec(),
                problem.getOutputSpec(),
                problem.getTimeLimit(),
                problem.getMemoryLimit()
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(problemSpecificResponse));
    }

}
