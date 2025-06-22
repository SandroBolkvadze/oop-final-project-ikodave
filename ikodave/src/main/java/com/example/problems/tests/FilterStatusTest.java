package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterStatus;
import com.example.problems.utils.ToSQL;
import com.example.registration.model.User;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.example.util.Constants.*;
import static com.example.util.Constants.DATABASE_USER;

public class FilterStatusTest extends TestCase {

    static BasicDataSource dataSource;
    static ProblemDAO dao;
    static User user1, user2, user3, user4;
    static void setup() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dao = new SQLProblemDAO(dataSource);
        user1 = new User("bolkvadze","sbolk23");
        user2 = new User("losaberidze","slosa23");
        user3 = new User("endeladze","kende23");
        user4 = new User("metreveli","nmetr23");
        user1.setId(1);
        user2.setId(2);
        user3.setId(3);
        user4.setId(4);
    }
    public void testStatusFilterAccepted() throws SQLException {
        setup();
        Status status = new Status(1, "ACCEPTED");
        FilterStatus filterStatus = new FilterStatus(dataSource, user1, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(2, problems.size());
        boolean problemFound1 = false, problemFound5 = false;
        for (Problem problem : problems) {
            if (problem.getId() == 1) problemFound1 = true;
            if (problem.getId() == 5) problemFound5 = true;
        }
        assertTrue(problemFound1);
        assertTrue(problemFound5);
    }
    public void testStatusFilterRejected() throws SQLException {
        setup();
        Status status = new Status(2, "WRONG");
        FilterStatus filterStatus = new FilterStatus(dataSource, user2, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(2, problems.size());
        boolean problemFound1 = false, problemFound3 = false;
        for (Problem problem : problems) {
            if (problem.getId() == 1) problemFound1 = true;
            if (problem.getId() == 3) problemFound3 = true;
        }
        assertTrue(problemFound1);
        assertTrue(problemFound3);
    }
    public void testStatusFilterPending() throws SQLException {
        setup();
        Status status = new Status(3, "PENDING");
        FilterStatus filterStatus = new FilterStatus(dataSource, user3, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(1 ,problems.size());
        boolean problemFound = false;
        for (Problem problem : problems) {
            if (problem.getId() == 4) problemFound = true;
        }
        assertTrue(problemFound);
    }

}
