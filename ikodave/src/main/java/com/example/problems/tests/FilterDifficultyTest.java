package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterDifficulty;
import com.example.problems.utils.ToSQL;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.example.util.DBConnectionConstants.*;

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
    public void testDifficultUser() throws SQLException {
        setup();
        //System.out.println(ToSQL.toProblemDifficultySQL());
        Difficulty difficulty = dao.getProblemDifficulty(1);
        //String s = ToSQL.toProblemDifficultySQL();
        assertEquals(1, difficulty.getId());
    }
    public void testFilterDifficulty() throws SQLException {
        setup();
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
