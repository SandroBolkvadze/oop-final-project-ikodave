package com.example.problems.tests;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.Filters.FilterStatus;
import com.example.registration.model.User;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterStatusTest {

    private static BasicDataSource dataSource;
    private static ProblemDAO dao;
    private static User user1, user2, user3, user4;

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
                    username VARCHAR(255),
                    password VARCHAR(255),
                    rank_id INT,
                    register_date DATE
                );
            """);

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
                CREATE TABLE submissions (
                    id INT PRIMARY KEY,
                    user_id INT,
                    problem_id INT,
                    verdict_id INT,
                    solution_code CLOB,
                    submit_date DATE,
                    log CLOB
                );
            """);

            stmt.execute("""
                INSERT INTO users (id, username, password, rank_id, register_date) VALUES
                    (1, 'bolkvadze',    'sbolk23', 1, '2025-06-10'),
                    (2, 'losaberidze', 'slosa23', 1, '2025-06-11'),
                    (3, 'endeladze',   'kende23', 2, '2025-06-12'),
                    (4, 'metreveli',   'nmetr23', 3, '2025-06-13');
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

    @BeforeEach
    void setupTestData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM submissions");

            stmt.execute("""
                INSERT INTO submissions (id, user_id, problem_id, verdict_id, solution_code, submit_date, log) VALUES
                    (1, 1, 1, 1, '+',    '2025-06-25', ''),
                    (2, 1, 2, 2, '++',   '2025-06-25', ''),
                    (3, 1, 3, 2, '+++',  '2025-06-25', ''),
                    (4, 1, 5, 1, '+.',   '2025-06-25', ''),
                    (5, 2, 1, 2, '-.',   '2025-06-25', ''),
                    (6, 2, 2, 1, '...',  '2025-06-25', ''),
                    (7, 2, 3, 2, '...',  '2025-06-25', ''),
                    (8, 3, 1, 1, '...',  '2025-06-25', ''),
                    (9, 3, 2, 1, '...',  '2025-06-25', ''),
                    (10,3, 4, 2, '...',  '2025-06-25', ''),
                    (11,4, 1, 1, '...',  '2025-06-25', ''),
                    (12,4, 5, 1, '...',  '2025-06-25', ''),
                    (13,4, 4, 2, '...',  '2025-06-25', ''),
                    (14,3, 3, 3, '...',  '2025-06-25', '');
            """);
        }

        user1 = new User("bolkvadze", "sbolk23"); user1.setId(1);
        user2 = new User("losaberidze", "slosa23"); user2.setId(2);
        user3 = new User("endeladze", "kende23"); user3.setId(3);
        user4 = new User("metreveli", "nmetr23"); user4.setId(4);
    }

    @Test
    void testStatusFilterAccepted() throws Exception {
        FilterStatus filter = new FilterStatus(user1, "SOLVED");
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(2, problems.size());
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 1));
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 5));
    }

    @Test
    void testProblemTODO() throws Exception {
        FilterStatus filter = new FilterStatus(user1, "TODO");
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(1, problems.size());
        assertEquals(4, problems.get(0).getId());
    }
}
