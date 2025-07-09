package com.example.user_profile.servlets;

import com.example.registration.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.util.SessionConstants.USER_KEY;

public class UserProfilePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(USER_KEY);

        if (user == null) {
            response.sendRedirect("/registration");
        }
        else {
            response.sendRedirect("/profile/" + user.getUsername());
        }
    }
}
