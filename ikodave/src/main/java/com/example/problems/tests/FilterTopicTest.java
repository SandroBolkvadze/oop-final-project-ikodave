package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Topic;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;

import static com.example.util.DBConnectionConstants.*;
import static com.example.util.DBConnectionConstants.DATABASE_USER;

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
    public void testFilterTopic() throws SQLException {
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
}
