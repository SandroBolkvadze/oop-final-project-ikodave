package com.example.user_profile.servlets;

import com.example.registration.dao.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.USER_DAO_KEY;

public class UserProfileServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = ((UserDAO) request.getSession().getAttribute(USER_DAO_KEY)).get;
    }
}

