package com.example.user_profile.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {
    public static String getProblemsCountByStatusSQL(){
        return format("SELECT COUNT(DISTINCT %s.%s) " +
                        "FROM %s JOIN %s ON %s.%s = %s.%s " +
                        "WHERE %s.%s = ? AND %s.%s = ?;",
                Problems.TABLE_NAME,
                Problems.COL_ID,
                Problems.TABLE_NAME,
                Submissions.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_STATUS_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }
}
