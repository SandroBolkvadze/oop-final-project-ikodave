package com.example.problems.Filters;

import com.example.problems.Filters.Parameters.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public abstract class FilterAnd implements Filter {

    public abstract void addFilter(Filter filter);

}
