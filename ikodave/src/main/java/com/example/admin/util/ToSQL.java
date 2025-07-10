package com.example.admin.util;

import com.example.util.DatabaseConstants;

import static java.lang.String.format;

public class ToSQL {
    public static String getRoleByIdSQL() {
        return format("SELECT USERROLE.* FROM %s USERROLE" +
                        "JOIN %s USERS on USERS.%s = USERROLE.%s" +
                        "WHERE USERS.%s = ?",
                DatabaseConstants.UserRole.COL_ID,
                DatabaseConstants.UserRole.COL_ROLE,
                DatabaseConstants.UserRole.TABLE_NAME,
                DatabaseConstants.Users.TABLE_NAME,
                DatabaseConstants.Users.COL_ROLE_ID,
                DatabaseConstants.UserRole.COL_ID,
                DatabaseConstants.Users.COL_ID
        );
    }
}
