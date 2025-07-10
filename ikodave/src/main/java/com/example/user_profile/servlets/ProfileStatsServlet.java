package com.example.user_profile.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.user_profile.Response.UsernameBody;
import com.example.user_profile.dao.UserStatsDAO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.*;

public class ProfileStatsServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        UserStatsDAO userStatsDAO = (UserStatsDAO) getServletContext().getAttribute(USER_STATS_DAO);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UsernameBody usernameBody = gson.fromJson(request.getReader(), UsernameBody.class);
        String username = usernameBody.getUsername();
        User user = userDAO.getUserByUsername(username);


    }
}

