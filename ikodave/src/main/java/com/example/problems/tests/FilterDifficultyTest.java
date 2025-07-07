package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.Filters.FilterDifficulty;

import org.apache.commons.dbcp2.BasicDataSource;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterDifficultyTest {

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
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
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
                INSERT INTO problem_difficulty (id, difficulty) VALUES
                    (1, 'Easy'),
                    (2, 'MEDIUM'),
                    (3, 'HARD');
            """);
        }
    }

    @BeforeEach
    void insertProblemData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description,
                                      difficulty_id, create_date, time_limit)
                VALUES
                    (1, 'Ants',         '...', 1, '2025-06-20', 20000),
                    (2, 'xorificator',  '...', 2, '2025-06-21', 20000),
                    (3, 'cool artem',   '...', 3, '2025-06-22', 20000),
                    (4, 'nice',         '...', 2, '2025-06-23', 20000),
                    (5, 'hard',         '...', 2, '2025-06-24', 20000);
            """);
        }
    }

    @AfterEach
    void clearProblemData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM problems");
        }
    }

    @Test
    void testDifficultUser() throws SQLException {
        Difficulty difficulty = dao.getProblemDifficulty(1);
        assertEquals(1, difficulty.getId());
        assertEquals("Easy", difficulty.getDifficulty());
    }

    @Test
    void testFilterDifficultyHard() throws SQLException {
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(3, "HARD"));
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(1, problems.size());
        assertEquals("cool artem", problems.get(0).getTitle());
    }

    @Test
    void testFilterDifficultyMedium() throws SQLException {
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(2, "MEDIUM"));
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(3, problems.size());
        boolean found2 = problems.stream().anyMatch(p -> p.getTitle().equals("xorificator"));
        boolean found4 = problems.stream().anyMatch(p -> p.getTitle().equals("nice"));
        boolean found5 = problems.stream().anyMatch(p -> p.getTitle().equals("hard"));

        assertTrue(found2);
        assertTrue(found4);
        assertTrue(found5);
    }

    @Test
    void testFilterDifficultyEasy() throws SQLException {
        FilterDifficulty filter = new FilterDifficulty(new Difficulty(1, "Easy"));
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(1, problems.size());
        assertEquals("Ants", problems.get(0).getTitle());
    }
}
