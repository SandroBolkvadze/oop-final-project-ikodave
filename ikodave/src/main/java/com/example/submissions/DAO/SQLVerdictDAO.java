package com.example.submission.DAO;

import com.example.problems.DTO.Problem;
import com.example.problems.Filters.Filter;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.toProblem;
import static com.example.problems.utils.ToSQL.toProblemTitleSQL;
import static com.example.submission.DAO.ToDTO.toVerdictId;
import static com.example.submission.DAO.ToSQL.toVerdictNameSQL;

public class SQLVerdictDAO implements VerdictDAO {
    private final BasicDataSource basicDataSource;

    public SQLVerdictDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int getVerdictIdByName(String verdictName) {
        String sqlStatement = toVerdictNameSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, verdictName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toVerdictId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
