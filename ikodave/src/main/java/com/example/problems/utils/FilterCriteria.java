package com.example.problems.utils;

import java.util.List;

public class FilterCriteria {

    private final String title;
    private final String status;
    private final String difficulty;
    private final List<String> topics;


    public FilterCriteria(String title, String status, String difficulty, List<String> topics) {
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics = topics;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<String> getTopics() {
        return topics;
    }

}
