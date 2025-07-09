package com.example.registration.servlets;

import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.registration.utils.UserInput;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_KEY;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInServletTest {

    private SignInServlet signInServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserDAO userDAO;

    private Gson gson;

    @Before
    public void setUp() throws ServletException {
        signInServlet = new SignInServlet();
        gson = new Gson();

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        signInServlet.init(servletConfig);

        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(GSON_KEY)).thenReturn(gson);
        when(servletContext.getAttribute(USER_DAO_KEY)).thenReturn(userDAO);
    }

    @Test
    public void testDoGet_UserAlreadyLoggedIn() throws IOException, ServletException {
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(session.getAttribute(USER_KEY)).thenReturn(mockUser);
        when(request.getRequestDispatcher("/profile/profile_page.html")).thenReturn(requestDispatcher);

        signInServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request, never()).getRequestDispatcher("/authentication/signin.html");
    }

    @Test
    public void testDoGet_UserNotLoggedIn() throws IOException, ServletException {
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(request.getRequestDispatcher("/authentication/signin.html")).thenReturn(requestDispatcher);

        signInServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("/authentication/signin.html");
    }

    @Test(expected = IOException.class)
    public void testDoGet_ForwardingException() throws IOException, ServletException {
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(request.getRequestDispatcher("/authentication/signin.html")).thenReturn(requestDispatcher);
        doThrow(new ServletException("Forwarding failed")).when(requestDispatcher).forward(request, response);

        signInServlet.doGet(request, response);
    }

    @Test
    public void testDoPost_SuccessfulLogin() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword(BCrypt.hashpw("testpass", BCrypt.gensalt()));

        when(userDAO.getUserByUsername("testuser")).thenReturn(mockUser);

        signInServlet.doPost(request, response);

        verify(response, times(2)).setContentType("application/json");
        verify(response, times(2)).setCharacterEncoding("UTF-8");
        verify(session).setAttribute(USER_KEY, mockUser);

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"ok\""));
    }

    @Test
    public void testDoPost_InvalidPassword() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"wrongpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword(BCrypt.hashpw("testpass", BCrypt.gensalt()));

        when(userDAO.getUserByUsername("testuser")).thenReturn(mockUser);

        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }

    @Test
    public void testDoPost_UserNotFound() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"nonexistent\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(userDAO.getUserByUsername("nonexistent")).thenReturn(null);

        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }

    @Test
    public void testDoPost_UserAlreadyLoggedIn() throws IOException, ServletException {
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(session.getAttribute(USER_KEY)).thenReturn(mockUser);
        when(request.getRequestDispatcher("/profile/profile_page.html")).thenReturn(requestDispatcher);

        signInServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request, never()).getReader();
    }

    @Test
    public void testDoPost_UserDAONotFound() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(servletContext.getAttribute(USER_DAO_KEY)).thenReturn(null);

        signInServlet.doPost(request, response);

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"error\""));
    }

    @Test
    public void testDoPost_InvalidPasswordHash() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("invalid-hash"); // Invalid BCrypt hash

        when(userDAO.getUserByUsername("testuser")).thenReturn(mockUser);

        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }

    @Test(expected = IOException.class)
    public void testDoPost_ServletException() throws IOException, ServletException {
        User mockUser = new User();
        mockUser.setUsername("existing");
        when(session.getAttribute(USER_KEY)).thenReturn(mockUser);
        when(request.getRequestDispatcher("/profile/profile_page.html")).thenReturn(requestDispatcher);
        doThrow(new ServletException("Test exception")).when(requestDispatcher).forward(request, response);

        signInServlet.doPost(request, response);
    }

    @Test(expected = IOException.class)
    public void testDoPost_IOException() throws IOException, ServletException {
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(request.getReader()).thenThrow(new IOException("Test IO exception"));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        signInServlet.doPost(request, response);
    }

    @Test
    public void testDoPost_UnexpectedException() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        when(userDAO.getUserByUsername("testuser")).thenThrow(new RuntimeException("Unexpected error"));

        signInServlet.doPost(request, response);

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"error\""));
    }

    @Test
    public void testDoPost_EmptyCredentials() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"\",\"password\":\"\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(userDAO.getUserByUsername("")).thenReturn(null);


        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }

    @Test
    public void testDoPost_NullCredentials() throws IOException, ServletException {
        String jsonInput = "{}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);
        when(userDAO.getUserByUsername(null)).thenReturn(null);

        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }

    @Test
    public void testDoPost_MalformedJson() throws IOException, ServletException {
        String jsonInput = "{invalid json}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        signInServlet.doPost(request, response);

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"error\""));
    }

    @Test
    public void testDoPost_NullPasswordFromUser() throws IOException, ServletException {
        String jsonInput = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(USER_KEY)).thenReturn(null);

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword(null); // Null password

        when(userDAO.getUserByUsername("testuser")).thenReturn(mockUser);

        signInServlet.doPost(request, response);

        verify(session, never()).setAttribute(eq(USER_KEY), any());

        String jsonOutput = stringWriter.toString();
        assertTrue(jsonOutput.contains("\"status\":\"invalid\""));
    }
}