package com.example.problems.Filters;


import com.example.problems.DTO.Status;
import com.example.registration.model.User;
import com.example.util.DatabaseConstants.*;

import static java.lang.String.format;

public class FilterStatus implements Filter {

    private final User user;
    private final Status status;

    public FilterStatus(User user, Status status) {
        this.user = user;
        this.status = status;
    }

    public String joinStatement() {
        return format(
                "JOIN %s ON %s.%s = %s.%s JOIN %s ON %s.%s = %s.%s",
                Submissions.TABLE_NAME,
                Submissions.TABLE_NAME,
                Submissions.COL_PROBLEM_ID,
                Problems.TABLE_NAME,
                Problems.COL_ID,

                ProblemStatus.TABLE_NAME,
                ProblemStatus.TABLE_NAME,
                ProblemStatus.COL_STATUS_ID,
                Submissions.TABLE_NAME,
                Submissions.COL_STATUS_ID
        );
    }

    public String whereStatement() {
        return format(
                "%s.%s = %s AND %s.%s = %s",
                Submissions.TABLE_NAME,
                Submissions.COL_USER_ID,
                user.getId(),
                ProblemStatus.TABLE_NAME,
                ProblemStatus.COL_STATUS_ID,
                status.getStatus()
        );
    }

    public String toString() {
        return format(
                "SELECT * FROM %s %s WHERE %s;",
                Problems.TABLE_NAME,
                joinStatement(),
                whereStatement()
        );
    }
}
