package com.example.problems.utils;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.registration.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDTO {

    public static User toUser(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Topic toTopic(ResultSet resultSet) {
        try {
            Topic topic = new Topic();
            topic.setId(resultSet.getInt(1));
            topic.setTopic(resultSet.getString(2));
            return topic;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Difficulty toDifficulty(ResultSet resultSet) {

        try {
            Difficulty difficulty = new Difficulty();
            difficulty.setId(resultSet.getInt(1));
            difficulty.setDifficulty(resultSet.getString(2));
            return difficulty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Status toStatus(ResultSet resultSet) {
        try {
            Status status = new Status();
            status.setId(resultSet.getInt(1));
            status.setStatus(resultSet.getString(2));
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Problem toProblem(ResultSet resultSet) {
        try {
            Problem problem = new Problem();
            problem.setId(resultSet.getInt(1));
            problem.setTitle(resultSet.getString(2));
            problem.setDescription(resultSet.getString(3));
            problem.setDifficultyId(resultSet.getInt(4));
            return problem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String toTitle(ResultSet resultSet) {
        try {
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int toId(ResultSet resultSet) {
        try {
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
