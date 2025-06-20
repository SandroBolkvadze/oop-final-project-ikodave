package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface Filter {

    String toSQLStatement();

    PreparedStatement toSQLPreparedStatement();

    List<Parameter> getParameters();

}
