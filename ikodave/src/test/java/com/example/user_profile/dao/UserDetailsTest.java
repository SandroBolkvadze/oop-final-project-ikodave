package com.example.user_profile.dao;

import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserDetailsTest {

    private static BasicDataSource dataSource;
    private MySQLUserDetails userDetails;

    @BeforeClass
    public static void setupDatabase() throws Exception {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Create users table
            stmt.execute("""
                CREATE TABLE users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    rank_id INT DEFAULT 0,
                    register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Create problems table
            stmt.execute("""
                CREATE TABLE problems (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    status_id INT NOT NULL
                )
            """);

            // Create submissions table
            stmt.execute("""
                CREATE TABLE submissions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    problem_id INT NOT NULL,
                    verdict_id INT NOT NULL,
                    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (problem_id) REFERENCES problems(id)
                )
            """);

            // Insert test data
            stmt.execute("INSERT INTO users (username, password) VALUES ('testuser1', 'password1')");
            stmt.execute("INSERT INTO users (username, password) VALUES ('testuser2', 'password2')");

            stmt.execute("INSERT INTO problems (title, status_id) VALUES ('Problem 1', 1)");
            stmt.execute("INSERT INTO problems (title, status_id) VALUES ('Problem 2', 1)");
            stmt.execute("INSERT INTO problems (title, status_id) VALUES ('Problem 3', 2)");

            // Insert submissions with different verdicts
            // User 1 - Verdict 1 (e.g., Accepted) - 2 different problems
            stmt.execute("INSERT INTO submissions (user_id, problem_id, verdict_id) VALUES (1, 1, 1)");
            stmt.execute("INSERT INTO submissions (user_id, problem_id, verdict_id) VALUES (1, 1, 1)"); // Same problem, should count as 1
            stmt.execute("INSERT INTO submissions (user_id, problem_id, verdict_id) VALUES (1, 2, 1)");

            // User 1 - Verdict 2 (e.g., Wrong Answer) - 1 problem
            stmt.execute("INSERT INTO submissions (user_id, problem_id, verdict_id) VALUES (1, 3, 2)");

            // User 2 - Verdict 1 - 1 problem
            stmt.execute("INSERT INTO submissions (user_id, problem_id, verdict_id) VALUES (2, 1, 1)");
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Before
    public void setUp() {
        userDetails = new MySQLUserDetails(dataSource);
    }

    @Test
    public void testGetVerdictProblemsCount_UserWithAcceptedSubmissions() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser1");

        SubmissionVerdict acceptedVerdict = new SubmissionVerdict(1, "Accepted");


        // Act
        int count = userDetails.getVerdictProblemsCount(user, acceptedVerdict);

        // Assert
        assertEquals(2, count); // User 1 has solved 2 different problems with verdict 1
    }

    @Test
    public void testGetVerdictProblemsCount_UserWithWrongAnswerSubmissions() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser1");

        SubmissionVerdict wrongAnswerVerdict = new SubmissionVerdict(2, "Wrong Answer");


        // Act
        int count = userDetails.getVerdictProblemsCount(user, wrongAnswerVerdict);

        // Assert
        assertEquals(1, count); // User 1 has 1 problem with verdict 2
    }

    @Test
    public void testGetVerdictProblemsCount_UserWithNoSubmissionsForVerdict() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser1");

        SubmissionVerdict timeLimitVerdict = new SubmissionVerdict(3, "Time Limit Exceeded");


        // Act
        int count = userDetails.getVerdictProblemsCount(user, timeLimitVerdict);

        // Assert
        assertEquals(0, count); // User 1 has no problems with verdict 3
    }

    @Test
    public void testGetVerdictProblemsCount_DifferentUser() {
        // Arrange
        User user = new User();
        user.setId(2);
        user.setUsername("testuser2");

        SubmissionVerdict acceptedVerdict = new SubmissionVerdict(1, "Accepted");


        // Act
        int count = userDetails.getVerdictProblemsCount(user, acceptedVerdict);

        // Assert
        assertEquals(1, count); // User 2 has solved 1 problem with verdict 1
    }

    @Test
    public void testGetVerdictProblemsCount_NonExistentUser() {
        // Arrange
        User user = new User();
        user.setId(999);
        user.setUsername("nonexistent");

        SubmissionVerdict acceptedVerdict = new SubmissionVerdict(1, "Accepted");


        // Act
        int count = userDetails.getVerdictProblemsCount(user, acceptedVerdict);

        // Assert
        assertEquals(0, count); // Non-existent user has no problems
    }

    @Test(expected = RuntimeException.class)
    public void testGetVerdictProblemsCount_DatabaseError() throws SQLException {
        // Create a BasicDataSource that will fail
        BasicDataSource failingDataSource = new BasicDataSource();
        failingDataSource.setUrl("jdbc:h2:mem:nonexistent");
        failingDataSource.setDriverClassName("org.h2.Driver");

        // Close it to simulate connection failure
        failingDataSource.close();

        MySQLUserDetails failingUserDetails = new MySQLUserDetails(failingDataSource);

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        SubmissionVerdict verdict = new SubmissionVerdict(1, "Accepted");

        // This should throw RuntimeException
        failingUserDetails.getVerdictProblemsCount(user, verdict);
    }

    @Test(expected = NullPointerException.class)
    public void testGetVerdictProblemsCount_NullUser() {
        // Arrange
        SubmissionVerdict verdict = new SubmissionVerdict(1, "Accepted");

        // Act & Assert - should throw NPE
        userDetails.getVerdictProblemsCount(null, verdict);
    }

    @Test(expected = NullPointerException.class)
    public void testGetVerdictProblemsCount_NullVerdict() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        // Act & Assert - should throw NPE
        userDetails.getVerdictProblemsCount(user, null);
    }
}

