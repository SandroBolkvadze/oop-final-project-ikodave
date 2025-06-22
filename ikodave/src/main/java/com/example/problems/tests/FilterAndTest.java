package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.*;
import com.example.problems.utils.ToSQL;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.util.Constants.*;
import static com.example.util.Constants.DATABASE_USER;

public class FilterAndTest extends TestCase {

    static BasicDataSource dataSource;
    static FilterAnd filterand;
    static ProblemDAO dao;
    static List<Topic> topics;
    static FilterTopic filter1;
    static FilterDifficulty filter2;
    static void setup() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        filterand = new FilterAnd(dataSource);
        dao = new SQLProblemDAO(dataSource);
        topics = new ArrayList<Topic>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(2, "greedy"));
        filter1 = new FilterTopic(dataSource, topics);
        filter2 = new FilterDifficulty(dataSource, new Difficulty(2, "MEDIUM"));
    }
    public void testFilterAnd() throws SQLException {
        setup();
        filterand.addFilter(filter1);
        filterand.addFilter(filter2);
        List<Problem> problems = dao.getProblemsByFilter(filterand);
        boolean problem4 = false, problem5 = false;
        for (Problem problem : problems) {
            int p = problem.getId();
            if (p == 4) problem4 = true;
            if (p == 5) problem5 = true;
        }
        assertEquals("nice", problems.get(0).getTitle());
        assertEquals("hard", problems.get(1).getTitle());
        assertTrue(problem4);
        assertTrue(problem5);
    }
    public void testNoFilter() throws SQLException {
        setup();
        List<Problem> problems = dao.getProblemsByFilter(filterand);
        assertEquals(5, problems.size());
    }
    public void testAllFilter() throws SQLException {
        setup();
        FilterTitle filter3 = new FilterTitle(dataSource, "ic");
        filterand.addFilter(filter1);
        filterand.addFilter(filter2);
        filterand.addFilter(filter3);
        List<Problem> problems = dao.getProblemsByFilter(filterand);
        assertEquals(1, problems.size());
        assertEquals("nice", problems.get(0).getTitle());
    }
    public void testFilterNoProblems() throws SQLException {
        setup();
        FilterTitle filter3 = new FilterTitle(dataSource, "ick");
        filterand.addFilter(filter1);
        filterand.addFilter(filter2);
        filterand.addFilter(filter3);
        List<Problem> problems = dao.getProblemsByFilter(filterand);
        assertEquals(0, problems.size());
    }

}
