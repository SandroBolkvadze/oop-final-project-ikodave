package com.example.problems.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {

    public static String toProblemTopicsSQL() {
        return format("SELECT %s FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                ProblemTopic.COL_TOPIC,
                ProblemTopic.TABLE_NAME,
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemManyToManyTopic.COL_TOPIC_ID,
                ProblemTopic.TABLE_NAME,
                ProblemTopic.COL_ID,
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemManyToManyTopic.COL_PROBLEM_ID
        );
    }


    public static String toProblemDifficultySQL() {
        return format(
            "SELECT * FROM"
        );
    }

}
