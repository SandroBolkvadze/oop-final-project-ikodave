package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.registration.servlets.Helper.*;
import static com.example.util.AttributeConstants.USER_DAO_KEY;
import static com.example.util.SessionConstants.USER_KEY;

public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (!redirectProfileIfRegistered(request, response)) {
                request.getRequestDispatcher("/static/authentication/registration.html")
                        .forward(request, response);
            }
        } catch (ServletException e) {
            throw new IOException("Forwarding failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (redirectProfileIfRegistered(request, response)) return;

            User inputUser = parseJsonBody(request, User.class);
            String username = inputUser.getUsername();
            String password = inputUser.getPassword();

            UserDAO userDao = (UserDAO) request.getServletContext().getAttribute(USER_DAO_KEY);
            if (userDao == null) throw new IllegalStateException("UserDAO not found in ServletContext.");

            Map<String, String> result = new HashMap<>();
            if (userDao.userExists(username)) {
                result.put("status", "exists");
            } else {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                User newUser = new User(username, hashedPassword);
                userDao.addUser(newUser);
                request.getSession().setAttribute(USER_KEY, newUser);
                result.put("status", "ok");
            }
            System.out.println(userDao.getUserByUsername(username).getRoleId());
            sendJsonResponse(response, result);

        } catch (ServletException e) {
            throw new IOException("Forwarding failed", e);
        }
    }
}
