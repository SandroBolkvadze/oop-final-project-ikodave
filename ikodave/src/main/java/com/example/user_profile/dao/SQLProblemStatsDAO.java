package com.example.user_profile.dao;

import com.example.problems.DTO.Difficulty;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.user_profile.utils.ToSQL.getProblemCountByDifficultySQL;
import static com.example.user_profile.utils.ToSQL.getUserSubmissionCountByDays;

public class SQLProblemStatsDAO implements ProblemStatsDAO {

    private final BasicDataSource basicDataSource;

    public SQLProblemStatsDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int getProblemCountByDifficulty(Difficulty difficulty) {
        String sqlStatement = getProblemCountByDifficultySQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, difficulty.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying tried problems", e);
        }
    }
}
