package com.example.problems.servlets;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.*;
import com.example.problems.utils.FilterCriteria;
import com.example.registration.model.User;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.util.AttributeConstants.*;
import static com.example.util.SessionConstants.USER_KEY;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProblemsListServletTest {

    private ProblemsListServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ProblemDAO problemDAO;

    private User user;
    private Gson gson;
    private StringWriter responseWriter;
    private PrintWriter printWriter;

    @Before
    public void setUp() throws Exception {
        servlet = new ProblemsListServlet() {
            @Override
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        gson = new Gson();
        responseWriter = new StringWriter();
        printWriter = new PrintWriter(responseWriter);

        user = new User();
        user.setId(1);
        user.setUsername("testuser");

        when(servletContext.getAttribute(GSON_KEY)).thenReturn(gson);
        when(servletContext.getAttribute(PROBLEM_DAO_KEY)).thenReturn(problemDAO);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void testDoPost_WithAllFilters() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(user);

        FilterCriteria criteria = new FilterCriteria("Two Sum", "Solved",
                "Easy", Arrays.asList("Array", "Hash Table"));
        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getDifficultyId("Easy")).thenReturn(1);
        when(problemDAO.getStatusId("Solved")).thenReturn(1);
        when(problemDAO.getTopicId("Array")).thenReturn(1);
        when(problemDAO.getTopicId("Hash Table")).thenReturn(2);

        Problem problem1 = new Problem();
        problem1.setId(1);
        problem1.setTitle("Two Sum");

        List<Problem> expectedProblems = Arrays.asList(problem1);
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        ArgumentCaptor<FilterAnd> filterCaptor = ArgumentCaptor.forClass(FilterAnd.class);
        verify(problemDAO).getProblemsByFilter(filterCaptor.capture());

        FilterAnd capturedFilter = filterCaptor.getValue();
        assertNotNull(capturedFilter);

        String responseJson = responseWriter.toString();
        List<Problem> actualProblems = Arrays.asList(gson.fromJson(responseJson, Problem[].class));
        assertEquals(expectedProblems.size(), actualProblems.size());
        assertEquals(expectedProblems.getFirst().getId(), actualProblems.getFirst().getId());
    }

    @Test
    public void testDoPost_WithNoFilters() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("", "",
                "", Collections.emptyList());
        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        List<Problem> expectedProblems = Collections.emptyList();
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));
        verify(problemDAO, never()).getDifficultyId(anyString());
        verify(problemDAO, never()).getStatusId(anyString());
        verify(problemDAO, never()).getTopicId(anyString());

        String responseJson = responseWriter.toString();
        assertEquals("[]", responseJson);
    }

    @Test
    public void testDoPost_WithTitleFilterOnly() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("Binary Search", "",
                "", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        Problem problem = new Problem();
        problem.setId(2);
        problem.setTitle("Binary Search");

        List<Problem> expectedProblems = Arrays.asList(problem);
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        ArgumentCaptor<FilterAnd> filterCaptor = ArgumentCaptor.forClass(FilterAnd.class);
        verify(problemDAO).getProblemsByFilter(filterCaptor.capture());

        String responseJson = responseWriter.toString();
        assertTrue(responseJson.contains("Binary Search"));
    }

    @Test
    public void testDoPost_WithDifficultyFilterOnly() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("", "",
                "Medium", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getDifficultyId("Medium")).thenReturn(2);

        List<Problem> expectedProblems = Arrays.asList(new Problem(), new Problem());
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(problemDAO).getDifficultyId("Medium");
        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));

        String responseJson = responseWriter.toString();
        List<Problem> actualProblems = Arrays.asList(gson.fromJson(responseJson, Problem[].class));
        assertEquals(2, actualProblems.size());
    }

    @Test
    public void testDoPost_StatusFilterWithoutUser() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("", "Solved",
                "", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        List<Problem> expectedProblems = Collections.emptyList();
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(problemDAO, never()).getStatusId(anyString());
        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));
    }

    @Test
    public void testDoPost_StatusFilterWithUser() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(user);

        FilterCriteria criteria = new FilterCriteria("", "Attempted",
                "", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getStatusId("Attempted")).thenReturn(2);

        List<Problem> expectedProblems = Arrays.asList(new Problem());
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(problemDAO).getStatusId("Attempted");
        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));
    }

    @Test
    public void testDoPost_WithMultipleTopics() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("", "",
                "", Arrays.asList("Dynamic Programming", "Graph", "Tree"));

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getTopicId("Dynamic Programming")).thenReturn(1);
        when(problemDAO.getTopicId("Graph")).thenReturn(2);
        when(problemDAO.getTopicId("Tree")).thenReturn(3);

        List<Problem> expectedProblems = Arrays.asList(new Problem(), new Problem(), new Problem());
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        servlet.doPost(request, response);

        verify(problemDAO).getTopicId("Dynamic Programming");
        verify(problemDAO).getTopicId("Graph");
        verify(problemDAO).getTopicId("Tree");
        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));

        String responseJson = responseWriter.toString();
        List<Problem> actualProblems = Arrays.asList(gson.fromJson(responseJson, Problem[].class));
        assertEquals(3, actualProblems.size());
    }

    @Test
    public void testDoPost_WithIOException() throws Exception {
        when(request.getReader()).thenThrow(new IOException("Read error"));

        assertThrows(IOException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testDoPost_WithNullFilterCriteria() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(user);

        String jsonRequest = "invalid json";
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        assertThrows(Exception.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testDoPost_VerifyResponseHeaders() throws Exception {
        when(servletContext.getAttribute(USER_KEY)).thenReturn(null);

        FilterCriteria criteria = new FilterCriteria("", "",
                "", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(Collections.emptyList());

        servlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    public void testDoPost_WithDifferentUser() throws Exception {
        User anotherUser = new User();
        anotherUser.setId(2);
        anotherUser.setUsername("anotheruser");

        when(servletContext.getAttribute(USER_KEY)).thenReturn(anotherUser);

        FilterCriteria criteria = new FilterCriteria("", "Solved",
                "", Collections.emptyList());

        String jsonRequest = gson.toJson(criteria);
        BufferedReader reader = new BufferedReader(new StringReader(jsonRequest));
        when(request.getReader()).thenReturn(reader);

        when(problemDAO.getStatusId("Solved")).thenReturn(1);

        List<Problem> expectedProblems = Arrays.asList(new Problem());
        when(problemDAO.getProblemsByFilter(any(FilterAnd.class))).thenReturn(expectedProblems);

        //sadgac tosqlstatementshi ari areuli %d da %s
        servlet.doPost(request, response);

        verify(problemDAO).getStatusId("Solved");
        verify(problemDAO).getProblemsByFilter(any(FilterAnd.class));
    }
}