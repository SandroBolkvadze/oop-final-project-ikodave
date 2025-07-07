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

class FilterAndTest {

    private static BasicDataSource dataSource;
    private static ProblemDAO dao;

    private FilterAnd filterAnd;
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

            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT PRIMARY KEY,
                    topic VARCHAR(50) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE problems (
                    id INT PRIMARY KEY,
                    problem_title VARCHAR(255) NOT NULL,
                    problem_description CLOB,
                    difficulty_id INT NOT NULL,
                    create_date DATE,
                    time_limit BIGINT,
                    CONSTRAINT fk_diff FOREIGN KEY (difficulty_id)
                        REFERENCES problem_difficulty(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    id INT PRIMARY KEY,
                    problem_id INT NOT NULL,
                    topic_id INT NOT NULL,
                    CONSTRAINT fk_problem FOREIGN KEY (problem_id)
                        REFERENCES problems(id),
                    CONSTRAINT fk_topic FOREIGN KEY (topic_id)
                        REFERENCES problem_topic(id)
                );
            """);

            stmt.execute("""
                INSERT INTO problem_difficulty (id, difficulty) VALUES
                   (1, 'Easy'),
                   (2, 'MEDIUM'),
                   (3, 'HARD');
            """);

            stmt.execute("""
                INSERT INTO problem_topic (id, topic) VALUES
                   (1, 'dp'),
                   (2, 'greedy'),
                   (3, 'graphs'),
                   (4, 'trees');
            """);
        }
    }

    @BeforeEach
    void seedProblemsAndFilters() throws Exception {

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description,
                                      difficulty_id, create_date, time_limit)
                VALUES
                   (1,'Ants',        'there are n nodes',      1,'2025-06-20',20000),
                   (2,'xorificator', 'xors of x y',            2,'2025-06-21',20000),
                   (3,'cool artem',  'there are n elements',   3,'2025-06-22',20000),
                   (4,'nice',        'there are n strings',    2,'2025-06-23',20000),
                   (5,'hard',        'there are n graph',      2,'2025-06-24',20000);
            """);

            stmt.execute("""
                INSERT INTO problem_many_to_many_topic (id, problem_id, topic_id) VALUES
                   (1, 1, 1),  (2, 1, 2),  (3, 1, 4),
                   (4, 2, 3),
                   (5, 3, 1),  (6, 3, 3),
                   (7, 4, 1),  (8, 4, 2),
                   (9, 5, 2),  (10,5, 3), (12,5, 4), (13,5, 1);
            """);
        }
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(2, "greedy"));

        filterTopic      = new FilterTopic(topics);
        filterDifficulty = new FilterDifficulty(new Difficulty(2, "MEDIUM"));
        filterAnd        = new FilterAnd();   // empty for starters
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

        filterAnd.addFilter(filterTopic);
        filterAnd.addFilter(filterDifficulty);

        List<Problem> problems = dao.getProblemsByFilter(filterAnd);

        assertEquals("nice", problems.get(0).getTitle());
        assertEquals("hard", problems.get(1).getTitle());

        assertTrue(problems.stream().anyMatch(p -> p.getId() == 4));
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 5));
    }

    @Test
    void testNoFilter() throws Exception {

        List<Problem> problems = dao.getProblemsByFilter(filterAnd);
        assertEquals(5, problems.size());
    }

    @Test
    void testAllFilter() throws Exception {

        FilterTitle filterTitle = new FilterTitle("ic");

        filterAnd.addFilter(filterTopic);
        filterAnd.addFilter(filterDifficulty);
        filterAnd.addFilter(filterTitle);

        List<Problem> problems = dao.getProblemsByFilter(filterAnd);

        assertEquals(1, problems.size());
        assertEquals("nice", problems.get(0).getTitle());
    }

    @Test
    void testFilterNoProblems() throws Exception {

        FilterTitle filterTitle = new FilterTitle("ick");

        filterAnd.addFilter(filterTopic);
        filterAnd.addFilter(filterDifficulty);
        filterAnd.addFilter(filterTitle);

        List<Problem> problems = dao.getProblemsByFilter(filterAnd);

        assertTrue(problems.isEmpty());
    }
}
