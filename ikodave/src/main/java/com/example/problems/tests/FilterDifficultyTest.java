package com.example.problems.tests;

import com.example.problems.DTO.Difficulty;
import com.example.problems.Filters.FilterDifficulty;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class FilterDifficultyTest {

    public static void main(String[] args) {
        BasicDataSource basicDataSource = null;
        FilterDifficulty filterDifficulty = new FilterDifficulty(new Difficulty(1, "MEDIUM"));
        System.out.println(filterDifficulty.toSQLStatement());
    }

}
