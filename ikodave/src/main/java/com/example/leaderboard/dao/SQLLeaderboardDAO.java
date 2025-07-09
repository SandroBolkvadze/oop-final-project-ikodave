package com.example.leaderboard.dao;

import com.example.leaderboard.dto.UserWithRank;
import com.example.registration.model.User;
import com.mysql.cj.exceptions.ClosedOnExpiredPasswordException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.leaderboard.utils.ToSQL.getUsersRanked;
import static com.example.problems.utils.ToDTO.toUser;

public class SQLLeaderboardDAO implements LeaderboardDAO {
    BasicDataSource dataSource;

    public SQLLeaderboardDAO(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<UserWithRank> getUsersByRank() {
        String sqlStatement = getUsersRanked();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserWithRank> userWithRanks = new ArrayList<>();
            while (resultSet.next()) {
                User user = toUser(resultSet);                    // Create User object
                int rank = resultSet.getInt("RANK");              // Read RANK column
                userWithRanks.add(new UserWithRank(user, rank));  // Add to list
            }
            return userWithRanks;
        } catch (SQLException e) {
            throw new RuntimeException("Error querying users by rank", e);
        }
    }
}
