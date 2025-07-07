package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.Filters.FilterStatus;
import com.example.registration.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import static com.example.util.DBConnectionConstants.*;

public class FilterStatusTest {

    static BasicDataSource dataSource;
    static ProblemDAO dao;
    static User user1, user2, user3, user4;
    @BeforeEach
    void setup() throws SQLException {
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
    @Test
    public void testStatusFilterAccepted() throws SQLException {
        Status status = new Status(1, "ACCEPTED");
        FilterStatus filterStatus = new FilterStatus(user1, status);
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
    @Test
    public void testStatusFilterRejected() throws SQLException {
        Status status = new Status(2, "WRONG");
        FilterStatus filterStatus = new FilterStatus(user2, status);
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
    @Test
    public void testStatusFilterPending() throws SQLException {
        Status status = new Status(3, "PENDING");
        FilterStatus filterStatus = new FilterStatus(user3, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(1 ,problems.size());
        Problem problem = problems.get(0);
        assertEquals(3, problem.getId());
    }
    @Test
    public void testProblemTODO() throws SQLException {
        Status status = new Status(4, "TODO");
        FilterStatus filterStatus = new FilterStatus(user1, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(1, problems.size());
        Problem problem = problems.get(0);
        assertEquals(4, problem.getId());
    }
}
