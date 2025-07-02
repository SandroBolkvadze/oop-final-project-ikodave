package com.example.user_profile.utils;

import static java.lang.String.format;

public class ToSQL {
    public static String getSolvedProblemsCountSQL(int userId){
        return format("SELECT COUNT(DISTINCT %s)" +
                "FROM %s JOIN %s ON %s.%s = %s.%s" +
                "WHERE %s.%s = %s AND %s.%s = %s;",



        );

    }
}
