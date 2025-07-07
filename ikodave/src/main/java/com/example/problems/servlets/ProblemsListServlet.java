package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.*;
import com.example.problems.FrontResponse.ProblemResponse;
import com.example.problems.utils.FilterCriteria;
import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.google.gson.Gson;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_ID_KEY;
import static com.example.util.SessionConstants.USER_KEY;

public class ProblemsListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) getServletContext().getAttribute(USER_KEY);
        ProblemDAO problemDAO = (ProblemDAO) getServletContext().getAttribute(PROBLEM_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        FilterCriteria filterCriteria = gson.fromJson(request.getReader(), FilterCriteria.class);

        FilterAnd filterAnd = new FilterAnd();

        String titleString = filterCriteria.getTitle();
        if (!titleString.isEmpty()) {
            filterAnd.addFilter(new FilterTitle(titleString));
        }

        String difficultyString = filterCriteria.getDifficulty();
        if (!difficultyString.isEmpty()) {
            Difficulty difficulty = new Difficulty(problemDAO.getDifficultyId(difficultyString), difficultyString);
            FilterDifficulty filterDifficulty = new FilterDifficulty(difficulty);
            filterAnd.addFilter(filterDifficulty);
        }

        String statusString = filterCriteria.getStatus();
        if (!statusString.isEmpty() && user != null) {
            Status status = new Status(problemDAO.getStatusId(statusString), statusString);
            FilterStatus filterStatus = new FilterStatus(user, status);
            filterAnd.addFilter(filterStatus);
        }

        List<String> topicStrings = filterCriteria.getTopics();
        List<Topic> topics = new ArrayList<>();
        for (String topicString : topicStrings) {
            Topic topic = new Topic(problemDAO.getTopicId(topicString), topicString);
            topics.add(topic);
        }

        if (!topics.isEmpty()) {
            FilterTopic filterTopic = new FilterTopic(topics);
            filterAnd.addFilter(filterTopic);
        }

        System.out.println(filterAnd.toSQLStatement());

        List<Problem> problems = problemDAO.getProblemsByFilter(filterAnd);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(problems));
    }

}
