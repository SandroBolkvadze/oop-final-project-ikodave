package com.example.leaderboard.dao;

import com.example.leaderboard.dto.UserWithScore;
import com.example.registration.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.leaderboard.utils.ToDTO.toUserWithScore;
import static com.example.leaderboard.utils.ToSQL.getUsersScored;
import static com.example.problems.utils.ToDTO.toUser;

public class SQLLeaderboardDAO implements LeaderboardDAO {
    BasicDataSource dataSource;

    public SQLLeaderboardDAO(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<UserWithScore> getUsersByScore() {
        String sqlStatement = getUsersScored();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserWithScore> userWithScores = new ArrayList<>();
            while (resultSet.next()) {
                UserWithScore userWithScore = toUserWithScore(resultSet);
                userWithScores.add(userWithScore);  // Add to list
            }

            return userWithScores;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying users by score", e);
        }
    }
}
