package com.example.problems.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {

    public static String toProblemTopicsSQL() {
        return format("SELECT %s.* FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                ProblemTopic.TABLE_NAME,
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
        return format("SELECT %s.* FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_DIFFICULTY_ID,
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.COL_ID,
                Problems.TABLE_NAME,
                Problems.COL_ID
        );
    }

    public static String toProblemTitleSQL() {
        return format("SELECT %s FROM %s WHERE %s.%s = ?;",
                Problems.COL_TITLE,
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_ID
        );
    }

    public static String toProblemIdSQL() {
        return format("SELECT %s FROM %s WHERE %s.%s = ?;",
                Problems.COL_ID,
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_TITLE
        );
    }

    public static String toProblemByTitleSQL(){
        return format("SELECT * FROM %s WHERE %s.%s = ?;",
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_TITLE
        );
    }


    public static String toDifficultyId() {
        return "";
    }

}
