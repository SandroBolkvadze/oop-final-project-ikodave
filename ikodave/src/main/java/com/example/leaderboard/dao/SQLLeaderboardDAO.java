package com.example.leaderboard.dao;

import com.example.registration.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class SQLLeaderboardDAO implements LeaderboardDAO {
    BasicDataSource dataSource;

    public SQLLeaderboardDAO(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getUsersByRank(){

        return null;
    }
}
