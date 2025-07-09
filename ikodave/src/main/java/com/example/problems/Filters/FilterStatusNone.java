package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static java.lang.String.format;

public class FilterStatusNone implements Filter {

    private final BasicDataSource basicDataSource;
    private final int userId;

    public FilterStatusNone(BasicDataSource basicDataSource, int userId) {
        this.basicDataSource = basicDataSource;
        this.userId = userId;
    }

    @Override
    public String toSQLStatement() {

    }

    @Override
    public PreparedStatement toSQLPreparedStatement(Connection connection) {
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return List.of();
    }
}
