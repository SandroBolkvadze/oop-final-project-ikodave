package com.example.user_profile.dao;

import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.user_profile.utils.ToSQL.*;


public class SQLUserDetailsDAO implements UserDetailsDAO {

    private final BasicDataSource basicDataSource;

    public SQLUserDetailsDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    @Override
    public int getVerdictProblemsCount(User user, SubmissionVerdict verdict) {
        String sqlStatement = getProblemsCountByStatusSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, verdict.getId());
            preparedStatement.setInt(2,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying problems by status", e);
        }
        return 0;
    }
}