package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.*;
import static com.example.problems.utils.ToSQL.*;

public class SQLProblemDAO implements ProblemDAO {

    private final BasicDataSource basicDataSource;

    public SQLProblemDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }


    @Override
    public List<Problem> getProblemsByFilter(Filter filter) {
        String sqlStatement = filter.toSQLStatement();

        try (Connection connection = basicDataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            List<Problem> problems = new ArrayList<>();
            while (resultSet.next()) {
                problems.add(toProblem(resultSet));
            }
            return problems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> getProblemTopics(int problemId) {
        String sqlStatement = toProblemTopicsSQL();

        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, problemId);

            List<Topic> topics = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                topics.add(toTopic(resultSet));
            }

            return topics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Difficulty getProblemDifficulty(int problemId) {
        String sqlStatement = toProblemDifficultySQL();

        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, problemId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return toDifficulty(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status getProblemStatus(int problemId, int userId) {
        String sqlStatement = toProblemStatusSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, problemId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toStatus(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProblemTitle(int problemId) {
        String sqlStatement = toProblemTitleSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, problemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toTitle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getProblemId(String problemTitle) {
        String sqlStatement = toProblemTitleSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, problemTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDifficultyId(String difficulty) {
        return 0;
    }

    @Override
    public int getStatusId(String status) {
        return 0;
    }

}
