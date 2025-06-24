package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.util.SessionConstants;
import com.password4j.types.Bcrypt;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.*;

public class Registration extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, hashedPassword);
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute(USER_DAO_KEY);

        if (user.getUsername() == null || user.getPassword() == null
                || user.getUsername().isEmpty()
                || user.getPassword().isEmpty()
                || userDao.userExists(user.getUsername())) {
            response.sendRedirect(request.getContextPath() + "/authentication/registration.html?error=1");
        } else {
            userDao.addUser(user);
            request.getSession().setAttribute(SessionConstants.USER_ID_KEY, user);
            response.sendRedirect(request.getContextPath() + "/problems/html/problems.html");
        }
    }
}
