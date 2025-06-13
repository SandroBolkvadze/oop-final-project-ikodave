package com.example.registration.servlets;

import com.example.registration.dao.UserDao;
import com.example.registration.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.Constants.USER_DAO_KEY;

public class LogInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(username, password);
        UserDao userDao = (UserDao) request.getServletContext().getAttribute(USER_DAO_KEY);

        if (user.getUsername() == null || user.getPassword() == null
                || user.getUsername().isEmpty()
                || user.getPassword().isEmpty()
                || userDao.userExists(user.getUsername())) {
            //TODO handle failed login
        } else {
            userDao.addUser(user);
            //TODO handle successful login
        }
    }
}
