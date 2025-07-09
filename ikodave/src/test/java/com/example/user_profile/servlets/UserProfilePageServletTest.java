package com.example.user_profile.servlets;

import com.example.registration.servlets.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserProfilePageServletTest {

    private UserProfilePageServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new UserProfilePageServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void whenUserNotRegistered_thenForwardToSignIn() throws IOException, ServletException {
        try (MockedStatic<Helper> helperMock = mockStatic(Helper.class)) {

            helperMock.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);

            when(request.getRequestDispatcher("/authentication/signin.html")).thenReturn(dispatcher);

            servlet.doGet(request, response);

            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void whenUserRegistered_thenNoForward() throws IOException, ServletException {
        try (MockedStatic<Helper> helperMock = mockStatic(Helper.class)) {
            helperMock.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(true);

            servlet.doGet(request, response);

            verify(request, never()).getRequestDispatcher(anyString());
        }
    }

    @Test
    void whenForwardThrowsServletException_thenIOExceptionIsThrown() throws ServletException {
        try (MockedStatic<Helper> helperMock = mockStatic(Helper.class)) {
            helperMock.when(() -> Helper.redirectProfileIfRegistered(request, response)).thenReturn(false);
            when(request.getRequestDispatcher("/authentication/signin.html")).thenReturn(dispatcher);
            doThrow(new ServletException("Mock forward fail")).when(dispatcher).forward(request, response);

            assertThrows(IOException.class, () -> servlet.doGet(request, response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
