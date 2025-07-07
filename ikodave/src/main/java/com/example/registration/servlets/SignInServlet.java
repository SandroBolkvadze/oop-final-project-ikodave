package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.registration.utils.UserInput;
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
import static com.example.util.SessionConstants.USER_KEY;

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
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        try {
            if (redirectProfileIfRegistered(request, response)) return;

            UserInput userInput = gson.fromJson(request.getReader(), UserInput.class);
            String username = userInput.getUsername();
            String password = userInput.getPassword();

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
                request.getSession().setAttribute(USER_KEY, user);
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