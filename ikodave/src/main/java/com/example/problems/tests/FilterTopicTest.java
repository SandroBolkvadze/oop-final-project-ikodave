package com.example.problems.tests;

import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterTopic;

import java.util.List;

public class FilterTopicTest {

    public static void main(String[] args) {
        FilterTopic filterTopic = new FilterTopic( null,
                List.of(new Topic(0, "A"),
                        new Topic(1, "B"),
                        new Topic(2, "C")));

        System.out.println(filterTopic.toSQLStatement());
    }

}
