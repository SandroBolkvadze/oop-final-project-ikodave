package com.example.submissions.DAO;

import com.example.submissions.DTO.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.submissions.DAO.ToDTO.toTestCase;
import static com.example.submissions.DAO.ToSQL.toInsertTestCaseSQL;
import static com.example.submissions.DAO.ToSQL.toTestCasesSQL;

public class SQLTestDAO implements TestDAO {

    private final BasicDataSource basicDataSource;

    public SQLTestDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }


    @Override
    public void insertTestCase(TestCase testCase) {
        String sqlStatement = toInsertTestCaseSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, testCase.getProblemId());
            preparedStatement.setString(2, testCase.getProblemInput());
            preparedStatement.setString(3, testCase.getProblemOutput());
            preparedStatement.setInt(4, testCase.getTestNumber());
            preparedStatement.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TestCase> getTestCasesByProblemId(int problemId) {
        String sqlStatement = toTestCasesSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, problemId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<TestCase> testCases = new ArrayList<>();
            while (resultSet.next()) {
                testCases.add(toTestCase(resultSet));
            }

            return testCases;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
