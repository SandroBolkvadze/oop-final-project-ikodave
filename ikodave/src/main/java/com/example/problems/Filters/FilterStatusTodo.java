package com.example.problems.Filters;

import com.example.problems.DTO.Status;
import com.example.problems.Filters.Parameters.Parameter;
import com.example.problems.Filters.Parameters.ParameterInteger;
import com.example.registration.model.User;
import com.example.util.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class FilterStatusTodo implements Filter{
    private final User user;
    private final Status status;

    public FilterStatusTodo(User user, Status status) {
        this.user = user;
        this.status = status;
    }
    @Override
    public String toSQLStatement() {
        return format("SELECT %s.*, 'Todo' AS status FROM %s LEFT JOIN %s ON %s.%s = %s.%s " +
                        "AND %s.%s = ? WHERE %s.%s IS NULL",
                DatabaseConstants.Problems.TABLE_NAME,
                DatabaseConstants.Problems.TABLE_NAME,
                DatabaseConstants.Submissions.TABLE_NAME,
                DatabaseConstants.Submissions.TABLE_NAME,
                DatabaseConstants.Submissions.COL_PROBLEM_ID,
                DatabaseConstants.Problems.TABLE_NAME,
                DatabaseConstants.Problems.COL_ID,
                DatabaseConstants.Submissions.TABLE_NAME,
                DatabaseConstants.Submissions.COL_USER_ID,
                DatabaseConstants.Submissions.TABLE_NAME,
                DatabaseConstants.Submissions.COL_PROBLEM_ID
        );
    }

    @Override
    public PreparedStatement toSQLPreparedStatement(Connection connection) {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 1;
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
        return List.of(new ParameterInteger(user.getId()));
    }
}
