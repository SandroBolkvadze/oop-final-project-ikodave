package com.example.verification.servlet;

import com.example.registration.DTO.User;
import com.example.registration.dao.UserDAO;
import com.example.verification.Response.Verification;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.util.AttributeConstants.GSON_KEY;
import static com.example.util.AttributeConstants.USER_DAO_KEY;

public class VerificationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        String verificationCode = request.getParameter("code");
        User user = userDAO.updateUserByVerificationCode(verificationCode);

        if (user == null) {
            request.getRequestDispatcher("/static/verification/verification_error.html").forward(request, response);
        }
        else {
            request.getRequestDispatcher("/static/verification/verification_success.html").forward(request, response);
        }

    }

}
