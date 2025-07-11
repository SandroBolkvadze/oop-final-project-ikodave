package com.example.leaderboard.servlets;

import com.example.leaderboard.dao.SQLLeaderboardDAO;
import com.example.leaderboard.dto.UserWithScore;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaderboardServletTest {

    private LeaderboardServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    private ServletContext context;
    private SQLLeaderboardDAO dao;
    private Gson gson;

    @BeforeEach
    void setUp() throws Exception {
        // Mock dependencies
        ServletConfig config = mock(ServletConfig.class);
        context = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dao = mock(SQLLeaderboardDAO.class);
        gson = new Gson();

        // Mock context attributes
        when(context.getAttribute("gson")).thenReturn(gson);
        when(context.getAttribute("leaderboardDAO")).thenReturn(dao);
        when(config.getServletContext()).thenReturn(context);

        // Mock response writer
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Create servlet and initialize with mocked config
        servlet = new LeaderboardServlet();
        servlet.init(config);
    }

    @Test
    void testDoGet_ReturnsLeaderboardJson() throws Exception {
        List<UserWithScore> dummyUsers = List.of(
                new UserWithScore("Alice", 100),
                new UserWithScore("Bob", 80)
        );
        when(dao.getUsersByScore()).thenReturn(dummyUsers);

        servlet.doGet(request, response);

        String jsonResponse = responseWriter.toString();
        assertTrue(jsonResponse.contains("Alice"));
        assertTrue(jsonResponse.contains("Bob"));
        assertTrue(jsonResponse.contains("100"));
    }

    @Test
    void testDoGet_WithEmptyList_ReturnsEmptyJsonArray() throws Exception {
        when(dao.getUsersByScore()).thenReturn(Collections.emptyList());

        servlet.doGet(request, response);

        String jsonResponse = responseWriter.toString();
        assertEquals("[]", jsonResponse.trim());
    }

    @Test
    void testDoGet_WithNullDAO_ThrowsException() throws Exception {
        when(context.getAttribute("leaderboardDAO")).thenReturn(null); // DAO not in context

        Exception exception = assertThrows(NullPointerException.class, () -> {
            servlet.doGet(request, response);
        });

        assertNotNull(exception.getMessage());
    }


    @Test
    void testDoGet_WithNullGson_ThrowsException() throws Exception {
        when(context.getAttribute("gson")).thenReturn(null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            servlet.doGet(request, response);
        });

        assertNotNull(exception.getMessage());
    }
}
