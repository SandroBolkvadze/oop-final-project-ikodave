package com.example.problems.utils;

import com.example.problems.DTO.*;
import com.example.registration.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.util.DatabaseConstants.*;

public class ToDTO {

    public static User toUser(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt(Users.COL_ID));
            user.setUsername(rs.getString(Users.COL_USERNAME));
            user.setPassword(rs.getString(Users.COL_PASSWORD));
            user.setRankId(rs.getInt(Users.COL_RANK_ID));
            user.setRegisterDate(rs.getDate(Users.COL_REGISTER_DATE));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Topic toTopic(ResultSet rs) {
        try {
            Topic topic = new Topic();
            topic.setId(rs.getInt(ProblemTopic.COL_ID));
            topic.setTopic(rs.getString(ProblemTopic.COL_TOPIC));
            return topic;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Difficulty toDifficulty(ResultSet rs) {
        try {
            Difficulty difficulty = new Difficulty();
            difficulty.setId(rs.getInt(ProblemDifficulty.COL_ID));
            difficulty.setDifficulty(rs.getString(ProblemDifficulty.COL_DIFFICULTY));
            return difficulty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Problem toProblem(ResultSet rs) {
        try {
            Problem problem = new Problem();
            problem.setId(rs.getInt(Problems.COL_ID));
            problem.setTitle(rs.getString(Problems.COL_TITLE));
            problem.setDescription(rs.getString(Problems.COL_DESCRIPTION));
            problem.setDifficultyId(rs.getInt(Problems.COL_DIFFICULTY_ID));
            problem.setTimeLimit(rs.getLong(Problems.COL_TIME_LIMIT));
            problem.setDescription(rs.getString(Problems.COL_DESCRIPTION));
            return problem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toTitle(ResultSet rs) {
        try {
            return rs.getString(Problems.COL_TITLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int toId(ResultSet rs) {
        try {
            return rs.getInt(Problems.COL_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
