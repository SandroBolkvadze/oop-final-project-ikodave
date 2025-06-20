package com.example.problems.Filters.test;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.FilterAnd;
import com.example.problems.Filters.FilterDifficulty;
import com.example.problems.Filters.FilterTopic;

import java.sql.Connection;
import java.util.List;

public class FilterAndTest {

    public static void main(String[] args) {
        Connection connection = null;
        FilterDifficulty filterDifficulty = new FilterDifficulty(connection, new Difficulty(1, "MEDIUM"));
        FilterTopic filterTopic = new FilterTopic( null,
                List.of(new Topic(0, "A"),
                        new Topic(1, "B"),
                        new Topic(2, "C")));

        FilterAnd filterAnd = new FilterAnd(
                null, List.of(filterDifficulty, filterTopic));

        System.out.println(filterAnd.toSQLStatement());
    }

}
