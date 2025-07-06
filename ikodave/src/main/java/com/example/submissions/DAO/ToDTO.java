package com.example.submissions.DAO;


import com.example.submissions.DTO.CodeLanguage;
import com.example.submissions.DTO.Submission;
import com.example.submissions.DTO.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.util.DatabaseConstants.*;

public class ToDTO {

    public static TestCase toTestCase(ResultSet resultSet) throws SQLException {
        return new TestCase(
                resultSet.getInt(TestCases.COL_ID),
                resultSet.getInt(TestCases.COL_PROBLEM_ID),
                resultSet.getInt(TestCases.COL_ORDER),
                resultSet.getString(TestCases.COL_INPUT),
                resultSet.getString(TestCases.COL_OUTPUT)
        );
    }
    public static int toVerdictId(ResultSet rs) throws SQLException {
        try {
            return rs.getInt(SubmissionVerdict.COL_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int toCodeLanguageId(ResultSet rs) throws SQLException {
        try {
            return rs.getInt(CodeLanguages.COL_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Submission toSubmission(ResultSet rs) throws SQLException {
        try {
            Submission submission = new Submission();
            submission.setId(rs.getInt(Problems.COL_ID));
            submission.setUserId(rs.getInt(Submissions.COL_USER_ID));
            submission.setProblemId(rs.getInt(Submissions.COL_PROBLEM_ID));
            submission.setVerdictId(rs.getInt(Submissions.COL_VERDICT_ID));
            submission.setCodeLanguageId(rs.getInt(Submissions.COL_CODE_LANGUAGE_ID));
            submission.setSolutionCode(rs.getString(Submissions.COL_SOLUTION));
            submission.setTime(rs.getLong(Submissions.COL_TIME));
            submission.setMemory(rs.getLong(Submissions.COL_MEMORY));
            submission.setSubmitDate(rs.getDate(Submissions.COL_SUBMIT_DATE));
            submission.setLog(rs.getString(Submissions.COL_LOG));
            return submission;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static CodeLanguage toCodeLanguage(ResultSet rs) throws SQLException {
        try {
            CodeLanguage codeLanguage = new CodeLanguage();
            codeLanguage.setId(rs.getInt(CodeLanguages.COL_ID));
            codeLanguage.setLanguage(rs.getString(CodeLanguages.COL_LANGUAGE));
            return codeLanguage;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
