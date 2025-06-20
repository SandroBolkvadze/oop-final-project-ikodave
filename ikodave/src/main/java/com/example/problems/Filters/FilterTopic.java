package com.example.problems.Filters;


import com.example.problems.DTO.Topic;

import java.util.List;

import static com.example.util.DatabaseConstants.*;
import static java.lang.String.format;

public class FilterTopic implements Filter {

    private final List<Topic> topics;

    public FilterTopic(List<Topic> topics) {
        this.topics = topics;
    }


    @Override
    public String toSQLStatement() {
        return "";
    }
}
