package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.util.SessionConstants;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.*;

public class SignInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String rawPassword = request.getParameter("password");

        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute(USER_DAO_KEY);
        User user = userDao.getUserByUsername(username); // fetch user with stored hash

        boolean authOK = false;
        if (user != null) {
            try {
                authOK = BCrypt.checkpw(rawPassword, user.getPassword());
            } catch (IllegalArgumentException e) {
                // invalid hash format → treat as auth failure
            }
        }

        if (!authOK) {
            response.sendRedirect(request.getContextPath() + "/authentication/signin.html?error=1");
        } else {
            request.getSession().setAttribute(SessionConstants.USER_ID_KEY, user);
            response.sendRedirect(request.getContextPath() + "/problems/html/problems.html");
        }
    }
}