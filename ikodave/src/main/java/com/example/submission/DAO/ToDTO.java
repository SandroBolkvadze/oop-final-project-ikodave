package com.example.submission.DAO;

import com.example.submission.DTO.TestCase;

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
    public static int toVerdictId(ResultSet rs) {
        try {
            return rs.getInt(SubmissionVerdict.COL_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int toCodeLanguageId(ResultSet rs) {
        try {
            return rs.getInt(CodeLanguage.COL_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
