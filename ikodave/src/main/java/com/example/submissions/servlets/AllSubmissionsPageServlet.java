package com.example.submissions.servlets;

import com.example.registration.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.SessionConstants.USER_KEY;

public class AllSubmissionsPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(USER_KEY);

        System.out.println("user: " + user);
        if (user == null) {
            response.sendRedirect("/signin");
        }
        else {
            request.getRequestDispatcher("/static/all_submissions/all_submissions.html").forward(request, response);
        }
    }

}