package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.Filters.FilterTitle;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTitleTest {

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
            // Create problem_difficulty table for FK reference
            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
                );
            """);

            // Insert difficulties
            stmt.execute("""
                INSERT INTO problem_difficulty (id, difficulty) VALUES
                    (1, 'Easy'),
                    (2, 'Medium'),
                    (3, 'Hard');
            """);

            // Create problems table with full schema
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

            // Insert sample data matching new schema
            stmt.execute("""
                INSERT INTO problems (problem_title, problem_description, difficulty_id, time_limit, memory_limit, input_spec, output_spec, create_date) VALUES
                    ('Ants', 'Ants description', 1, 20000, 1024, 'Input spec', 'Output spec', '2025-06-20 00:00:00'),
                    ('xorificator', 'Xorificator description', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-21 00:00:00'),
                    ('cool artem', 'Cool Artem description', 3, 20000, 1024, 'Input spec', 'Output spec', '2025-06-22 00:00:00'),
                    ('nice', 'Nice description', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-23 00:00:00'),
                    ('hard', 'Hard description', 2, 20000, 1024, 'Input spec', 'Output spec', '2025-06-24 00:00:00');
            """);
        }
    }

    @Test
    void testTitleFilter() throws Exception {
        FilterTitle filter = new FilterTitle("ic");
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(2, problems.size());

        boolean foundXorificator = problems.stream().anyMatch(p -> p.getTitle().equals("xorificator"));
        boolean foundNice = problems.stream().anyMatch(p -> p.getTitle().equals("nice"));

        assertTrue(foundXorificator);
        assertTrue(foundNice);
    }

    @Test
    void testTitleFilterNoProblems() throws Exception {
        FilterTitle filter = new FilterTitle("ick");
        List<Problem> problems = dao.getProblemsByFilter(filter);
        assertEquals(0, problems.size());
    }
}
