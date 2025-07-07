package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class FilterNone implements Filter {
    @Override
    public String toSQLStatement() {
        return "";
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
