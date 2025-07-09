package com.example.user_profile.dao;

import com.example.problems.DTO.Difficulty;
import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.user_profile.utils.ToSQL.*;
import static java.lang.String.format;

public class SQLUserDetailsDAO implements UserDetailsDAO {

    private final BasicDataSource basicDataSource;

    public SQLUserDetailsDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int getVerdictProblemsCount(User user, SubmissionVerdict verdict) {
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


    public int getAcceptedProblemCount(User user) {
        String sqlStatement = getAcceptedProblemCountSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2,"Accepted");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying accepted problems", e);
        }
    }

    public int getProblemCountByDifficulty(User user, Difficulty difficulty) {
        String sqlStatement = getProblemsCountByDifficultySQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, "Accepted");
            preparedStatement.setString(2, difficulty.getDifficulty());
            preparedStatement.setInt(3, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying accepted problems by difficulty", e);
        }
    }

    public int getSubmittedProblemCount(User user) {
        String sqlStatement = getTriedProblemCountSQL();
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


}