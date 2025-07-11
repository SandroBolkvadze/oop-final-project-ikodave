package com.example.leaderboard.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {
    public static String getUsersScored() {
        return format("SELECT USERS.%s AS \"USER\", " +
                        "(SELECT COUNT(DISTINCT SUBMISSIONS.%s) " +
                        "FROM %s USERCOUNTER " +
                        "LEFT JOIN %s SUBMISSIONS ON SUBMISSIONS.%s = USERCOUNTER.%s " +
                        "JOIN %s VERDICTS ON VERDICTS.%s = SUBMISSIONS.%s " +
                        "WHERE VERDICTS.%s = 'Accepted' AND USERCOUNTER.%s = USERS.%s) AS SCORE " +
                        "FROM %s USERS " +
                        "ORDER BY SCORE DESC;",
                Users.COL_USERNAME,
                Submissions.COL_PROBLEM_ID,
                Users.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                Users.COL_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_ID,
                Submissions.COL_VERDICT_ID,
                SubmissionVerdict.COL_VERDICT,
                Users.COL_ID,
                Users.COL_ID,
                Users.TABLE_NAME
        );
    }
}
