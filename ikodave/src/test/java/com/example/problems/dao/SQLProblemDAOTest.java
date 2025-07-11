package com.example.problems.dao;

import com.example.problems.DAO.SQLDifficultyDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.problems.Response.ProblemListResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SQLProblemDAOTest {

    private static BasicDataSource dataSource;
    private SQLProblemDAO problemDAO;
    private SQLDifficultyDAO difficultyDAO;

    @Mock
    private Filter mockFilter;

    @BeforeAll
    static void setupDatabase() {
        // Add JVM flag for Mockito with Java 24
        System.setProperty("net.bytebuddy.experimental", "true");

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb_problem;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create tables based on the expected structure
            stmt.execute("""
                CREATE TABLE problems (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    problem_title VARCHAR(255) NOT NULL UNIQUE,
                    problem_description TEXT,
                    difficulty_id INT,
                    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    input_spec TEXT,
                    output_spec TEXT,
                    time_limit BIGINT,
                    memory_limit BIGINT
                )
            """);

            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(255) NOT NULL UNIQUE
                )
            """);

            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT PRIMARY KEY,
                    topic VARCHAR(255) NOT NULL UNIQUE
                )
            """);

            // Create the many-to-many mapping table with the correct name
            stmt.execute("""
                CREATE TABLE problem_many_to_many_topic (
                    problem_id INT,
                    topic_id INT,
                    PRIMARY KEY (problem_id, topic_id),
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (topic_id) REFERENCES problem_topic(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE submission_verdict (
                    id INT PRIMARY KEY,
                    verdict VARCHAR(50) NOT NULL UNIQUE
                )
            """);

            stmt.execute("""
                CREATE TABLE submissions (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    problem_id INT,
                    user_id INT,
                    verdict_id INT,
                    submit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (problem_id) REFERENCES problems(id),
                    FOREIGN KEY (verdict_id) REFERENCES submission_verdict(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE submission_status (
                    id INT PRIMARY KEY,
                    status VARCHAR(50) NOT NULL UNIQUE
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup test database", e);
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        difficultyDAO = new SQLDifficultyDAO(dataSource);
        problemDAO = new SQLProblemDAO(dataSource);

        // Clean data before each test
        cleanDatabase();
        // Insert test data
        insertTestData();
    }

    @AfterEach
    void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    static void closeDatabase() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            // Log error but don't fail
        }
    }

    private void cleanDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM problem_many_to_many_topic");
            stmt.execute("DELETE FROM submissions");
            stmt.execute("DELETE FROM problems");
            stmt.execute("DELETE FROM problem_difficulty");
            stmt.execute("DELETE FROM problem_topic");
            stmt.execute("DELETE FROM submission_verdict");
            stmt.execute("DELETE FROM submission_status");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }

    private void insertTestData() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (1, 'Easy')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (2, 'Medium')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (3, 'Hard')");

            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (1, 'Arrays')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (2, 'Dynamic Programming')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (3, 'Graphs')");

            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, input_spec, output_spec, time_limit, memory_limit) 
                VALUES (1, 'Two Sum', 'Find two numbers that add up to target', 1, 
                        'Array of integers and target', 'Indices of two numbers', 1000, 128)
            """);

            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, input_spec, output_spec, time_limit, memory_limit) 
                VALUES (2, 'Longest Palindromic Substring', 'Find the longest palindrome', 2,
                        'String s', 'Longest palindromic substring', 2000, 256)
            """);

            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, input_spec, output_spec, time_limit, memory_limit) 
                VALUES (3, 'Graph Coloring', 'Color graph with minimum colors', 3,
                        'Graph adjacency matrix', 'Minimum colors needed', 3000, 512)
            """);

            stmt.execute("INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (1, 1)"); // Two Sum - Arrays
            stmt.execute("INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (2, 2)"); // Longest Palindrome - DP
            stmt.execute("INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (3, 3)"); // Graph Coloring - Graphs
            stmt.execute("INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (2, 1)"); // Longest Palindrome - Also Arrays

            stmt.execute("INSERT INTO submission_verdict (id, verdict) VALUES (1, 'Accepted')");
            stmt.execute("INSERT INTO submission_verdict (id, verdict) VALUES (2, 'Wrong Answer')");
            stmt.execute("INSERT INTO submission_verdict (id, verdict) VALUES (3, 'Time Limit Exceeded')");

            stmt.execute("INSERT INTO submission_status (id, status) VALUES (1, 'Accepted')");
            stmt.execute("INSERT INTO submission_status (id, status) VALUES (2, 'Wrong Answer')");
            stmt.execute("INSERT INTO submission_status (id, status) VALUES (3, 'Time Limit Exceeded')");

            stmt.execute("INSERT INTO submissions (problem_id, user_id, verdict_id) VALUES (1, 1, 1)");
            stmt.execute("INSERT INTO submissions (problem_id, user_id, verdict_id) VALUES (1, 1, 2)");
            stmt.execute("INSERT INTO submissions (problem_id, user_id, verdict_id) VALUES (2, 1, 1)");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert test data", e);
        }
    }

    @Test
    void testGetProblemResponsesByFilterLoggedIn() throws SQLException {
        when(mockFilter.toSQLPreparedStatement(any())).thenAnswer(invocation -> {
            Connection conn = invocation.getArgument(0);
            return conn.prepareStatement("""
        SELECT p.*, 1 as difficulty_id, 'Accepted' as status
        FROM problems p
    """);
        });
        List<ProblemListResponse> result = problemDAO.getProblemResponsesByFilterLoggedIn(mockFilter);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Two Sum")));
    }

    @Test
    void testGetProblemsByFilter() throws SQLException {
        when(mockFilter.toSQLPreparedStatement(any(Connection.class)))
                .thenAnswer(invocation -> {
                    Connection conn = invocation.getArgument(0);
                    return conn.prepareStatement("SELECT * FROM problems");
                });

        List<Problem> result = problemDAO.getProblemsByFilter(mockFilter);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Two Sum", result.get(0).getTitle());
    }

    @Test
    void testGetProblemResponsesByFilterLoggedOut() throws SQLException {
        when(mockFilter.toSQLPreparedStatement(any())).thenAnswer(invocation -> {
            Connection conn = invocation.getArgument(0);
            return conn.prepareStatement("""
        SELECT p.*, 1 as difficulty_id
        FROM problems p
    """);
        });

        List<ProblemListResponse> result = problemDAO.getProblemResponsesByFilterLoggedOut(mockFilter);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Two Sum")));
    }

    @Test
    void testGetProblemByTitle_ValidTitle() {
        Problem problem = problemDAO.getProblemByTitle("Two Sum");

        assertNotNull(problem);
        assertEquals("Two Sum", problem.getTitle());
        assertEquals("Find two numbers that add up to target", problem.getDescription());
        assertEquals(1, problem.getDifficultyId());
        assertEquals(1000, problem.getTimeLimit());
        assertEquals(128, problem.getMemoryLimit());
    }

    @Test
    void testGetProblemByTitle_InvalidTitle() {
        Problem problem = problemDAO.getProblemByTitle("Non Existent Problem");

        assertNull(problem);
    }

    @Test
    void testGetProblemByTitle_NullTitle() {
        Problem problem = problemDAO.getProblemByTitle(null);

        assertNull(problem);
    }

    @Test
    void testGetProblemByTitle_EmptyTitle() {
        Problem problem = problemDAO.getProblemByTitle("");

        assertNull(problem);
    }

    @Test
    void testGetProblemByTitle_CaseSensitive() {
        Problem problem = problemDAO.getProblemByTitle("two sum");

        assertNull(problem);
    }

    @Test
    void testGetProblemByTitle_SpecialCharacters() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, time_limit, memory_limit) 
                VALUES (10, 'Problem & Solution', 'Test special chars', 1, 1000, 128)
            """);
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        Problem problem = problemDAO.getProblemByTitle("Problem & Solution");

        assertNotNull(problem);
        assertEquals("Problem & Solution", problem.getTitle());
    }

    @Test
    void testGetProblemByTitle_SQLInjection() {
        String maliciousTitle = "'; DROP TABLE problems; --";

        Problem problem = problemDAO.getProblemByTitle(maliciousTitle);
        assertNull(problem);

        Problem validProblem = problemDAO.getProblemByTitle("Two Sum");
        assertNotNull(validProblem);
    }

    @Test
    void testGetProblemTopics_ValidProblemId() {
        List<Topic> topics = problemDAO.getProblemTopics(1);

        assertNotNull(topics);
        assertEquals(1, topics.size());
        assertEquals("Arrays", topics.get(0).getTopic());
    }

    @Test
    void testGetProblemTopics_MultipleTopic() {
        List<Topic> topics = problemDAO.getProblemTopics(2);

        assertNotNull(topics);
        assertEquals(2, topics.size());
        assertTrue(topics.stream().anyMatch(t -> "Dynamic Programming".equals(t.getTopic())));
        assertTrue(topics.stream().anyMatch(t -> "Arrays".equals(t.getTopic())));
    }

    @Test
    void testGetProblemTopics_NoTopics() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                INSERT INTO problems (id, problem_title, problem_description, difficulty_id, time_limit, memory_limit) 
                VALUES (20, 'No Topic Problem', 'Problem without topics', 1, 1000, 128)
            """);
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        List<Topic> topics = problemDAO.getProblemTopics(20);

        assertNotNull(topics);
        assertTrue(topics.isEmpty());
    }

    @Test
    void testGetProblemTopics_InvalidProblemId() {
        List<Topic> topics = problemDAO.getProblemTopics(999);

        assertNotNull(topics);
        assertTrue(topics.isEmpty());
    }

    @Test
    void testGetProblemDifficulty_ValidProblemId() {
        Difficulty difficulty = problemDAO.getProblemDifficulty(1);

        assertNotNull(difficulty);
        assertEquals("Easy", difficulty.getDifficulty());
        assertEquals(1, difficulty.getId());
    }

    @Test
    void testGetProblemDifficulty_InvalidProblemId() {
        Difficulty difficulty = problemDAO.getProblemDifficulty(999);

        assertNull(difficulty);
    }

    @Test
    void testGetProblemDifficulty_AllLevels() {
        Difficulty easy = problemDAO.getProblemDifficulty(1);
        Difficulty medium = problemDAO.getProblemDifficulty(2);
        Difficulty hard = problemDAO.getProblemDifficulty(3);

        assertNotNull(easy);
        assertNotNull(medium);
        assertNotNull(hard);

        assertEquals("Easy", easy.getDifficulty());
        assertEquals("Medium", medium.getDifficulty());
        assertEquals("Hard", hard.getDifficulty());
    }

    @Test
    void testGetProblemStatus_ValidInput() {
        String status = problemDAO.getProblemStatus(1, 1);

        assertNotNull(status);
    }

    @Test
    void testGetProblemStatus_NoSubmissions() {
        String status = problemDAO.getProblemStatus(3, 1);

        assertNotNull(status);
    }

    @Test
    void testGetProblemTitle_ValidId() {
        String title = problemDAO.getProblemTitle(1);

        assertEquals("Two Sum", title);
    }

    @Test
    void testGetProblemTitle_InvalidId() {
        String title = problemDAO.getProblemTitle(999);

        assertNull(title);
    }

    @Test
    void testGetProblemId_ValidTitle() {
        int id = problemDAO.getProblemId("Two Sum");
        assertEquals(1, id);
    }

    @Test
    void testGetProblemId_InvalidTitle() {
        assertThrows(RuntimeException.class, () -> {
            problemDAO.getProblemId("Non Existent Problem");
        });
    }

    @Test
    void testGetTopicId() {
        int id = problemDAO.getTopicId("Arrays");

        assertEquals(0, id);
    }


    @Test
    void testPerformance_LargeDataset() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            for (int i = 100; i < 500; i++) {
                stmt.execute(String.format("""
                    INSERT INTO problems (id, problem_title, problem_description, difficulty_id, time_limit, memory_limit) 
                    VALUES (%d, 'Problem %d', 'Description %d', %d, 1000, 128)
                    """, i, i, i, (i % 3) + 1));
            }
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        long startTime = System.currentTimeMillis();
        Problem problem = problemDAO.getProblemByTitle("Problem 250");
        long endTime = System.currentTimeMillis();

        assertNotNull(problem);
        assertTrue(endTime - startTime < 100, "Query took too long: " + (endTime - startTime) + "ms");
    }

    @Test
    void testConnectionPooling() {
        for (int i = 0; i < 20; i++) {
            problemDAO.getProblemByTitle("Two Sum");
            problemDAO.getProblemTopics(1);
            problemDAO.getProblemDifficulty(1);
            problemDAO.getProblemTitle(1);
        }

        int activeConnections = dataSource.getNumActive();
        int idleConnections = dataSource.getNumIdle();

        assertTrue(activeConnections >= 0);
        assertTrue(idleConnections >= 0);
        assertTrue(activeConnections + idleConnections <= dataSource.getMaxTotal());
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();

                    Problem problem = problemDAO.getProblemByTitle("Two Sum");
                    List<Topic> topics = problemDAO.getProblemTopics(1);
                    Difficulty difficulty = problemDAO.getProblemDifficulty(1);

                    if (problem != null && !topics.isEmpty() && difficulty != null) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    // Error occurred
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();

        assertEquals(threadCount, successCount.get());
    }

    @Test
    void testDataConsistency() {
        Problem problemByTitle = problemDAO.getProblemByTitle("Two Sum");
        assertNotNull(problemByTitle);

        String title = problemDAO.getProblemTitle(problemByTitle.getId());
        assertEquals("Two Sum", title);

        Difficulty difficulty = problemDAO.getProblemDifficulty(problemByTitle.getId());
        assertNotNull(difficulty);
        assertEquals(problemByTitle.getDifficultyId(), difficulty.getId());
    }

}

class ProblemTestDataBuilder {

    public static Problem createProblem(int id, String title, String description, int difficultyId) {
        Problem problem = new Problem();
        problem.setId(id);
        problem.setTitle(title);
        problem.setDescription(description);
        problem.setDifficultyId(difficultyId);
        problem.setTimeLimit(1000);
        problem.setMemoryLimit(128);
        problem.setInputSpec("Standard input");
        problem.setOutputSpec("Standard output");
        problem.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return problem;
    }

    public static List<Problem> createSampleProblems() {
        return List.of(
                createProblem(1, "Two Sum", "Find two numbers that add up to target", 1),
                createProblem(2, "Longest Palindromic Substring", "Find the longest palindrome", 2),
                createProblem(3, "Graph Coloring", "Color graph with minimum colors", 3)
        );
    }
}