package com.example.user_profile.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.SessionConstants.USER_KEY;

public class UserProfileServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = null;
        if(request.getSession(false) != null){
            user = (User) request.getSession().getAttribute(USER_KEY);
        }

        String json;
        if (user != null) {
            json = new Gson().toJson(user.getUsername());
        } else {
            json = new Gson().toJson(null);
        }

        response.getWriter().write(json);
    }
}

