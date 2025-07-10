package com.example.admin.util;

import com.example.util.DatabaseConstants;

import static java.lang.String.format;

public class ToSQL {
    public static String getRoleByIdSQL() {
        return format("SELECT * FROM %s " +
                        "WHERE %s = ?",
                DatabaseConstants.UserRole.TABLE_NAME,
                DatabaseConstants.UserRole.COL_ID
        );
    }
}
