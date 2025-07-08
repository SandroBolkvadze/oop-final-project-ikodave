package com.example.submissions.DAO;

import com.example.problems.DTO.Status;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.toStatus;
import static com.example.submissions.DAO.ToDTO.toVerdict;
import static com.example.submissions.DAO.ToSQL.*;

public class SQLStatusDAO implements StatusDAO {

    private final BasicDataSource basicDataSource;

    public SQLStatusDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public List<Status> getStatuses() {
        String sqlStatement = toStatusSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Status> statuses = new ArrayList<>();
            while(resultSet.next()){
                statuses.add(toStatus(resultSet));
            }
            return statuses;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status getStatusById(int statusId) {
        String sqlStatement = toStatusByIdSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toStatus(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status getStatusByStatusName(String statusName) {
        String sqlStatement = toStatusByNameSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, statusName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toStatus(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Status getStatusByUserProblemId(int userId, int problemId) {
        //String sqlStatement = toStatusByIdsSQL();
        return null;
    }

}
