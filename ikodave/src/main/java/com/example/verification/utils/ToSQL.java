package com.example.verification.utils;

import com.example.constants.DatabaseConstants.*;

import static java.lang.String.format;

public class ToSQL {

    public static String toRemoveTimedOutVerifications() {
        return format("DELETE FROM %s WHERE %s.%s = FALSE AND %s.%s < NOW()  LIMIT 100",
                Users.TABLE_NAME,
                Users.TABLE_NAME,
                Users.COL_IS_VERIFIED,
                Users.TABLE_NAME,
                Users.COL_VERIFICATION_TOKEN_EXPIRY
        );
    }


}
