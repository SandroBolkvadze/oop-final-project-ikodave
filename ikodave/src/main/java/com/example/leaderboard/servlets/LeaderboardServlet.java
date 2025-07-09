package com.example.leaderboard.servlets;

import com.example.leaderboard.dao.LeaderboardDAO;
import com.example.leaderboard.dto.UserWithRank;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.util.AttributeConstants.GSON_KEY;
import static com.example.util.AttributeConstants.LEADERBOARD_DAO_KEY;

public class LeaderboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);
        LeaderboardDAO leaderboardDAO = (LeaderboardDAO) request.getSession().getAttribute(LEADERBOARD_DAO_KEY);

        // Now returns List<UserWithRank>
        List<UserWithRank> leaderboard = leaderboardDAO.getUsersByRank();

        String json = gson.toJson(leaderboard);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
