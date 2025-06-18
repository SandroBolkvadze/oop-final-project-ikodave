package com.example.problems.Filters;


import com.example.problems.DTO.Topic;

import java.util.List;

import static com.example.util.DatabaseConstants.*;
import static java.lang.String.format;

public class FilterTopic implements Filter {

    private final List<Topic> topics;

    public FilterTopic(List<Topic> topics) {
        this.topics = topics;
    }

    private String generateJoinStatement() {
        return format(
            "JOIN %s on %s.%s = %s.%s JOIN %s on %s.%s = %s.%s",
                ProblemManyToManyTopic.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_ID,
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemManyToManyTopic.COL_PROBLEM_ID,

                ProblemTopic.TABLE_NAME,
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemManyToManyTopic.COL_TOPIC_ID,
                ProblemTopic.TABLE_NAME,
                ProblemTopic.COL_ID
        );
    }

    private String generateWhereStatement() {
        StringBuilder statement =
                new StringBuilder(format("%s.%s in ", ProblemTopic.TABLE_NAME, ProblemTopic.COL_TOPIC));

        statement.append("(");
        for (int i = 0; i < topics.size(); i++) {
            statement.append(format("'%s'", topics.get(i).getTopic()));
            if (i < topics.size() - 1) {
                statement.append(", ");
            }
        }
        statement.append(")");

        return statement.toString();
    }

    public String joinStatement() {
        return generateJoinStatement();
    }

    public String whereStatement() {
        return generateWhereStatement();
    }

    @Override
    public String toString() {
        return format("SELECT * FROM %s %s WHERE %s;",
                Problems.TABLE_NAME,
                joinStatement(),
                whereStatement());
    }
}
