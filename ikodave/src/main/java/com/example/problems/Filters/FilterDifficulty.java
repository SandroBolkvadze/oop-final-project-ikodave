package com.example.problems.Filters;

public class FilterDifficulty implements Filter {


    @Override
    public String joinStatement() {
        return "JOIN ";
    }

    @Override
    public String whereStatement() {
        return "";
    }
}
