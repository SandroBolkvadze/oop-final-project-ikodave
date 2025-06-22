package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;

import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;

import static com.example.util.DBConnectionConstants.*;
import static com.example.util.DBConnectionConstants.DATABASE_USER;

public class FilterDifficultyTest extends TestCase {

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
    public void testFilterDifficulty() throws SQLException {
        setup();
        //System.out.println(ToSQL.toProblemDifficultySQL());
        Difficulty difficulty = dao.getProblemDifficulty(1);
        //String s = ToSQL.toProblemDifficultySQL();

        assertEquals(1, difficulty.getId());
    }
}
