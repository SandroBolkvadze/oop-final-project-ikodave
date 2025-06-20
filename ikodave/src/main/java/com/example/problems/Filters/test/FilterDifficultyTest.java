package com.example.problems.Filters.test;

import com.example.problems.DTO.Difficulty;
import com.example.problems.Filters.FilterDifficulty;

import java.sql.Connection;

public class FilterDifficultyTest {

    public static void main(String[] args) {
        Connection connection = null;
        FilterDifficulty filterDifficulty = new FilterDifficulty(connection, new Difficulty(1, "MEDIUM"));
        System.out.println(filterDifficulty.toSQLStatement());
    }

}
