package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterStatusNone implements Filter {

    private final List<Filter> filters;
    public FilterStatusNone() {
        filters = new ArrayList<>();
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    @Override
    public String toSQLStatement() {
        StringBuilder sqlStatement = new StringBuilder();
        for (int i = 0; i < filters.size(); i++) {
            sqlStatement.append(filters.get(i).toSQLStatement());
            if (i < filters.size() - 1){
                sqlStatement.append(" UNION ALL ");
            }
        }
        return sqlStatement.toString();
    }

    @Override
    public PreparedStatement toSQLPreparedStatement(Connection connection) {
        String sqlStatement = toSQLStatement();
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 1;
            for (Filter filter : filters) {
                List<Parameter> parameters = filter.getParameters();
                for (Parameter parameter : parameters) {
                    parameter.setParameter(index++, preparedStatement);
                }
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        for (Filter filter : filters) {
            parameters.addAll(filter.getParameters());
        }
        return parameters;
    }
}