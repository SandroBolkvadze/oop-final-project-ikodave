package com.example.problems.utils;

import com.example.problems.DTO.*;
import com.example.problems.FrontResponse.ProblemListResponse;
import com.example.problems.FrontResponse.ProblemSpecificResponse;
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

    public static Status toStatus(ResultSet rs) {
        try {
            Status status = new Status();
            status.setId(rs.getInt(ProblemStatus.COL_ID));
            status.setStatus(rs.getString(ProblemStatus.COL_STATUS));
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProblemListResponse toProblemListResponse(ResultSet resultSet) {
        try {
            ProblemListResponse problem = new ProblemListResponse();
            problem.setTitle(resultSet.getString(Problems.COL_TITLE));
            problem.setDifficultyId(resultSet.getInt(Problems.COL_DIFFICULTY_ID));
            problem.setStatus(resultSet.getString("status"));
            return problem;
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
            problem.setCreateDate(rs.getTimestamp(Problems.COL_CREATE_DATE));
            problem.setInputSpec(rs.getString(Problems.COL_INPUT_SPEC));
            problem.setOutputSpec(rs.getString(Problems.COL_OUTPUT_SPEC));
            problem.setTimeLimit(rs.getLong(Problems.COL_TIME_LIMIT));
            problem.setMemoryLimit(rs.getLong(Problems.COL_MEMORY_LIMIT));
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
