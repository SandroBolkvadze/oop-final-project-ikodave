package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterDifficulty;
import com.example.problems.utils.ToSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.example.util.DBConnectionConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilterDifficultyTest {

    static BasicDataSource dataSource;
    static ProblemDAO dao;
    @BeforeEach
    void setup() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dao = new SQLProblemDAO(dataSource);
    }

    @Test
    public void testDifficultUser() throws SQLException {
        setup();
        Difficulty difficulty = dao.getProblemDifficulty(1);
        assertEquals(1, difficulty.getId());
    }
  
    @Test
    public void testFilterDifficultyHard() throws SQLException {
        setup();
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(3, "HARD"));
        List<Problem> problems = dao.getProblemsByFilter(filter);
        boolean isProblemsFound = false;
        for(Problem problem : problems){
            if(Objects.equals(problem.getTitle(), "cool artem")){
                isProblemsFound = true;
            }
        }
        assertTrue(isProblemsFound);
        assertEquals(1, problems.size());
    }
    @Test
    public void testFilterDifficultyMedium() throws SQLException {
        setup();
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(2, "MEDIUM"));
        List<Problem> problems = dao.getProblemsByFilter(filter);
        boolean isProblemsFound2 = false, isProblemsFound4 = false, isProblemsFound5 = false;
        for(Problem problem : problems){
            if(Objects.equals(problem.getTitle(), "xorificator")){
                isProblemsFound2 = true;
            }
            if(Objects.equals(problem.getTitle(), "nice")){
                isProblemsFound4 = true;
            }
            if(Objects.equals(problem.getTitle(), "nice")){
                isProblemsFound5 = true;
            }
        }
        assertTrue(isProblemsFound2);
        assertTrue(isProblemsFound4);
        assertTrue(isProblemsFound5);
        assertEquals(3, problems.size());
    }
    @Test
    public void testFilterDifficultyEasy() throws SQLException {
        setup();
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(1, "Easy"));
        List<Problem> problems = dao.getProblemsByFilter(filter);
        boolean isProblemsFound = false;
        for(Problem problem : problems){
            if(Objects.equals(problem.getTitle(), "Ants")){
                isProblemsFound= true;
            }
        }
        assertTrue(isProblemsFound);
        assertEquals(1, problems.size());
    }
}
