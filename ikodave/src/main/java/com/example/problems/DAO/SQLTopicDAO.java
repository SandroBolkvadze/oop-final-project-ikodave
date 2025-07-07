package com.example.problems.DAO;

import com.example.problems.DTO.Topic;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.ToDTO.toTitle;
import static com.example.problems.utils.ToDTO.toTopic;
import static com.example.problems.utils.ToProblemTopicsSQL.*;
import static com.example.problems.utils.ToSQL.toProblemTitleSQL;

public class SQLTopicDAO implements TopicDAO {

    private final BasicDataSource basicDataSource;

    public SQLTopicDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public List<Topic> getTopics() {
        String sqlStatement = toProblemTopicsSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Topic> topics = new ArrayList<>();
            while (resultSet.next()) {
                topics.add(toTopic(resultSet));
            }
            return topics;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic getTopicById(int topicId) {
        String sqlStatement = toProblemTopicsByIdSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return toTopic(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic getTopicByName(String topicName) {
        String sqlStatement = toProblemTopicsByTopicNameSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, topicName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return toTopic(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
