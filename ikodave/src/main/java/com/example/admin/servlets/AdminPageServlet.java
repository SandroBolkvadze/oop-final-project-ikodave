package com.example.admin.servlets;

import com.example.admin.dao.RoleDAO;
import com.example.admin.dto.Role;
import com.example.registration.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

import static com.example.util.AttributeConstants.ROLE_DAO_KEY;
import static com.example.util.SessionConstants.USER_KEY;

@WebServlet(name = "AdminPageServlet", value = "/AdminPage")
public class AdminPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdminAuthHelper.isAdmin(request, getServletContext())) {
            response.sendRedirect("/home");
            return;
        }

        request.getRequestDispatcher("/static/admin/admin_page.html")
                .forward(request, response);
    }

}
