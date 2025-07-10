package com.example.user_profile.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {
    public static String getSubmittedProblemCountByVerdictSQL() {
        return format("SELECT COUNT(*) " +
                        "FROM %s WHERE %s.%s = ? AND %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }
    public static String getProblemCountByDifficultySQL() {
        return format("SELECT COUNT(DISTINCT %s.%s) " +
                        "FROM %s JOIN %s ON %s.%s = %s.%s " +
                        "JOIN %s ON %s.%s = %s.%s " +
                        "WHERE %s.%s = ? AND %s.%s = ? AND %s.%s = ?",
                Problems.TABLE_NAME,
                Problems.COL_ID,
                Problems.TABLE_NAME,
                Submissions.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_VERDICT,
                Problems.TABLE_NAME,
                Problems.COL_DIFFICULTY_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }
    public static String getSolvedProblemCountSQL() {
        return format("SELECT COUNT(DISTINCT %s.%s) " +
                        "FROM %s JOIN %s ON %s.%s = %s.%s " +
                        "WHERE %s.%s = ? AND %s.%s = ?",
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                Submissions.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_VERDICT
        );
    }
    public static String getSubmissionsCountSQL() {
        return format("SELECT COUNT(*) FROM %s WHERE %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }


    public static String getSubmissionCountByDays() {
        return format("SELECT COUNT(*) FROM %s " +
                        "WHERE DAY(%s.%s) = DAY(CURRDATE()) AND %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_SUBMIT_DATE,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }



}