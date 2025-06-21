package com.example.problems.utils;

import com.example.problems.DAO.ProblemDAO;
import com.example.problems.Filters.Filter;
import com.example.problems.Filters.FilterAnd;
import com.example.problems.Filters.FilterTitle;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.util.ArrayList;
import java.util.List;

public class FilterRequest {

    private final String title;
    private final String status;
    private final String difficulty;
    private final List<String> topics;


    public FilterRequest(String title, String status, String difficulty, List<String> topics) {
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics = topics;
    }

    public Filter toFilter(ProblemDAO problemDAO, BasicDataSource basicDataSource) {
        FilterAnd filterAnd = new FilterAnd(basicDataSource);
        if (!title.isEmpty()) {
            FilterTitle filterTitle = new FilterTitle(basicDataSource, title);
            filterAnd.addFilter(filterTitle);
        }

        return null;
    }
}
