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
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, create_date, time_limit) VALUES
                    (1, 'Ants',        '...', 1, '2025-06-20', 20000),
                    (2, 'xorificator', '...', 2, '2025-06-21', 20000),
                    (3, 'cool artem',  '...', 3, '2025-06-22', 20000),
                    (4, 'nice',        '...', 2, '2025-06-23', 20000),
                    (5, 'hard',        '...', 2, '2025-06-24', 20000);
            """);
        }
    }

    @Test
    void testTitleFilter() throws Exception {
        FilterTitle filter = new FilterTitle("ic");
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(2, problems.size());

        boolean foundXorificator = problems.stream().anyMatch(p -> p.getId() == 2);
        boolean foundNice = problems.stream().anyMatch(p -> p.getId() == 4);

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
