package com.example.user_profile.servlets;

import com.example.registration.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.registration.servlets.Helper.redirectProfileIfRegistered;

public class ProfilePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (!redirectProfileIfRegistered(request, response)) {
                request.getRequestDispatcher("/static/authentication/signin.html")
                        .forward(request, response);
            }
        }catch (ServletException e){
            throw new IOException("Forwarding failed", e);
        }
    }
}
