package com.example.listener;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DAO.SQLProblemDAO;
import com.example.registration.dao.MySQLUserDao;
import com.example.registration.dao.UserDAO;
import com.example.submissions.CodeRunner.DockerCodeRunner;
import com.example.submissions.DAO.*;
import com.google.gson.Gson;
import org.apache.commons.dbcp2.BasicDataSource;

import static com.example.util.AttributeConstants.*;
import static com.example.util.DBConnectionConstants.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private BasicDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("here");
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);

        UserDAO userDao = new MySQLUserDao(dataSource);
        sce.getServletContext().setAttribute(USER_DAO_KEY, userDao);

        ProblemDAO problemDAO = new SQLProblemDAO(dataSource);
        sce.getServletContext().setAttribute(PROBLEM_DAO_KEY, problemDAO);

        UserDAO userDAO = new MySQLUserDao(dataSource);
        sce.getServletContext().setAttribute(USER_DAO_KEY, userDAO);

        TestDAO testDAO = new SQLTestDAO(dataSource);
        sce.getServletContext().setAttribute(TEST_DAO_KEY, testDAO);

        SubmissionDAO submissionDAO = new SQLSubmissionDAO(dataSource);
        sce.getServletContext().setAttribute(SUBMISSION_DAO_KEY, submissionDAO);

        CodeLanguageDAO codeLanguageDAO = new SQLCodeLanguageDAO(dataSource);
        sce.getServletContext().setAttribute(CODE_LANGUAGE_DAO_KEY, codeLanguageDAO);

        VerdictDAO verdictDAO = new SQLVerdictDAO(dataSource);
        sce.getServletContext().setAttribute(VERDICT_DAO_KEY, verdictDAO);

        DockerCodeRunner dockerCodeRunner = new DockerCodeRunner();
        dockerCodeRunner.startContainers();
        sce.getServletContext().setAttribute(DOCKER_CODE_RUNNER_KEY, dockerCodeRunner);

        Gson gson = new Gson();
        sce.getServletContext().setAttribute(GSON_KEY, gson);

        sce.getServletContext().setAttribute(BASIC_DATASOURCE_KEY, dataSource);
    }

    @Override
    public void contextDestroyed(javax.servlet.ServletContextEvent sce) {
        try {
            DockerCodeRunner dockerCodeRunner = (DockerCodeRunner) sce.getServletContext().getAttribute(DOCKER_CODE_RUNNER_KEY);
            if (dockerCodeRunner != null) {
                dockerCodeRunner.destroyContainers();
            }

            if (dataSource != null) {
                dataSource.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error closing the database connection pool", e);
        }
    }

}
