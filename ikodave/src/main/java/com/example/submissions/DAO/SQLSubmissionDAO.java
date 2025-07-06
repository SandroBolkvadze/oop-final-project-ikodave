package com.example.submissions.DAO;

import com.example.problems.DTO.Problem;
import com.example.submissions.DTO.Submission;
import com.example.submissions.DAO.SubmissionDAO;
import com.example.util.DatabaseConstants;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.toProblem;
import static com.example.submissions.DAO.ToDTO.toSubmission;
import static com.example.submissions.DAO.ToDTO.toVerdictId;
import static com.example.submissions.DAO.ToSQL.*;
import static java.lang.String.format;

public class SQLSubmissionDAO implements SubmissionDAO {

    private final BasicDataSource basicDataSource;

    public SQLSubmissionDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int insertSubmission(Submission submission) {
        String sqlStatement = toInsertSubmissionSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            setParameters(submission, preparedStatement);
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
            setParameters(submission, preparedStatement);
            preparedStatement.setInt(10, submission.getId());
            int num = preparedStatement.executeUpdate();
            assert num == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Submission> getSubmissionsBy(int userId, int problemId) {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toSubmissionSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);
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

    private void setParameters(Submission submission, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt   (1, submission.getUserId());
        preparedStatement.setInt   (2, submission.getProblemId());
        preparedStatement.setInt   (3, submission.getVerdictId());
        preparedStatement.setString(4, submission.getSolutionCode());
        preparedStatement.setInt   (5, submission.getCodeLanguageId());
        preparedStatement.setLong  (6, submission.getTime());
        preparedStatement.setLong  (7, submission.getMemory());
        preparedStatement.setDate  (8, submission.getSubmitDate());
        preparedStatement.setString(9, submission.getLog());
    }
}
