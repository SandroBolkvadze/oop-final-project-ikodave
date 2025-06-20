package com.example.problems.utils;

import com.example.problems.servlets.ProblemsServlet;
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
        return format("SELECT %s FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                ProblemDifficulty.COL_DIFFICULTY,
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


    public static String toProblemStatusSQL() {
        return format("SELECT %s FROM %s JOIN %s on %s.%s = %s.%s WHERE %s.%s = ? AND %s.%s = ?;",
                ProblemStatus.COL_STATUS_ID,
                ProblemStatus.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_STATUS_ID,
                ProblemStatus.TABLE_NAME,
                ProblemStatus.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_STATUS_ID
        );
    }

}
