package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.problems.Filters.FilterAnd;
import com.example.problems.Filters.FilterDifficulty;
import com.example.problems.Filters.FilterTopic;
import com.example.problems.utils.ToSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.DBConnectionConstants.*;

public class FilterAndTest {

    static BasicDataSource dataSource;
    static FilterAnd filterand;
    static ProblemDAO dao;
    @BeforeEach
    void setup() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        filterand = new FilterAnd();
        dao = new SQLProblemDAO(dataSource);
    }
    @Test
    void testFilterAnd() throws SQLException {
        List<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(2, "greedy"));
        FilterTopic filter1 = new FilterTopic(topics);
        FilterDifficulty filter2 = new FilterDifficulty(new Difficulty(2, "MEDIUM"));
        filterand.addFilter(filter1);
        filterand.addFilter(filter2);
        List<Problem> problems=dao.getProblemsByFilter(filterand);
        int f = 0,  s = 0;
        for(int i = 0; i < problems.size(); ++i){
            int p = problems.get(i).getId();
            if(p == 4) f = 1;
            if(p == 5) s = 1;
        }
        assertEquals("nice", problems.get(0).getTitle());
        assertEquals("hard", problems.get(1).getTitle());
        assertEquals(2, f + s);
    }
}
