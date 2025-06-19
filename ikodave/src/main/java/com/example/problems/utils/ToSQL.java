package com.example.problems.utils;

import com.example.util.DatabaseConstants;

import static java.lang.String.format;

public class ToSQL {

    public static String toProblemTopicsSQL() {
        return format("SELECT %s FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                DatabaseConstants.ProblemTopic.COL_TOPIC,
                DatabaseConstants.ProblemTopic.TABLE_NAME,
                DatabaseConstants.ProblemManyToManyTopic.TABLE_NAME,
                DatabaseConstants.ProblemManyToManyTopic.TABLE_NAME,
                DatabaseConstants.ProblemManyToManyTopic.COL_TOPIC_ID,
                DatabaseConstants.ProblemTopic.TABLE_NAME,
                DatabaseConstants.ProblemTopic.COL_ID,
                DatabaseConstants.ProblemManyToManyTopic.TABLE_NAME,
                DatabaseConstants.ProblemManyToManyTopic.COL_PROBLEM_ID
        );
    }


    public static String toProblemDifficultySQL() {
        return format(
            "SELECT * FROM"
        );
    }

}
