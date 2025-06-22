package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;
import com.example.problems.Filters.Parameters.ParameterString;
import com.example.util.DatabaseConstants.*;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class FilterTitle implements Filter {

    private final String title;

    private final BasicDataSource basicDataSource;

    public FilterTitle(BasicDataSource basicDataSource, String title) {
        this.title = "%" + title + "%";
        this.basicDataSource = basicDataSource;
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
    public PreparedStatement toSQLPreparedStatement(Connection connection) {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try  {
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
        return List.of(new ParameterString(title));
    }


}
