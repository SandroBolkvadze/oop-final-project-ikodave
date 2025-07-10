package com.example.user_profile.dao;

import com.example.problems.DTO.Difficulty;
import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.user_profile.utils.ToSQL.*;
import static com.example.util.DatabaseConstants.ProblemVerdictElements.VERDICT_ACCEPTED;

public class SQLUserStatsDAO implements UserStatsDAO {

    private final BasicDataSource basicDataSource;

    public SQLUserStatsDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int getSubmittedProblemCountByVerdict(User user, SubmissionVerdict verdict) {
        String sqlStatement = getProblemsCountByVerdictSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, verdict.getId());
            preparedStatement.setInt(2,user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying problems by status", e);
        }
        return 0;
    }


    public int getSolvedProblemCount(User user) {
        String sqlStatement = getAcceptedProblemCountSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2,VERDICT_ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying accepted problems", e);
        }
    }

    public int getSolvedProblemCountByDifficulty(User user, Difficulty difficulty) {
        String sqlStatement = getProblemCountByDifficultySQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, VERDICT_ACCEPTED);
            preparedStatement.setString(2, difficulty.getDifficulty());
            preparedStatement.setInt(3, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying accepted problems by difficulty", e);
        }
    }

    public int getSubmissionsCount(User user) {
        String sqlStatement = getSubmissionsCountSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying tried problems", e);
        }
    }

    @Override
    public int getSubmissionsCountByDays(User user, int lastDays) {
        return 0;
    }

    @Override
    public int getUserRank(User user) {
        return 0;
    }


}