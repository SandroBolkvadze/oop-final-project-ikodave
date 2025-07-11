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
            // Create all necessary tables with correct schema

            stmt.execute("""
                CREATE TABLE user_role (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    role_name VARCHAR(64) NOT NULL UNIQUE
                );
            """);

            stmt.execute("""
                CREATE TABLE users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    role_id INT,
                    username VARCHAR(64) NOT NULL UNIQUE,
                    password VARCHAR(256) NOT NULL,
                    register_date DATETIME NOT NULL,
                    FOREIGN KEY (role_id) REFERENCES user_role(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    difficulty VARCHAR(20) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE problems (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    problem_title VARCHAR(255),
                    problem_description CLOB,
                    difficulty_id INT,
                    create_date DATE,
                    time_limit BIGINT,
                    FOREIGN KEY (difficulty_id) REFERENCES problem_difficulty(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE submission_verdict (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    verdict_name VARCHAR(64) NOT NULL UNIQUE
                );
            """);

            stmt.execute("""
                CREATE TABLE code_language (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    language_name VARCHAR(64) NOT NULL UNIQUE
                );
            """);

            stmt.execute("""
                CREATE TABLE submissions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    problem_id INT,
                    verdict_id INT,
                    solution_code CLOB,
                    code_language_id INT NOT NULL,
                    time BIGINT NOT NULL,
                    memory BIGINT,
                    submit_date DATETIME NOT NULL,
                    log CLOB,
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (verdict_id) REFERENCES submission_verdict(id),
                    FOREIGN KEY (code_language_id) REFERENCES code_language(id)
                );
            """);

            // Insert reference data

            stmt.execute("""
                INSERT INTO user_role (role_name) VALUES ('admin'), ('user');
            """);

            stmt.execute("""
                INSERT INTO problem_difficulty (difficulty) VALUES ('Easy'), ('Medium'), ('Hard');
            """);

            stmt.execute("""
                INSERT INTO submission_verdict (verdict_name) VALUES ('SOLVED'), ('REJECTED'), ('TODO');
            """);

            stmt.execute("""
                INSERT INTO code_language (language_name) VALUES ('Java'), ('Python'), ('C++');
            """);

            // Insert users with role_id = 2 (user)
            stmt.execute("""
                INSERT INTO users (role_id, username, password, register_date) VALUES
                    (2, 'bolkvadze', 'sbolk23', '2025-06-10 00:00:00'),
                    (2, 'losaberidze', 'slosa23', '2025-06-11 00:00:00'),
                    (2, 'endeladze', 'kende23', '2025-06-12 00:00:00'),
                    (2, 'metreveli', 'nmetr23', '2025-06-13 00:00:00');
            """);

            // Insert problems with difficulty_id
            stmt.execute("""
                INSERT INTO problems (problem_title, problem_description, difficulty_id, create_date, time_limit) VALUES
                    ('Ants', '...', 1, '2025-06-20', 20000),
                    ('xorificator', '...', 2, '2025-06-21', 20000),
                    ('cool artem', '...', 3, '2025-06-22', 20000),
                    ('nice', '...', 2, '2025-06-23', 20000),
                    ('hard', '...', 2, '2025-06-24', 20000);
            """);
        }
    }

    @BeforeEach
    void setupTestData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM submissions");

            stmt.execute("""
                INSERT INTO submissions (user_id, problem_id, verdict_id, solution_code, code_language_id, time, memory, submit_date, log) VALUES
                    (1, 1, 1, '+',    1, 0, 0, '2025-06-25 00:00:00', ''),
                    (1, 2, 2, '++',   1, 0, 0, '2025-06-25 00:00:00', ''),
                    (1, 3, 2, '+++',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (1, 5, 1, '+.',   1, 0, 0, '2025-06-25 00:00:00', ''),
                    (2, 1, 2, '-.',   1, 0, 0, '2025-06-25 00:00:00', ''),
                    (2, 2, 1, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (2, 3, 2, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (3, 1, 1, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (3, 2, 1, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (3, 4, 2, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (4, 1, 1, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (4, 5, 1, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (4, 4, 2, '...',  1, 0, 0, '2025-06-25 00:00:00', ''),
                    (3, 3, 3, '...',  1, 0, 0, '2025-06-25 00:00:00', '');
            """);
        }

        user1 = new User("bolkvadze", "sbolk23"); user1.setId(1);
        user2 = new User("losaberidze", "slosa23"); user2.setId(2);
        user3 = new User("endeladze", "kende23"); user3.setId(3);
        user4 = new User("metreveli", "nmetr23"); user4.setId(4);
    }

    @Test
    void testStatusFilterAccepted() throws Exception {
        FilterStatus filter = new FilterStatus(user1, new Status(1, "SOLVED"));
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(2, problems.size());
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 1));
        assertTrue(problems.stream().anyMatch(p -> p.getId() == 5));
    }

    @Test
    void testProblemTODO() throws Exception {
        FilterStatus filter = new FilterStatus(user1, new Status(3, "TODO"));
        List<Problem> problems = dao.getProblemsByFilter(filter);

        assertEquals(1, problems.size());
        assertEquals(4, problems.get(0).getId());
    }
}
