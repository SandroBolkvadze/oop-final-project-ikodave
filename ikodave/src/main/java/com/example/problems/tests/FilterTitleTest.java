package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterStatus;
import com.example.problems.Filters.FilterTitle;
import com.example.problems.utils.ToSQL;
import com.example.registration.model.User;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.util.Constants.*;
import static com.example.util.Constants.DATABASE_USER;

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
        FilterTitle ft = new FilterTitle(dataSource,"ic");
        List<Problem> problems = new ArrayList<>();
        problems =dao.getProblemsByFilter(ft);
        assertEquals(2,problems.size());
        int k2 = 0, k4 = 0;
        for (Problem problem : problems) {
            if(problem.getId() == 2) k2++;
            if(problem.getId() == 4) k4++;
        }
        assertEquals(1, k2);
        assertEquals(1, k4);
    }
}
