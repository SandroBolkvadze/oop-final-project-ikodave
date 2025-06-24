package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.utils.ProblemTitle;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.PROBLEM_DAO_KEY;

public class ProblemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ProblemDAO problemDao = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        ProblemTitle problemTitle;
        try {
            problemTitle = gson.fromJson(request.getReader(), ProblemTitle.class);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format.");
            return;
        }
        String title = problemTitle.getTitle();
        if (title == null || title.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty 'title' field.");
            return;
        }
        Problem problem = problemDao.getProblemByTitle(title);
        response.getWriter().write(gson.toJson(problem));
    }

}
