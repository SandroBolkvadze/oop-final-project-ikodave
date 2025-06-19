package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.util.DatabaseConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MySQLProblemDAO implements ProblemDAO {

    private final Connection connection;

    public MySQLProblemDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Problem> getProblemsByFilter(Filter filter) {
        String sqlStatement = filter.toSQLStatement();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            List<Problem> problems = new ArrayList<>();
            while (resultSet.next()) {
                Problem problem = new Problem();
                problem.setId(Integer.parseInt(resultSet.getString(1)));
                problem.setTitle(resultSet.getString(2));
                problem.setDescription(resultSet.getString(3));
                problem.setDifficultyId(resultSet.getInt(4));
                problems.add(problem);
            }
            return problems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> getProblemTopics(int problemId) {
        String sqlStatement = "SELECT ? FROM ? WHERE ? = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return List.of();
    }

    @Override
    public Difficulty getProblemDifficulty(int problemId) {
        return null;
    }

    @Override
    public Status getProblemStatus(int problemId, int userId) {
        return null;
    }

    @Override
    public String getProblemName(int problemId) {
        return "";
    }

    @Override
    public int getProblemId(String problemName) {
        return 0;
    }


}
