package com.example.submissions.DAO;

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

}
