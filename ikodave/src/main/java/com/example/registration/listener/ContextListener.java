package com.example.registration.listener;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    BasicDataSource dataSource;

    @Override
    public void contextInitialized(javax.servlet.ServletContextEvent sce) {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/registration");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        // Store the DataSource in the servlet context for global access
        sce.getServletContext().setAttribute("dataSource", dataSource);
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
