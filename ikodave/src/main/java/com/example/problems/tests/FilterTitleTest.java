package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.Filters.FilterTitle;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.DBConnectionConstants.*;

public class FilterTitleTest extends TestCase {

    static BasicDataSource dataSource;
    static ProblemDAO dao;
    static void setup() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dao = new SQLProblemDAO(dataSource);
    }
    public void testTitleFilter() throws SQLException {
        setup();
        FilterTitle ft = new FilterTitle("ic");
        List<Problem> problems = new ArrayList<>();
        problems = dao.getProblemsByFilter(ft);
        assertEquals(2,problems.size());
        boolean problemFound2 = false, problemFound4 = false;
        for (Problem problem : problems) {
            if(problem.getId() == 2) problemFound2 = true;
            if(problem.getId() == 4) problemFound4 = true;
        }
        assertTrue(problemFound2);
        assertTrue(problemFound4);
    }
    public void testTitleFilterNoProblems() throws SQLException {
        setup();
        FilterTitle ft = new FilterTitle("ick");
        List<Problem> problems = new ArrayList<>();
        problems = dao.getProblemsByFilter(ft);
        assertEquals(0,problems.size());
    }
}
