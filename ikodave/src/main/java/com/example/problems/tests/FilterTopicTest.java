package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterTopic;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.Constants.*;
import static com.example.util.Constants.DATABASE_USER;

public class FilterTopicTest extends TestCase {

    static List<Problem> allProblems;
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
    public void testTopicUserId() throws SQLException {
        setup();
        List<Topic> topics = dao.getProblemTopics(1);
        boolean problemFound1 = false, problemFound2 = false, problemFound4 = false;
        for (Topic topic : topics) {
            if (topic.getId() == 1) {
                problemFound1 = true;
            }
            if (topic.getId() == 2) {
                problemFound2 = true;
            }
            if (topic.getId() == 4) {
                problemFound4 = true;
            }
        }
        assertTrue(problemFound1);
        assertTrue(problemFound2);
        assertTrue(problemFound4);
        assertEquals(3,topics.size());
    }
    public void testFilterTopic() throws SQLException {
        setup();
        List<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(1, "dp"));
        FilterTopic filter = new FilterTopic(dataSource, topics);

        List<Problem> problems = dao.getProblemsByFilter(filter);
        int cnt = 0;
        for (Problem problem : problems) {
            if(problem.getId() == 1)cnt++;
            if(problem.getId() == 3)cnt++;
            if(problem.getId() == 4)cnt++;
            if(problem.getId() == 5)cnt++;
        }
        assertEquals(4, problems.size());
        assertEquals(4, cnt);
    }
    public void testFilterMultipleTopics() throws SQLException {
        setup();
        List<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(3, "graphs"));
        FilterTopic filter = new FilterTopic(dataSource, topics);
        List<Problem> problems = dao.getProblemsByFilter(filter);
        boolean problemFound3 = false, problemFound4 = false;
        for (Problem problem : problems) {
            if(problem.getId() == 3){
                problemFound3 = true;
            }
            if(problem.getId() == 5){
                problemFound4 = true;
            }
        }
        assertEquals(2, problems.size());
        assertTrue(problemFound3);
        assertTrue(problemFound4);
    }
}
