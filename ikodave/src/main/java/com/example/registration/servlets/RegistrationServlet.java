package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.DTO.User;
import com.example.registration.Responce.UserRegistrationInput;
import com.example.registration.mail.MailSender;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

import static com.example.registration.servlets.Authentication.*;
import static com.example.constants.AttributeConstants.*;
import static com.example.constants.MailConstants.*;
import static com.example.constants.SessionConstants.USER_KEY;
import static com.example.constants.WebConstants.HOST;
import static java.lang.String.format;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!redirectProfileIfSignedIn(request, response)) {
            request.getRequestDispatcher("/static/authentication/registration.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (redirectProfileIfSignedIn(request, response)) return;

        Executor mailExec = (Executor) getServletContext().getAttribute(MAIL_EXEC_KEY);
        MailSender mailSender = (MailSender) getServletContext().getAttribute(MAIL_SENDER_KEY);
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UserRegistrationInput userRegistrationInput = gson.fromJson(request.getReader(), UserRegistrationInput.class);
        String mail = userRegistrationInput.getMail();
        String username = userRegistrationInput.getUsername();
        String password = userRegistrationInput.getPassword();

        System.out.println(userDAO.usernameExists(username));
        System.out.println(userDAO.verifiedMailExists(mail));


        Map<String, String> signInResult = new HashMap<>();
        if (userDAO.usernameExists(username) || userDAO.verifiedMailExists(mail)) {
            signInResult.put("status", "exists");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(signInResult));
            return;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User();
        newUser.setMail(mail);
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordHash);
        newUser.setVerificationCode(String.valueOf(UUID.randomUUID()));
        newUser.setVerificationCodeExpiry(LocalDateTime.now().plusMinutes(1));

        userDAO.addUser(newUser);
        request.getSession().setAttribute(USER_KEY, userDAO.getUserByUsername(newUser.getUsername()));

        mailExec.execute(() -> {
            String verifyUrl = format("%s/verify?code=%s", HOST, newUser.getVerificationCode());
            String text = TEXT.formatted(verifyUrl);
            String html = HTML.formatted(newUser.getUsername(), verifyUrl, verifyUrl, verifyUrl);
            mailSender.send(newUser.getMail(), SUBJECT, text, html);
        });

        signInResult.put("status", "ok");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(signInResult));
    }
}
