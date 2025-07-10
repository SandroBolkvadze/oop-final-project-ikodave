package com.example.user_profile.utils;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {
    public static String getUserSubmittedProblemCountByVerdictSQL() {
        return format("SELECT COUNT(*) " +
                        "FROM %s WHERE %s.%s = ? AND %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }
    public static String getUserProblemCountByDifficultySQL() {
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
    public static String getUserSolvedProblemCountSQL() {
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
    public static String getUserSubmissionsCountSQL() {
        return format("SELECT COUNT(*) FROM %s WHERE %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }


    public static String getUserSubmissionCountByDays() {
        return format("SELECT COUNT(*) FROM %s " +
                        "WHERE DAY(%s.%s) = DAY(NOW()) AND %s.%s = ?;",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_SUBMIT_DATE,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID
        );
    }


    public static String getUserRankSQL() {
        return format("""
                        WITH scores AS (
                             SELECT 
                             %s.%s AS username, COUNT(DISTINCT %s.%s) as score 
                             FROM %s 
                             LEFT JOIN %s on %s.%s = %s.%s 
                             LEFT JOIN %s on %s.%s = %s.%s and %s.%s = 'Accepted' 
                             GROUP BY %s.%s, %s.%s 
                        ), 
                        ranks as ( 
                             SELECT 
                             username, 
                             ROW_NUMBER() OVER (ORDER BY score DESC, username ASC) AS user_rank 
                             FROM scores 
                        )
                        SELECT user_rank FROM ranks WHERE username = ?; 
                """,
                Users.TABLE_NAME,
                Users.COL_USERNAME,
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                Users.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                Users.TABLE_NAME,
                Users.COL_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_VERDICT,
                Users.TABLE_NAME,
                Users.COL_ID,
                Users.TABLE_NAME,
                Users.COL_USERNAME
        );

    }



    public static String getProblemCountByDifficultySQL() {
        return format("SELECT COUNT(*) FROM %s WHERE %s.%s = ?",
                Problems.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_DIFFICULTY_ID
        );
    }
    public static String getUserActivityByMonthSQL(){
        return format("SELECT DISTINCT(%s.%s) FROM %s JOIN %s ON %s.%s = %s.%s " +
                        "WHERE MONTH(%s.%s) = MONTH(NOW()) AND %s.%s = ? AND %s.%s = ?",
                Submissions.TABLE_NAME,
                Submissions.COL_SUBMIT_DATE,
                Submissions.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_VERDICT_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_SUBMIT_DATE,
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_VERDICT
        );
    }

}