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
    void testDifficultUser() throws SQLException {
        Difficulty difficulty = dao.getProblemDifficulty(1);
        assertEquals(1, difficulty.getId());
    }
    @Test
    void testFilterDifficulty() throws SQLException {
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(3, "HARD"));
        List<Problem> problems = dao.getProblemsByFilter(filter);
        int k = 0;
        for(Problem problem : problems){
            if(Objects.equals(problem.getTitle(), "cool artem")){
                k=1;
            }
        }
        assertEquals(1, k);
        assertEquals(1, problems.size());
    }
}
