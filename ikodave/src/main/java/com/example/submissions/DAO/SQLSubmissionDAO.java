package com.example.submissions.DAO;

import com.example.submissions.DTO.Submission;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.submissions.DAO.ToDTO.toSubmission;
import static com.example.submissions.DAO.ToSQL.*;

public class SQLSubmissionDAO implements SubmissionDAO {

    private final BasicDataSource basicDataSource;

    public SQLSubmissionDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int insertSubmission(Submission submission) {
        String sqlStatement = toInsertSubmissionNoIdSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, submission.getUserId());
            preparedStatement.setInt(2, submission.getProblemId());
            preparedStatement.setInt(3, submission.getVerdictId());
            preparedStatement.setString(4, submission.getSolutionCode());
            preparedStatement.setInt(5, submission.getCodeLanguageId());
            preparedStatement.setLong(6, submission.getTime());
            preparedStatement.setLong(7, submission.getMemory());
            preparedStatement.setTimestamp(8, submission.getSubmitDate());
            preparedStatement.setString(9, submission.getLog());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateSubmission(Submission submission) {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toUpdateSubmissionSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, submission.getUserId());
            preparedStatement.setInt(2, submission.getProblemId());
            preparedStatement.setInt(3, submission.getVerdictId());
            preparedStatement.setString(4, submission.getSolutionCode());
            preparedStatement.setInt(5, submission.getCodeLanguageId());
            preparedStatement.setLong(6, submission.getTime());
            preparedStatement.setLong(7, submission.getMemory());
            preparedStatement.setTimestamp(8, submission.getSubmitDate());
            preparedStatement.setString(9, submission.getLog());
            preparedStatement.setInt(10, submission.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Submission> getSubmissionsBy(int userId, int problemId) {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toSubmissionSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, problemId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Submission> submissions = new ArrayList<>();
            while (resultSet.next()) {
                submissions.add(toSubmission(resultSet));
            }
            return submissions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Submission> getSubmissionsByOrder(int userId, int problemId) {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toSubmissionSortedSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, problemId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Submission> submissions = new ArrayList<>();
            while (resultSet.next()) {
                submissions.add(toSubmission(resultSet));
            }
            return submissions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Submission> getSubmissionsByDateByOrder(int userId, int day, int month, int year) {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toSubmissionByDateSortedSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, day);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, year);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Submission> submissions = new ArrayList<>();
            while (resultSet.next()) {
                submissions.add(toSubmission(resultSet));
            }
            return submissions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
