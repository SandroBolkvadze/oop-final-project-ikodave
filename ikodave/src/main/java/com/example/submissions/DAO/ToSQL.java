package com.example.submissions.DAO;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {

    public static String toTestCasesSQL() {
        return format("SELECT * FROM %s WHERE %s.%s = ?;",
                TestCases.TABLE_NAME,
                TestCases.TABLE_NAME,
                TestCases.COL_ID);
    }
    public static String toVerdictNameSQL() {
        return format("SELECT * FROM %s WHERE %s.%s = ?;",
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.TABLE_NAME,
                SubmissionVerdict.COL_VERDICT
        );
    }
    public static String toCodeLanguageNameSQL() {
        return format("SELECT * FROM %s WHERE %s.%s = ?;",
                CodeLanguage.TABLE_NAME,
                CodeLanguage.TABLE_NAME,
                CodeLanguage.COL_LANGUAGE
        );
    }
    public static String toInsertSubmissionSQL() {
        return format("INSERT INTO %s " +
                "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                Submissions.TABLE_NAME,
                Submissions.COL_ID,
                Submissions.COL_USER_ID,
                Submissions.COL_PROBLEM_ID,
                Submissions.COL_VERDICT_ID,
                Submissions.COL_SOLUTION,
                Submissions.COL_CODE_LANGUAGE,
                Submissions.COL_TIME,
                Submissions.COL_MEMORY,
                Submissions.COL_SUBMIT_DATE,
                Submissions.COL_LOG
        );
    }
    public static String toUpdateSubmissionSQL() {
        return format("UPDATE %s SET " +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "%s.%s = ?" +
                "WHERE %s.%s = ?",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME, Submissions.COL_USER_ID,
                Submissions.TABLE_NAME, Submissions.COL_PROBLEM_ID,
                Submissions.TABLE_NAME, Submissions.COL_VERDICT_ID,
                Submissions.TABLE_NAME, Submissions.COL_SOLUTION,
                Submissions.TABLE_NAME, Submissions.COL_CODE_LANGUAGE,
                Submissions.TABLE_NAME, Submissions.COL_TIME,
                Submissions.TABLE_NAME, Submissions.COL_MEMORY,
                Submissions.TABLE_NAME, Submissions.COL_SUBMIT_DATE,
                Submissions.TABLE_NAME, Submissions.COL_LOG,
                Submissions.TABLE_NAME
        );
    }
}
