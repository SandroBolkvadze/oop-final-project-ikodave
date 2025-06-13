package com.example.listener;

import com.example.registration.dao.MySQLUserDao;
import com.example.registration.dao.UserDao;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.servlet.ServletContextListener;

import static com.example.util.Constants.*;

public class ContextListener implements ServletContextListener {
    private BasicDataSource dataSource;

    @Override
    public void contextInitialized(javax.servlet.ServletContextEvent sce) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);

        UserDao userDao = new MySQLUserDao(dataSource);
        sce.getServletContext().setAttribute(USER_DAO_KEY, userDao);
    }

    @Override
    public void contextDestroyed(javax.servlet.ServletContextEvent sce) {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error closing the database connection pool", e);
        }
    }

}
