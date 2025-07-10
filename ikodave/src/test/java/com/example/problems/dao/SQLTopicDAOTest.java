package com.example.problems.dao;

import com.example.problems.DAO.SQLTopicDAO;
import com.example.problems.DTO.Topic;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SQLTopicDAOTest {

    private static BasicDataSource dataSource;
    private SQLTopicDAO topicDAO;

    @BeforeAll
    static void setupDatabase() {
        // Setup H2 in-memory database
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE problem_topic (
                    id INT PRIMARY KEY,
                    topic VARCHAR(255) NOT NULL UNIQUE
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup test database", e);
        }
    }

    @BeforeEach
    void setUp() {
        topicDAO = new SQLTopicDAO(dataSource);
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
            // Log error
        }
    }

    private void cleanDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM problem_topic");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }

    private void insertTestData() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (1, 'Arrays')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (2, 'Dynamic Programming')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (3, 'Graphs')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (4, 'Trees')");
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (5, 'Strings')");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert test data", e);
        }
    }

    @Test
    void testGetTopics_Success() {
        List<Topic> topics = topicDAO.getTopics();

        assertNotNull(topics);
        assertEquals(5, topics.size());

        assertTrue(topics.stream().anyMatch(t -> "Arrays".equals(t.getTopic())));
        assertTrue(topics.stream().anyMatch(t -> "Dynamic Programming".equals(t.getTopic())));
        assertTrue(topics.stream().anyMatch(t -> "Graphs".equals(t.getTopic())));
        assertTrue(topics.stream().anyMatch(t -> "Trees".equals(t.getTopic())));
        assertTrue(topics.stream().anyMatch(t -> "Strings".equals(t.getTopic())));
    }

    @Test
    void testGetTopics_EmptyDatabase() {
        cleanDatabase();

        List<Topic> topics = topicDAO.getTopics();

        assertNotNull(topics);
        assertTrue(topics.isEmpty());
    }

    @Test
    void testGetTopics_VerifyMapping() {
        List<Topic> topics = topicDAO.getTopics();

        Topic firstTopic = topics.stream()
                .filter(t -> t.getId() == 1)
                .findFirst()
                .orElseThrow();

        assertEquals(1, firstTopic.getId());
        assertEquals("Arrays", firstTopic.getTopic());
    }

    @Test
    void testGetTopicById_ValidId() {
        Topic topic = topicDAO.getTopicById(1);

        assertNotNull(topic);
        assertEquals(1, topic.getId());
        assertEquals("Arrays", topic.getTopic());
    }

    @Test
    void testGetTopicById_InvalidId() {
        Topic topic = topicDAO.getTopicById(999);

        assertNull(topic);
    }

    @Test
    void testGetTopicById_NegativeId() {
        Topic topic = topicDAO.getTopicById(-1);

        assertNull(topic);
    }

    @Test
    void testGetTopicById_ZeroId() {
        Topic topic = topicDAO.getTopicById(0);

        assertNull(topic);
    }

    @Test
    void testGetTopicById_MultipleDifferentIds() {
        Topic topic1 = topicDAO.getTopicById(1);
        Topic topic2 = topicDAO.getTopicById(2);
        Topic topic3 = topicDAO.getTopicById(3);

        assertNotNull(topic1);
        assertNotNull(topic2);
        assertNotNull(topic3);

        assertEquals("Arrays", topic1.getTopic());
        assertEquals("Dynamic Programming", topic2.getTopic());
        assertEquals("Graphs", topic3.getTopic());
    }


    @Test
    void testGetTopicByName_ValidName() {
        Topic topic = topicDAO.getTopicByName("Arrays");

        assertNotNull(topic);
        assertEquals("Arrays", topic.getTopic());
        assertEquals(1, topic.getId());
    }

    @Test
    void testGetTopicByName_InvalidName() {
        Topic topic = topicDAO.getTopicByName("NonExistentTopic");

        assertNull(topic);
    }

    @Test
    void testGetTopicByName_NullName() {
        Topic topic = topicDAO.getTopicByName(null);

        assertNull(topic);
    }

    @Test
    void testGetTopicByName_EmptyName() {
        Topic topic = topicDAO.getTopicByName("");

        assertNull(topic);
    }

    @Test
    void testGetTopicByName_CaseSensitive() {
        Topic topic = topicDAO.getTopicByName("arrays");

        assertNull(topic);
    }

    @Test
    void testGetTopicByName_WithSpaces() {
        Topic topic = topicDAO.getTopicByName("Dynamic Programming");

        assertNotNull(topic);
        assertEquals("Dynamic Programming", topic.getTopic());
        assertEquals(2, topic.getId());
    }

    @Test
    void testGetTopicByName_SpecialCharacters() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (10, 'Trees & Graphs')");
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        Topic topic = topicDAO.getTopicByName("Trees & Graphs");

        assertNotNull(topic);
        assertEquals("Trees & Graphs", topic.getTopic());
    }

    @Test
    void testGetTopicByName_SQLInjection() {
        String maliciousInput = "'; DROP TABLE problem_topic; --";

        Topic topic = topicDAO.getTopicByName(maliciousInput);
        assertNull(topic);


        List<Topic> topics = topicDAO.getTopics();
        assertEquals(5, topics.size());
    }

    @Test
    void testGetTopicByName_LongName() {
        String longName = "A".repeat(255);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO problem_topic (id, topic) VALUES (11, '" + longName + "')");
        } catch (SQLException e) {
            fail("Failed to insert test data");
        }

        Topic topic = topicDAO.getTopicByName(longName);

        assertNotNull(topic);
        assertEquals(longName, topic.getTopic());
    }


    @Test
    void testConnectionPooling() {
        for (int i = 0; i < 20; i++) {
            topicDAO.getTopics();
            topicDAO.getTopicById(1);
            topicDAO.getTopicByName("Arrays");
        }

        assertEquals(5, topicDAO.getTopics().size());

        int activeConnections = dataSource.getNumActive();
        int idleConnections = dataSource.getNumIdle();

        assertTrue(activeConnections >= 0);
        assertTrue(idleConnections >= 0);
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        boolean[] results = new boolean[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                try {
                    List<Topic> topics = topicDAO.getTopics();
                    Topic topicById = topicDAO.getTopicById(1);
                    Topic topicByName = topicDAO.getTopicByName("Arrays");

                    results[index] = topics.size() == 5 &&
                            topicById != null &&
                            topicByName != null;
                } catch (Exception e) {
                    results[index] = false;
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (boolean result : results) {
            assertTrue(result);
        }
    }

    @Test
    void testDataConsistency() {
        List<Topic> allTopics = topicDAO.getTopics();

        for (Topic topic : allTopics) {
            Topic byId = topicDAO.getTopicById(topic.getId());
            Topic byName = topicDAO.getTopicByName(topic.getTopic());

            assertNotNull(byId);
            assertNotNull(byName);
            assertEquals(topic.getId(), byId.getId());
            assertEquals(topic.getTopic(), byId.getTopic());
            assertEquals(topic.getId(), byName.getId());
            assertEquals(topic.getTopic(), byName.getTopic());
        }
    }
}

class TopicTestDataBuilder {

    public static Topic createTopic(int id, String topicName) {
        Topic topic = new Topic();
        topic.setId(id);
        topic.setTopic(topicName);
        return topic;
    }

    public static List<Topic> createTopicList() {
        return List.of(
                createTopic(1, "Arrays"),
                createTopic(2, "Dynamic Programming"),
                createTopic(3, "Graphs"),
                createTopic(4, "Trees"),
                createTopic(5, "Strings")
        );
    }
}