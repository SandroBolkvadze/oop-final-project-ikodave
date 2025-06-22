package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;

import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterStatus;
import com.example.problems.utils.ToSQL;
import com.example.registration.model.User;
import junit.framework.TestCase;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.example.util.Constants.*;
import static com.example.util.Constants.DATABASE_USER;

public class FilterStatusTest extends TestCase {

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
    public void testStatusFilter() throws SQLException {
        setup();
        User user1 = new User("bolkvadze","sbolk23");
        User user2 = new User("losaberidze","slosa23");
        User user3 = new User("endeladze","kende23");
        User user4 = new User("metreveli","nmetr23");
        user1.setId(1);
        user2.setId(2);
        user3.setId(3);
        user4.setId(4);
        Status status = new Status(1, "ACCEPTED");
        FilterStatus filterStatus = new FilterStatus(dataSource, user1, status);
        List<Problem> problems = dao.getProblemsByFilter(filterStatus);
        assertNotNull(problems);
        assertEquals(2,problems.size());
        int k1 = 0, k5 = 0;
        for(int i = 0; i < problems.size(); i++){
            if(problems.get(i).getId() == 1) k1++;
            if(problems.get(i).getId() == 5) k5++;
        }
        assertEquals(1, k1);
        assertEquals(1, k5);
    }
}
