package com.example.leaderboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeaderboardPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{
            request.getRequestDispatcher("static/leaderboard/leaderboard.html")
                    .forward(request, response);
        }catch (ServletException e){
            throw new IOException("Forwarding failed", e);
        }
    }
}
