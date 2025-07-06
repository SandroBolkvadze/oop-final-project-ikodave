package com.example.submission.DAO;

import com.example.submission.DTO.TestCase;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.apache.commons.dbcp2.BasicDataSource;

import java.awt.geom.RectangularShape;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.submission.DAO.ToDTO.toTestCase;
import static com.example.submission.DAO.ToSQL.toTestCasesSQL;

public class SQLTestDAO implements TestDAO {

    private final BasicDataSource basicDataSource;

    public SQLTestDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public List<TestCase> getTestCasesByProblemId(int problemId) {
        String sqlStatement = toTestCasesSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
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
