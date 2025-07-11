package com.example.problems.dao;

import com.example.problems.DAO.SQLDifficultyDAO;
import com.example.problems.DTO.Difficulty;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SQLDifficultyDAOTest {

    private static BasicDataSource dataSource;
    private SQLDifficultyDAO difficultyDAO;

    @BeforeAll
    static void setupDatabase() {
        // Setup H2 in-memory database
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb_difficulty;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE problem_difficulty (
                    id INT PRIMARY KEY,
                    difficulty VARCHAR(255) NOT NULL UNIQUE
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup test database", e);
        }
    }

    @BeforeEach
    void setUp() {
        difficultyDAO = new SQLDifficultyDAO(dataSource);
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
            stmt.execute("DELETE FROM problem_difficulty");
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

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert test data", e);
        }
    }

    @Test
    void testGetDifficulties_Success() {
        List<Difficulty> difficulties = difficultyDAO.getDifficulties();

        assertNotNull(difficulties);
        assertEquals(3, difficulties.size());

        assertTrue(difficulties.stream().anyMatch(d -> "Easy".equals(d.getDifficulty())));
        assertTrue(difficulties.stream().anyMatch(d -> "Medium".equals(d.getDifficulty())));
        assertTrue(difficulties.stream().anyMatch(d -> "Hard".equals(d.getDifficulty())));
    }

    @Test
    void testGetDifficulties_EmptyDatabase() {
        cleanDatabase();

        List<Difficulty> difficulties = difficultyDAO.getDifficulties();

        assertNotNull(difficulties);
        assertTrue(difficulties.isEmpty());
    }


    @Test
    void testGetDifficulties_VerifyMapping() {
        List<Difficulty> difficulties = difficultyDAO.getDifficulties();

        Difficulty easyDifficulty = difficulties.stream()
                .filter(d -> d.getId() == 1)
                .findFirst()
                .orElseThrow();

        assertEquals(1, easyDifficulty.getId());
        assertEquals("Easy", easyDifficulty.getDifficulty());
    }

    @Test
    void testGetDifficulties_Order() {
        List<Difficulty> difficulties = difficultyDAO.getDifficulties();

        assertEquals(3, difficulties.size());

        Difficulty easy = difficulties.stream().filter(d -> "Easy".equals(d.getDifficulty())).findFirst().orElseThrow();
        Difficulty medium = difficulties.stream().filter(d -> "Medium".equals(d.getDifficulty())).findFirst().orElseThrow();
        Difficulty hard = difficulties.stream().filter(d -> "Hard".equals(d.getDifficulty())).findFirst().orElseThrow();

        assertEquals(1, easy.getId());
        assertEquals(2, medium.getId());
        assertEquals(3, hard.getId());
    }

    @Test
    void testGetDifficultyById_ValidId() {
        Difficulty difficulty = difficultyDAO.getDifficultyById(1);

        assertNotNull(difficulty);
        assertEquals(1, difficulty.getId());
        assertEquals("Easy", difficulty.getDifficulty());
    }

    @Test
    void testGetDifficultyById_InvalidId() {
        Difficulty difficulty = difficultyDAO.getDifficultyById(999);

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyById_NegativeId() {
        Difficulty difficulty = difficultyDAO.getDifficultyById(-1);

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyById_ZeroId() {
        Difficulty difficulty = difficultyDAO.getDifficultyById(0);

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyById_AllValidIds() {
        Difficulty easy = difficultyDAO.getDifficultyById(1);
        Difficulty medium = difficultyDAO.getDifficultyById(2);
        Difficulty hard = difficultyDAO.getDifficultyById(3);

        assertNotNull(easy);
        assertNotNull(medium);
        assertNotNull(hard);

        assertEquals("Easy", easy.getDifficulty());
        assertEquals("Medium", medium.getDifficulty());
        assertEquals("Hard", hard.getDifficulty());
    }
    @Test
    void testGetDifficultyByName_ValidName() {
        Difficulty difficulty = difficultyDAO.getDifficultyByName("Easy");

        assertNotNull(difficulty);
        assertEquals("Easy", difficulty.getDifficulty());
        assertEquals(1, difficulty.getId());
    }

    @Test
    void testGetDifficultyByName_InvalidName() {
        Difficulty difficulty = difficultyDAO.getDifficultyByName("VeryHard");

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyByName_NullName() {
        Difficulty difficulty = difficultyDAO.getDifficultyByName(null);

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyByName_EmptyName() {
        Difficulty difficulty = difficultyDAO.getDifficultyByName("");

        assertNull(difficulty);
    }

    @Test
    void testGetDifficultyByName_Whitespace() {
        Difficulty difficulty1 = difficultyDAO.getDifficultyByName(" Easy ");
        assertNull(difficulty1);

        Difficulty difficulty2 = difficultyDAO.getDifficultyByName("Easy");
        assertNotNull(difficulty2);
    }

    @Test
    void testGetDifficultyByName_SpecialCharacters() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (4, 'Extra-Hard')");
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        Difficulty difficulty = difficultyDAO.getDifficultyByName("Extra-Hard");

        assertNotNull(difficulty);
        assertEquals("Extra-Hard", difficulty.getDifficulty());
    }

    @Test
    void testGetDifficultyByName_SQLInjection() {
        String maliciousInput = "'; DROP TABLE problem_difficulty; --";

        Difficulty difficulty = difficultyDAO.getDifficultyByName(maliciousInput);
        assertNull(difficulty);

        List<Difficulty> difficulties = difficultyDAO.getDifficulties();
        assertEquals(3, difficulties.size());
    }

    @Test
    void testGetDifficultyByName_LongName() {
        String longName = "D".repeat(255);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (5, '" + longName + "')");
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        Difficulty difficulty = difficultyDAO.getDifficultyByName(longName);

        assertNotNull(difficulty);
        assertEquals(longName, difficulty.getDifficulty());
    }

    @Test
    @DisplayName("Should handle moderate number of difficulties efficiently")
    void testPerformance_ModerateDataset() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            for (int i = 10; i < 50; i++) {
                stmt.execute(String.format(
                        "INSERT INTO problem_difficulty (id, difficulty) VALUES (%d, 'Difficulty%d')",
                        i, i
                ));
            }
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        long startTime = System.currentTimeMillis();
        List<Difficulty> difficulties = difficultyDAO.getDifficulties();
        long endTime = System.currentTimeMillis();

        assertEquals(43, difficulties.size()); // 3 original + 40 new
        assertTrue(endTime - startTime < 500, "Query took too long: " + (endTime - startTime) + "ms");
    }

    @Test
    @DisplayName("Should properly release connections back to pool")
    void testConnectionPooling() {
        // Execute multiple operations to test connection reuse
        for (int i = 0; i < 50; i++) {
            difficultyDAO.getDifficulties();
            difficultyDAO.getDifficultyById(1);
            difficultyDAO.getDifficultyByName("Easy");
        }

        assertEquals(3, difficultyDAO.getDifficulties().size());

        int activeConnections = dataSource.getNumActive();
        int idleConnections = dataSource.getNumIdle();

        assertTrue(activeConnections >= 0);
        assertTrue(idleConnections >= 0);
        assertTrue(activeConnections + idleConnections <= dataSource.getMaxTotal());
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int threadCount = 20;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();

                    List<Difficulty> difficulties = difficultyDAO.getDifficulties();
                    Difficulty diffById = difficultyDAO.getDifficultyById(2);
                    Difficulty diffByName = difficultyDAO.getDifficultyByName("Medium");

                    if (difficulties.size() == 3 && diffById != null && diffByName != null) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();

        assertEquals(threadCount, successCount.get());
        assertEquals(0, errorCount.get());
    }

    @Test
    void testDataConsistency() {
        List<Difficulty> allDifficulties = difficultyDAO.getDifficulties();

        for (Difficulty difficulty : allDifficulties) {
            Difficulty byId = difficultyDAO.getDifficultyById(difficulty.getId());
            Difficulty byName = difficultyDAO.getDifficultyByName(difficulty.getDifficulty());

            assertNotNull(byId);
            assertNotNull(byName);
            assertEquals(difficulty.getId(), byId.getId());
            assertEquals(difficulty.getDifficulty(), byId.getDifficulty());
            assertEquals(difficulty.getId(), byName.getId());
            assertEquals(difficulty.getDifficulty(), byName.getDifficulty());
        }
    }

    @Test
    void testVariousDifficultyFormats() {
        cleanDatabase();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (1, 'Beginner')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (2, 'Intermediate')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (3, 'Advanced')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (4, 'Expert')");
            stmt.execute("INSERT INTO problem_difficulty (id, difficulty) VALUES (5, 'Master')");

        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        List<Difficulty> difficulties = difficultyDAO.getDifficulties();
        assertEquals(5, difficulties.size());

        assertNotNull(difficultyDAO.getDifficultyByName("Beginner"));
        assertNotNull(difficultyDAO.getDifficultyByName("Intermediate"));
        assertNotNull(difficultyDAO.getDifficultyByName("Advanced"));
        assertNotNull(difficultyDAO.getDifficultyByName("Expert"));
        assertNotNull(difficultyDAO.getDifficultyByName("Master"));
    }

    @Test
    void testPublicFieldAccess() {
        Difficulty difficulty = difficultyDAO.getDifficultyById(1);

        assertNotNull(difficulty);

        assertEquals(1, difficulty.id);
        assertEquals(1, difficulty.getId());
        assertEquals(difficulty.id, difficulty.getId());
    }
}

class DifficultyTestDataBuilder {

    public static Difficulty createDifficulty(int id, String difficultyName) {
        Difficulty difficulty = new Difficulty();
        difficulty.setId(id);
        difficulty.setDifficulty(difficultyName);
        return difficulty;
    }

    public static List<Difficulty> createStandardDifficulties() {
        return List.of(
                createDifficulty(1, "Easy"),
                createDifficulty(2, "Medium"),
                createDifficulty(3, "Hard")
        );
    }

    public static List<Difficulty> createExtendedDifficulties() {
        return List.of(
                createDifficulty(1, "Beginner"),
                createDifficulty(2, "Easy"),
                createDifficulty(3, "Medium"),
                createDifficulty(4, "Hard"),
                createDifficulty(5, "Expert"),
                createDifficulty(6, "Master")
        );
    }
}