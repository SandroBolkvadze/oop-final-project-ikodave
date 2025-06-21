package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.Filters.Filter;
import com.example.problems.utils.FilterRequest;
import com.google.gson.Gson;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.util.Constants.*;

public class ProblemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProblemDAO problemDAO = (ProblemDAO) request.getAttribute(PROBLEM_DAO_KEY);
        BasicDataSource basicDataSource = (BasicDataSource) request.getAttribute(BASIC_DATASOURCE_KEY);

        Gson gson = new Gson();
        FilterRequest filterRequest = gson.fromJson(request.getReader(), FilterRequest.class);
        Filter filter = filterRequest.toFilter(problemDAO, basicDataSource);
        List<Problem> problems = problemDAO.getProblemsByFilter(filter);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(problems));
    }

}
