package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.problems.FrontResponse.ProblemListResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.*;
import static com.example.problems.utils.ToSQL.*;

public class SQLProblemDAO implements ProblemDAO {

    private final BasicDataSource basicDataSource;

    private final DifficultyDAO difficultyDAO;

    public SQLProblemDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
        this.difficultyDAO = new SQLDifficultyDAO(basicDataSource);
    }

    @Override
    public void insertProblem(Problem problem) {
        String sqlStatement = toInsertProblemSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, problem.getTitle());
            preparedStatement.setString(2, problem.getDescription());
            preparedStatement.setInt(3, problem.getDifficultyId());
            preparedStatement.setLong(4, problem.getTimeLimit());
            preparedStatement.setLong(5, problem.getMemoryLimit());
            preparedStatement.setString(6, problem.getInputSpec());
            preparedStatement.setString(7, problem.getOutputSpec());
            preparedStatement.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProblemListResponse> getProblemResponsesByFilterLoggedIn(Filter filter) {
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = filter.toSQLPreparedStatement(connection);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ProblemListResponse> problems = new ArrayList<>();
            while (resultSet.next()) {
                ProblemListResponse problem = toProblemListResponseLoggedIn(resultSet);
                problem.setDifficultyName(difficultyDAO.getDifficultyById(problem.getDifficultyId()).getDifficulty());
                problems.add(problem);
            }
            return problems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Problem> getProblemsByFilter(Filter filter) {
        try (Connection connection = basicDataSource.getConnection();
             PreparedStatement preparedStatement = filter.toSQLPreparedStatement(connection)) {
            ResultSet resultSet = preparedStatement.executeQuery();

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
    public List<ProblemListResponse> getProblemResponsesByFilterLoggedOut(Filter filter) {
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = filter.toSQLPreparedStatement(connection);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ProblemListResponse> problems = new ArrayList<>();
            while (resultSet.next()) {
                ProblemListResponse problem = toProblemListResponseLoggedOut(resultSet);
                problem.setDifficultyName(difficultyDAO.getDifficultyById(problem.getDifficultyId()).getDifficulty());
                problems.add(problem);
            }
            return problems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Problem getProblemByTitle(String problemTitle) {
        String sqlStatement = toProblemByTitleSQL();

        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, problemTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return toProblem(resultSet);

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
            if (!resultSet.next()) {
                return null;
            }
            return toDifficulty(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProblemStatus(int problemId, int userId) {
        String sqlStatement = toProblemStatusCountsSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, "Accepted");
            preparedStatement.setString(2, "Accepted");
            preparedStatement.setInt(3, problemId);
            preparedStatement.setInt(4, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toStatusCounts(resultSet);
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
            if (!resultSet.next()) {
                return null;
            }
            return toTitle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getProblemId(String problemTitle) {
        String sqlStatement = toProblemIdSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, problemTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getDifficultyId(String difficulty) {
        String sqlStatement = toProblemDifficultySQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, difficulty);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getStatusId(String status) {
        String sqlStatement = toProblemStatusCountsSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getTopicId(String topic) {
        return 0;
    }

}
