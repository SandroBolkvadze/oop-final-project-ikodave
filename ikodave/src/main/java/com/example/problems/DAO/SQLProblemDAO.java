package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.*;
import static com.example.problems.utils.ToSQL.toProblemTopicsSQL;

public class SQLProblemDAO implements ProblemDAO {

    private final Connection connection;

    public SQLProblemDAO(Connection connection) {
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

        try {
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
