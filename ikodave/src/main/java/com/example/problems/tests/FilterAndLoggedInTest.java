package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.*;
import com.example.problems.Filters.*;
import com.example.registration.model.User;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterAndLoggedInTest {

    private static BasicDataSource dataSource;
    private static ProblemDAO dao;
    private FilterAndLoggedIn filter;
    private User testUser;

    @BeforeAll
    static void setupDatabase() throws Exception {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        dao = new SQLProblemDAO(dataSource);

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE users (
                    id INT PRIMARY KEY,
                    username VARCHAR(64),
                    password VARCHAR(64),
                    register_date DATETIME
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20)
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT PRIMARY KEY,
                    topic VARCHAR(50)
                );
            """);

            stmt.execute("""
                CREATE TABLE problems (
                    id INT PRIMARY KEY,
                    problem_title VARCHAR(255),
                    problem_description CLOB,
                    difficulty_id INT,
                    create_date DATE,
                    time_limit BIGINT,
                    FOREIGN KEY (difficulty_id) REFERENCES problem_difficulty(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    id INT PRIMARY KEY,
                    problem_id INT,
                    topic_id INT,
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (topic_id) REFERENCES problem_topic(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE submission_verdict (
                    id INT PRIMARY KEY,
                    verdict VARCHAR(50)
                );
            """);

            stmt.execute("""
                CREATE TABLE submissions (
                    id INT PRIMARY KEY,
                    user_id INT,
                    problem_id INT,
                    verdict_id INT,
                    solution_code TEXT,
                    code_language_id INT,
                    time BIGINT,
                    memory BIGINT,
                    submit_date DATETIME,
                    log TEXT,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (verdict_id) REFERENCES submission_verdict(id)
                );
            """);

            stmt.execute("""
                INSERT INTO users VALUES (1, 'nick', 'pass', '2025-01-01');

                INSERT INTO problem_difficulty VALUES (1, 'EASY'), (2, 'MEDIUM'), (3, 'HARD');

                INSERT INTO problem_topic VALUES (1, 'dp'), (2, 'greedy'), (3, 'graphs');

                INSERT INTO problems VALUES
                    (1, 'Ants', '...', 1, '2025-06-20', 20000),
                    (2, 'xorificator', '...', 2, '2025-06-21', 20000),
                    (3, 'cool artem', '...', 3, '2025-06-22', 20000);

                INSERT INTO problem_many_to_many_topic VALUES
                    (1, 1, 1),
                    (2, 2, 2),
                    (3, 3, 1),
                    (4, 3, 2);

                INSERT INTO submission_verdict VALUES (1, 'Accepted'), (2, 'Wrong Answer');

                INSERT INTO submissions VALUES
                    (1, 1, 1, 1, '...', 1, 100, 64, '2025-06-25', ''),
                    (2, 1, 3, 2, '...', 1, 200, 128, '2025-06-25', '');
            """);
        }
    }

    @BeforeEach
    void setup() {
        testUser = new User("nickolas", "pass");
        testUser.setId(1);
    }
    @Test
    void testLoggedInSolvedFilter() {
        FilterAndLoggedIn filter = new FilterAndLoggedIn();
        //filter.addFilter(new FilterSolved(testUser));

        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(1, problems.size());
        assertEquals("Ants", problems.get(0).getTitle());
    }

    @Test
    void testFilterAndLoggedInFilter() {
        FilterAndLoggedIn filterAndLoggedIn = new FilterAndLoggedIn();

       // filterAndLoggedIn.addFilter();
        //List<Problem> problems = dao.getProblemsByFilter(filter);

       // assertEquals(1, problems.size());
       // assertEquals("cool artem", problems.get(0).getTitle());
    }
}
