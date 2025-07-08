package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static com.example.util.AttributeConstants.USER_DAO_KEY;
import static com.example.util.SessionConstants.USER_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServletTest {

    private Registration servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext servletContext;
    private UserDAO userDao;

    @BeforeEach
    void setUp() {
        servlet = new Registration();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        servletContext = mock(ServletContext.class);
        userDao = mock(UserDAO.class);

        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
        when(servletContext.getAttribute(USER_DAO_KEY)).thenReturn(userDao);
    }

    @Test
    void testDoPost_successfulRegistration() throws Exception {
        User testUser = new User("newuser", "plainpass");

        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            helper.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);
            helper.when(() -> Helper.parseJsonBody(eq(request), eq(User.class))).thenReturn(testUser);

            when(userDao.userExists("newuser")).thenReturn(false);
            when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

            servlet.doPost(request, response);

            verify(userDao).addUser(argThat(u ->
                    u.getUsername().equals("newuser") &&
                            !u.getPassword().equals("plainpass") // ensure password is hashed
            ));

            verify(session).setAttribute(eq(USER_KEY), any(User.class));

            helper.verify(() -> Helper.sendJsonResponse(eq(response), argThat((Map<String, String> map) ->
                    "ok".equals(map.get("status"))
            )));
        }
    }

    @Test
    void testDoGet_ForwardToRegistrationForm() throws Exception {
        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            helper.when(() -> Helper.redirectProfileIfRegistered(req, res)).thenReturn(false);

            when(req.getRequestDispatcher("/authentication/registration.html")).thenReturn(dispatcher);

            Registration servlet = new Registration();
            servlet.doGet(req, res);

            // Should forward to registration.html
            verify(dispatcher).forward(req, res);
        }
    }

    @Test
    void testDoGet_UserAlreadyRegistered_Redirects() throws Exception {
        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);

            helper.when(() -> Helper.redirectProfileIfRegistered(req, res)).thenReturn(true);

            Registration servlet = new Registration();
            servlet.doGet(req, res);

            // Nothing forwarded
            verify(req, never()).getRequestDispatcher(anyString());
        }
    }

    @Test
    void testDoGet_ThrowsIOExceptionOnServletException() throws Exception {
        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            helper.when(() -> Helper.redirectProfileIfRegistered(req, res)).thenReturn(false);
            when(req.getRequestDispatcher("/authentication/registration.html")).thenReturn(dispatcher);

            doThrow(new ServletException("simulated")).when(dispatcher).forward(req, res);

            Registration servlet = new Registration();

            IOException ex = assertThrows(IOException.class, () -> servlet.doGet(req, res));
            assertTrue(ex.getMessage().contains("Forwarding failed"));
        }
    }

    @Test
    void testDoPost_userAlreadyExists_sendsExistsStatus() throws Exception {
        User existingUser = new User("existingUser", "irrelevant");

        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            helper.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);
            helper.when(() -> Helper.parseJsonBody(eq(request), eq(User.class))).thenReturn(existingUser);

            when(userDao.userExists("existingUser")).thenReturn(true);

            servlet.doPost(request, response);

            verify(userDao, never()).addUser(any());

            helper.verify(() -> Helper.sendJsonResponse(eq(response), argThat((Map<String, String> map) ->
                    "exists".equals(map.get("status"))
            )));
        }
    }

    @Test
    void testDoGet_throwsIOExceptionWhenServletExceptionOccurs() throws Exception {
        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            helper.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);

            RequestDispatcher dispatcher = mock(RequestDispatcher.class);
            when(request.getRequestDispatcher("/authentication/registration.html")).thenReturn(dispatcher);
            doThrow(new ServletException("Simulated failure")).when(dispatcher).forward(request, response);

            Registration servlet = new Registration();
            IOException thrown = assertThrows(IOException.class, () -> servlet.doGet(request, response));

            assertEquals("Forwarding failed", thrown.getMessage());
            assertTrue(thrown.getCause() instanceof ServletException);
        }
    }

    @Test
    void testDoGet_throwsIOExceptionWhenForwardFails() throws Exception {
        try (MockedStatic<Helper> helper = mockStatic(Helper.class)) {
            helper.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);
            when(request.getRequestDispatcher("/authentication/registration.html")).thenReturn(dispatcher);
            doThrow(new ServletException("forward failed")).when(dispatcher).forward(request, response);

            Registration servlet = new Registration();

            IOException thrown = assertThrows(IOException.class, () -> servlet.doGet(request, response));

            assertEquals("Forwarding failed", thrown.getMessage());
            assertTrue(thrown.getCause() instanceof ServletException);
            assertEquals("forward failed", thrown.getCause().getMessage());
        }
    }


}
