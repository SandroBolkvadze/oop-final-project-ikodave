package com.example.submissions.DAO;

import com.example.submissions.DTO.SubmissionVerdict;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.submissions.DAO.ToDTO.toVerdict;
import static com.example.submissions.DAO.ToSQL.toVerdictByIdSQL;
import static com.example.submissions.DAO.ToSQL.toVerdictByNameSQL;

public class SQLVerdictDAO implements VerdictDAO {
    private final BasicDataSource basicDataSource;

    public SQLVerdictDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public SubmissionVerdict getVerdictByName(String verdictName) {
        String sqlStatement = toVerdictByNameSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, verdictName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toVerdict(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SubmissionVerdict getVerdictById(int id) {
        String sqlStatement = toVerdictByIdSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toVerdict(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
