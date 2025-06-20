package com.example.problems.Filters;

import com.example.util.DatabaseConstants.*;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class FilterAnd implements Filter{

    private final List<Filter> filters;

    private final BasicDataSource basicDataSource;

    public FilterAnd(BasicDataSource basicDataSource, List<Filter> filters) {
        this.filters = filters;
        this.basicDataSource = new BasicDataSource();
    }

    @Override
    public String toSQLStatement() {
        StringBuilder sqlStatement = new StringBuilder("WITH ");

        for (int i = 0; i < filters.size(); i++) {
            sqlStatement.append(format("t%d AS ", i));
            sqlStatement.append("(");
            sqlStatement.append(filters.get(0).toSQLStatement());
            sqlStatement.append(")");
            if (i < filters.size() - 1) {
                sqlStatement.append(",");
            }
            sqlStatement.append(" \n");
        }

        StringBuilder selectStatement = new StringBuilder(format(
                "SELECT * FROM %s",
                Problems.TABLE_NAME
        ));

        for (int i = 0; i < filters.size(); i++) {
            selectStatement.append(format(
                    " JOIN t%d on %s.%s = t%d.%s ",
                    i,
                    Problems.TABLE_NAME,
                    Problems.COL_ID,
                    i,
                    Problems.COL_ID
            ));
        }

        selectStatement.append(";");

        sqlStatement.append(selectStatement);

        return sqlStatement.toString();
    }

    @Override
    public PreparedStatement toSQLPreparedStatement() {
        String sqlStatement = toSQLStatement();

        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 0;
            for (Filter filter : filters) {
                List<String> parameters = filter.getParameters();
                for (String parameter : parameters) {
                    preparedStatement.setString(index++, parameter);
                }
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
