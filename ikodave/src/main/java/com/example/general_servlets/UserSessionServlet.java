package com.example.general_servlets;

import com.example.admin.dao.RoleDAO;
import com.example.admin.dto.Role;
import com.example.registration.model.User;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.example.util.AttributeConstants.GSON_KEY;
import static com.example.util.AttributeConstants.ROLE_DAO_KEY;
import static com.example.util.SessionConstants.USER_KEY;

public class UserSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = null;
        if (request.getSession(false) != null){
            user = (User) request.getSession().getAttribute(USER_KEY);
        }
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        HashMap<String, String> map = new HashMap<>();
        if(user != null){
            map.put("loggedIn", "true");
            map.put("username", user.getUsername());
            RoleDAO roleDAO = (RoleDAO) getServletContext().getAttribute(ROLE_DAO_KEY);
            Role role = roleDAO.getRoleById(user.getRoleId());
            map.put("role", role.getRole());
        }else{
            map.put("loggedIn", "false");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(map));
    }
}
