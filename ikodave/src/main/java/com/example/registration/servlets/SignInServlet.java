package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.util.SessionConstants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.*;

public class SignInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(username, password);
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute(USER_DAO_KEY);

        if (user.getUsername() == null || user.getPassword() == null
                || user.getUsername().isEmpty()
                || user.getPassword().isEmpty()
                || !userDao.authenticate(user)) {
            response.sendRedirect(request.getContextPath() + "/authentication/signin.html?error=1");
        } else {
            request.getSession().setAttribute(SessionConstants.USER_ID_KEY, user);
            response.sendRedirect(request.getContextPath() + "/problems/html/problems.html");
        }
    }

}
