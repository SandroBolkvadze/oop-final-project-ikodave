package com.example.user_profile.servlets;

import com.example.registration.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileStatsServletTest {

    private ProfileStatsServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new ProfileStatsServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        responseWriter = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testSessionExistsAndUserPresent() throws Exception {
        User user = new User();
        user.setUsername("testUser");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        servlet.doGet(request, response);

        assertEquals("\"testUser\"", responseWriter.toString());
    }

    @Test
    void testSessionExistsButUserIsNull() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null); // USER_KEY

        servlet.doGet(request, response);

        assertEquals("null", responseWriter.toString());
    }

    @Test
    void testSessionIsNull() throws Exception {
        when(request.getSession()).thenReturn(null);

        servlet.doGet(request, response);

        assertEquals("null", responseWriter.toString());
    }
}
