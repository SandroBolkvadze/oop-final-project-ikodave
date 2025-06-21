package com.example.problems.utils;

import com.example.problems.Filters.Filter;
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

    public Filter toFilter() {

        return null;
    }
}
