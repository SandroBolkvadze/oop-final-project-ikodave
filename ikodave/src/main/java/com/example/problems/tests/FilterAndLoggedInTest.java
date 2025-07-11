package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.*;
import com.example.problems.Filters.*;

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

    private FilterAndLoggedIn filterAndLoggedIn;
    private FilterTopic filterTopic;
    private FilterDifficulty filterDifficulty;

    @BeforeAll
    static void setupDatabase() throws Exception {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        dao = new SQLProblemDAO(dataSource);

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            // Difficulty table
            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
                );
            """);

            // Topic table
            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    topic VARCHAR(50) NOT NULL UNIQUE
                );
            """);

            // Problems table with full columns & constraints
            stmt.execute("""
                CREATE TABLE problems (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    problem_title VARCHAR(128) UNIQUE NOT NULL,
                    problem_description TEXT NOT NULL,
                    difficulty_id INT NOT NULL,
                    time_limit BIGINT NOT NULL,
                    memory_limit BIGINT,
                    input_spec TEXT NOT NULL,
                    output_spec TEXT NOT NULL,
                    create_date DATETIME NOT NULL,
                    CONSTRAINT fk_diff FOREIGN KEY (difficulty_id)
                        REFERENCES problem_difficulty(id)
                );
            """);

            // Many-to-many join table for problem-topic relation
            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    problem_id INT NOT NULL,
                    topic_id INT NOT NULL,
                    CONSTRAINT fk_problem FOREIGN KEY (problem_id)
                        REFERENCES problems(id),
                    CONSTRAINT fk_topic FOREIGN KEY (topic_id)
                        REFERENCES problem_topic(id)
                );
            """);

            // Insert difficulties
            stmt.execute("""
                INSERT INTO problem_difficulty (id, difficulty) VALUES
                   (1, 'Easy'),
                   (2, 'MEDIUM'),
                   (3, 'HARD');
            """);

            // Insert topics
            stmt.execute("""
                INSERT INTO problem_topic (topic) VALUES
                   ('dp'),
                   ('greedy'),
                   ('graphs'),
                   ('trees');
            """);
        }
    }

    @BeforeEach
    void seedProblemsAndFilters() throws Exception {

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM problem_many_to_many_topic");
            stmt.execute("DELETE FROM problems");

            // Insert problems with full fields
            stmt.execute("""
                INSERT INTO problems (problem_title, problem_description,
                                      difficulty_id, create_date, time_limit, memory_limit, input_spec, output_spec)
                VALUES
                   ('Ants',        'there are n nodes',      1, '2025-06-20 00:00:00', 20000, 1024, 'Input spec', 'Output spec'),
                   ('xorificator', 'xors of x y',            2, '2025-06-21 00:00:00', 20000, 1024, 'Input spec', 'Output spec'),
                   ('cool artem',  'there are n elements',   3, '2025-06-22 00:00:00', 20000, 1024, 'Input spec', 'Output spec'),
                   ('nice',        'there are n strings',    2, '2025-06-23 00:00:00', 20000, 1024, 'Input spec', 'Output spec'),
                   ('hard',        'there are n graph',      2, '2025-06-24 00:00:00', 20000, 1024, 'Input spec', 'Output spec');
            """);

            // Insert problem-topic relations
            stmt.execute("""
                INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES
                   (1, 1),  (1, 2),  (1, 4),
                   (2, 3),
                   (3, 1),  (3, 3),
                   (4, 1),  (4, 2),
                   (5, 2),  (5, 3), (5, 4), (5, 1);
            """);
        }

        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(2, "greedy"));

        filterTopic      = new FilterTopic(topics);
        filterDifficulty = new FilterDifficulty(new Difficulty(2, "MEDIUM"));
        filterAndLoggedIn = new FilterAndLoggedIn();   // Assuming no-arg constructor is valid
    }

    @AfterEach
    void clearProblemTables() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM problem_many_to_many_topic");
            stmt.execute("DELETE FROM problems");
        }
    }

    @Test
    void testFilterAnd() throws Exception {
        filterAndLoggedIn.addFilter(filterTopic);
        filterAndLoggedIn.addFilter(filterDifficulty);

        List<Problem> problems = dao.getProblemsByFilter(filterAndLoggedIn);

        assertEquals(2, problems.size());
        assertTrue(problems.stream().anyMatch(p -> p.getTitle().equals("nice")));
        assertTrue(problems.stream().anyMatch(p -> p.getTitle().equals("hard")));
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 4));
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 5));
    }

    @Test
    void testNoFilter() throws Exception {
        List<Problem> problems = dao.getProblemsByFilter(filterAndLoggedIn);
        assertEquals(5, problems.size());
    }

    @Test
    void testAllFilter() throws Exception {
        FilterTitle filterTitle = new FilterTitle("ic");

        filterAndLoggedIn.addFilter(filterTopic);
        filterAndLoggedIn.addFilter(filterDifficulty);
        filterAndLoggedIn.addFilter(filterTitle);

        List<Problem> problems = dao.getProblemsByFilter(filterAndLoggedIn);

        assertEquals(1, problems.size());
        assertEquals("nice", problems.get(0).getTitle());
    }

    @Test
    void testFilterNoProblems() throws Exception {
        FilterTitle filterTitle = new FilterTitle("ick");

        filterAndLoggedIn.addFilter(filterTopic);
        filterAndLoggedIn.addFilter(filterDifficulty);
        filterAndLoggedIn.addFilter(filterTitle);

        List<Problem> problems = dao.getProblemsByFilter(filterAndLoggedIn);

        assertTrue(problems.isEmpty());
    }
}
