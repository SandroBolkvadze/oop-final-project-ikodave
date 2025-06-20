package com.example.problems.Filters;


import com.example.problems.DTO.Status;
import com.example.problems.Filters.Parameters.Parameter;
import com.example.problems.Filters.Parameters.ParameterString;
import com.example.registration.model.User;
import com.example.util.DatabaseConstants.*;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class FilterStatus implements Filter {

    private final User user;
    private final Status status;
    private final BasicDataSource basicDataSource;

    public FilterStatus(BasicDataSource basicDataSource, User user, Status status) {
        this.user = user;
        this.status = status;
        this.basicDataSource = basicDataSource;
    }

    public String toSQLStatement() {
        return format(
                "SELECT * FROM %s " +
                        "JOIN %s ON %s.%s = %s.%s JOIN %s ON %s.%s = %s.%s" +
                        "WHERE %s.%s = ? AND %s.%s = ?",
                Problems.TABLE_NAME,

                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                Problems.TABLE_NAME,
                Problems.COL_ID,

                ProblemStatus.TABLE_NAME,
                ProblemStatus.TABLE_NAME,
                ProblemStatus.COL_STATUS_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_STATUS_ID,

                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                ProblemStatus.TABLE_NAME,
                ProblemStatus.COL_STATUS_ID
        );
    }

    @Override
    public PreparedStatement toSQLPreparedStatement() {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try (Connection connection = basicDataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 0;
            for (Parameter parameter : getParameters()) {
                parameter.setParameter(index++, preparedStatement);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Parameter> getParameters() {
        return List.of(new ParameterString(user.getUsername()), new ParameterString(status.getStatus()));
    }
}
