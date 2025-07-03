package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.util.SessionConstants;
import com.google.gson.Gson;
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
import static com.example.util.AttributeConstants.*;

public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (!redirectProfileIfRegistered(request, response)) {
                request.getRequestDispatcher("/authentication/signin.html")
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
            User user = userDao.getUserByUsername(username);

            boolean authOK = false;
            if (user != null) {
                try {
                    authOK = BCrypt.checkpw(password, user.getPassword());
                } catch (IllegalArgumentException ignored) {}
            }

            Map<String, String> result = new HashMap<>();
            if (authOK) {
                request.getSession().setAttribute(SessionConstants.USER_ID_KEY, user);
                result.put("status", "ok");
            } else {
                result.put("status", "invalid");
            }

            sendJsonResponse(response, result);

        } catch (ServletException e) {
            throw new IOException("Forwarding failed", e);
        }
    }

}