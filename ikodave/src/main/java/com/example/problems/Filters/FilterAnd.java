package com.example.problems.Filters;

import java.util.List;

public class FilterAnd implements Filter{

    private final List<Filter> filters;

    public FilterAnd(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public String joinStatement() {
        return "";
    }

    @Override
    public String whereStatement() {
        return "";
    }

    @Override
    public String toSQLStatement() {
        return "";
    }
}
