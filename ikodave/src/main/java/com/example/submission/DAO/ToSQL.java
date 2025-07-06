package com.example.submission.DAO;

import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {

    public static String toTestCasesSQL() {
        return format("SELECT * FROM %s WHERE %s.%s = ?",
                TestCases.TABLE_NAME,
                TestCases.TABLE_NAME,
                TestCases.COL_ID);
    }


}
