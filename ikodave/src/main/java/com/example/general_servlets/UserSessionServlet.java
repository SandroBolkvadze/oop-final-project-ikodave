package com.example.general_servlets;

import com.example.registration.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.SessionConstants.USER_ID_KEY;

public class UserSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        User user = null;
        if(request.getSession(false) != null){
            user = (User) request.getSession().getAttribute(USER_ID_KEY);
        }
        String json;
        if(user != null){
            json = "{\"loggedIn\":true,\"username\":\"" + user.getUsername() + "\"}";
        } else {
            json = "{\"loggedIn\":false}";
        }
        response.getWriter().write(json);
    }
}
