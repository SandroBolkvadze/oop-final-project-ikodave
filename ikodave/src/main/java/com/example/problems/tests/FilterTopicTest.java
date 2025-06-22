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

import static com.example.util.DBConnectionConstants.*;

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
        int cnt1 = 0, cnt2 = 0, cnt3 = 0;
        for (Topic topic : topics) {
            if (topic.getId() == 1) {
                cnt1++;
            }
            if (topic.getId() == 2) {
                cnt2++;
            }
            if (topic.getId() == 4) {
                cnt3++;
            }
        }
        if(cnt1 ==1 && cnt2 ==1 && cnt3 ==1){
            assertTrue(true);
        }else{
            assertFalse(false);
        }
    }
    public void testFilterTopic() throws SQLException {
        setup();
        List<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(1, "dp"));
        FilterTopic filter = new FilterTopic(topics);

        List<Problem> problems = dao.getProblemsByFilter(filter);
        int cnt1 = 0, cnt4 = 0, cnt5 = 0;
        for (Problem problem : problems) {
            if(problem.getId() == 1)cnt1++;
            if(problem.getId() == 4)cnt4++;
            if(problem.getId() == 5)cnt5++;
        }
        assertEquals(3, cnt1 + cnt4 + cnt5);
    }
}
