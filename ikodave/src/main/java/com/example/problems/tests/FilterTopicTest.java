package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterTopic;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTopicTest {

    private static BasicDataSource dataSource;
    private static ProblemDAO dao;

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
                CREATE TABLE problems (
                    id INT PRIMARY KEY,
                    problem_title VARCHAR(255),
                    problem_description CLOB,
                    difficulty_id INT,
                    create_date DATE,
                    time_limit BIGINT
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT PRIMARY KEY,
                    topic VARCHAR(255)
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    id INT PRIMARY KEY,
                    problem_id INT,
                    topic_id INT
                );
            """);

            stmt.execute("""
                INSERT INTO problem_topic (id, topic) VALUES
                    (1, 'dp'),
                    (2, 'greedy'),
                    (3, 'graphs'),
                    (4, 'trees');
            """);

            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, create_date, time_limit) VALUES
                    (1, 'Ants', '...', 1, '2025-06-20', 20000),
                    (2, 'xorificator', '...', 2, '2025-06-21', 20000),
                    (3, 'cool artem', '...', 3, '2025-06-22', 20000),
                    (4, 'nice', '...', 2, '2025-06-23', 20000),
                    (5, 'hard', '...', 2, '2025-06-24', 20000);
            """);


            stmt.execute("""
                INSERT INTO problem_many_to_many_topic (id, problem_id, topic_id) VALUES
                    (1, 1, 1),
                    (2, 1, 2),
                    (3, 1, 4),
                    (4, 2, 3),
                    (5, 3, 1),
                    (6, 3, 3),
                    (7, 4, 1),
                    (8, 4, 2),
                    (9, 5, 2),
                    (10, 5, 3),
                    (11, 5, 4),
                    (12, 5, 1);
            """);
        }
    }

    @Test
    void testTopicUserId() throws Exception {

        List<Topic> topics = dao.getProblemTopics(1);

        assertEquals(3, topics.size());

        boolean found1 = topics.stream().anyMatch(t -> t.getId() == 1);
        boolean found2 = topics.stream().anyMatch(t -> t.getId() == 2);
        boolean found4 = topics.stream().anyMatch(t -> t.getId() == 4);

        assertTrue(found1);
        assertTrue(found2);
        assertTrue(found4);
    }

    @Test
    void testFilterTopic() throws Exception {
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(1, "dp"));

        FilterTopic filter = new FilterTopic(topics);

        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(4, problems.size());

        int countMatched = 0;
        for (Problem p : problems) {
            if (p.getId() == 1 || p.getId() == 3 || p.getId() == 4 || p.getId() == 5) {
                countMatched++;
            }
        }
        assertEquals(4, countMatched);
    }

    @Test
    void testFilterMultipleTopics() throws Exception {
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(1, "dp"));
        topics.add(new Topic(3, "graphs"));

        FilterTopic filter = new FilterTopic(topics);

        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(2, problems.size());

        boolean found3 = problems.stream().anyMatch(p -> p.getId() == 3);
        boolean found5 = problems.stream().anyMatch(p -> p.getId() == 5);

        assertTrue(found3);
        assertTrue(found5);
    }
}
