package com.example.problems.Filters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface Filter {

    String toSQLStatement();

    PreparedStatement toSQLPreparedStatement();

    default List<String> getParameters() {
        return List.of();
    }

}
