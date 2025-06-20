package com.example.problems.Filters.test;

import com.example.problems.DTO.Difficulty;
import com.example.problems.Filters.FilterDifficulty;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;

public class FilterDifficultyTest {

    public static void main(String[] args) {
        BasicDataSource basicDataSource = null;
        FilterDifficulty filterDifficulty = new FilterDifficulty(basicDataSource, new Difficulty(1, "MEDIUM"));
        System.out.println(filterDifficulty.toSQLStatement());
    }

}
