package com.example.registration.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class LogoutServletTest {

    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        logoutServlet = new LogoutServlet();
    }

    @Test
    public void testDoPost_WithSession() throws IOException {
        when(request.getSession(false)).thenReturn(session);

        logoutServlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/home");
    }

    @Test
    public void testDoPost_WithoutSession() throws IOException {
        when(request.getSession(false)).thenReturn(null);

        logoutServlet.doPost(request, response);

        verify(response).sendRedirect("/home");
        verify(request, never()).getSession(true); // ensure no session was created
    }

    @Test(expected = IOException.class)
    public void testDoPost_SendRedirectThrowsIOException() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        doThrow(new IOException("Redirect error")).when(response).sendRedirect("/home");

        logoutServlet.doPost(request, response);
    }
}
