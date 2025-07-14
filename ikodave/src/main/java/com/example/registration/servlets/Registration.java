package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.registration.utils.UserRegistrationInput;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.registration.servlets.Authentication.*;
import static com.example.util.AttributeConstants.GSON_KEY;
import static com.example.util.AttributeConstants.USER_DAO_KEY;
import static com.example.util.SessionConstants.USER_KEY;

public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!redirectProfileIfSignedIn(request, response)) {
            request.getRequestDispatcher("/static/authentication/registration.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (redirectProfileIfSignedIn(request, response)) return;

        UserDAO userDao = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UserRegistrationInput userRegistrationInput = gson.fromJson(request.getReader(), UserRegistrationInput.class);
        String mail = userRegistrationInput.getMail();
        String username = userRegistrationInput.getUsername();
        String password = userRegistrationInput.getPassword();

        Map<String, String> signInResult = new HashMap<>();
        if (userDao.userExists(username)) {
            signInResult.put("status", "exists");
        }
        else {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(username, hashedPassword);
            userDao.addUser(newUser);
            request.getSession().setAttribute(USER_KEY, userDao.getUserByUsername(newUser.getUsername()));
            signInResult.put("status", "ok");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(signInResult));
    }
}
