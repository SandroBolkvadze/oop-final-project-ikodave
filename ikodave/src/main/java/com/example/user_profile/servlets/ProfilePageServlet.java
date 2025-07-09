package com.example.user_profile.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.user_profile.Response.UsernameBody;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.GSON_KEY;
import static com.example.util.AttributeConstants.USER_DAO_KEY;

public class ProfilePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UsernameBody usernameBody = gson.fromJson(request.getReader(), UsernameBody.class);
        String username = usernameBody.getUsername();
        User user = userDAO.getUserByUsername(username);



    }
}