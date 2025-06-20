package com.example.problems.Filters;

import com.example.util.DatabaseConstants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class FilterTitle implements Filter {

    private final String title;

    private final Connection connection;

    public FilterTitle(Connection connection, String title) {
        this.title = "%" + title + "%";
        this.connection = connection;
    }

    @Override
    public String toSQLStatement() {
        return format(
                "SELECT * FROM %s WHERE %s.%s LIKE ?",
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_TITLE
        );
    }

    @Override
    public PreparedStatement toSQLPreparedStatement() {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 0;
            for (String parameter : getParameters()) {
                preparedStatement.setString(index++, parameter);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getParameters() {
        return List.of(title);
    }


}
