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
            // Create problem_difficulty for FK reference (if needed)
            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
                );
            """);

            stmt.execute("""
                INSERT INTO problem_difficulty (id, difficulty) VALUES
                    (1, 'Easy'),
                    (2, 'Medium'),
                    (3, 'Hard');
            """);

            // Create problems table with full schema (including all required columns)
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
                    FOREIGN KEY (difficulty_id) REFERENCES problem_difficulty(id)
                );
            """);

            // Create problem_topic table
            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    topic VARCHAR(255) NOT NULL UNIQUE
                );
            """);

            // Create many-to-many join table
            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    problem_id INT NOT NULL,
                    topic_id INT NOT NULL,
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (topic_id) REFERENCES problem_topic(id)
                );
            """);

            // Insert topics
            stmt.execute("""
                INSERT INTO problem_topic (topic) VALUES
                    ('dp'),
                    ('greedy'),
                    ('graphs'),
                    ('trees');
            """);

            // Insert problems with all required fields
            stmt.execute("""
                INSERT INTO problems (problem_title, problem_description, difficulty_id, time_limit, memory_limit, input_spec, output_spec, create_date) VALUES
                    ('Ants', '...', 1, 20000, 1024, 'Input spec', 'Output spec', '2025-06-20 00:00:00'),
                    ('xorificator', '...', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-21 00:00:00'),
                    ('cool artem', '...', 3, 20000, 1024, 'Input spec', 'Output spec', '2025-06-22 00:00:00'),
                    ('nice', '...', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-23 00:00:00'),
                    ('hard', '...', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-24 00:00:00');
            """);

            // Insert problem-topic relationships
            stmt.execute("""
                INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES
                    (1, 1),
                    (1, 2),
                    (1, 4),
                    (2, 3),
                    (3, 1),
                    (3, 3),
                    (4, 1),
                    (4, 2),
                    (5, 2),
                    (5, 3),
                    (5, 4),
                    (5, 1);
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
