package com.example.problems.Filters;


import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Parameters.Parameter;
import com.example.problems.Filters.Parameters.ParameterString;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.DatabaseConstants.*;
import static java.lang.String.format;

public class FilterTopic implements Filter {

    private final List<Topic> topics;
    private final BasicDataSource basicDataSource;

    public FilterTopic(Connection connection, List<Topic> topics) {
        this.topics = topics;
        this.basicDataSource = new BasicDataSource();
    }

    private String getTopicList() {
        StringBuilder topicsList = new StringBuilder();
        topicsList.append("(");
        for (int i = 0; i < topics.size(); i++) {
            topicsList.append("?");
            if (i < topics.size() - 1) {
                topicsList.append(", ");
            }
        }
        topicsList.append(")");
        return topicsList.toString();
    }

    @Override
    public String toSQLStatement() {
        return format(
                "SELECT * FROM %s p " +
                "WHERE %d = (SELECT COUNT(*) FROM %s m " +
                "JOIN %s ON %s.%s = m.%s " +
                "WHERE %s.%s IN %s AND m.%s = p.%s)",
                Problems.TABLE_NAME,
                topics.size(),
                ProblemManyToManyTopic.TABLE_NAME,
                ProblemTopic.TABLE_NAME,
                ProblemTopic.TABLE_NAME,
                ProblemTopic.COL_ID,
                ProblemManyToManyTopic.COL_TOPIC_ID,
                ProblemTopic.TABLE_NAME,
                ProblemTopic.COL_TOPIC,
                getTopicList(),
                ProblemManyToManyTopic.COL_PROBLEM_ID,
                Problems.COL_ID
        );
    }

    @Override
    public PreparedStatement toSQLPreparedStatement() {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try (Connection connection = basicDataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 0;
            for (Parameter parameter : getParameters()) {
                parameter.setParameter(index++, preparedStatement);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        for (Topic topic : topics) {
            parameters.add(new ParameterString(topic.getTopic()));
        }
        return parameters;
    }
}
