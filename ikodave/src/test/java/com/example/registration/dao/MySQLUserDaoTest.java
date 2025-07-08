package com.example.registration.dao;

import com.example.registration.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class MySQLUserDaoTest {

    private static BasicDataSource dataSource;
    private static MySQLUserDao userDao;

    @BeforeAll
    static void setupDatabase() throws Exception {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        userDao = new MySQLUserDao(dataSource);

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    rank_id INT DEFAULT 0,
                    register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    @AfterEach
    void clearData() throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }

    @Test
    void testAddAndGetUser() {
        User user = new User("kende", "1234");

        userDao.addUser(user);

        assertTrue(userDao.userExists("kende"));

        User retrieved = userDao.getUserByUsername("kende");
        assertNotNull(retrieved);
        assertEquals("kende", retrieved.getUsername());
        assertEquals("1234", retrieved.getPassword());
    }

    @Test
    void testAuthenticate() {
        User user = new User("nmetr", "pass");
        userDao.addUser(user);

        assertTrue(userDao.authenticate(new User("nmetr", "pass")));
        assertFalse(userDao.authenticate(new User("nmetr", "wrong")));
        assertFalse(userDao.authenticate(new User("nosuchuser", "pass")));
    }

    @Test
    void testDeleteUser() {
        User user = new User("slosa", "pwd");
        userDao.addUser(user);
        assertTrue(userDao.userExists("slosa"));

        userDao.deleteUser("slosa");
        assertFalse(userDao.userExists("slosa"));
    }

    @Test
    void testGetUserByUsernameReturnsNullIfNotFound() {
        assertNull(userDao.getUserByUsername("notfound"));
    }
}
