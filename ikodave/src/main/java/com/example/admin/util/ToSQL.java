package com.example.admin.util;

import static java.lang.String.format;

public class ToSQL {
    public static String getRoleByIdSQL(){
        return format("SELECT USERROLE.%s FROM %s USERROLE" +
                "JOIN %s USERS on USERS.%s = USERROLE.%s" +
                "WHERE USERS.%s = ?"),


    }
}
