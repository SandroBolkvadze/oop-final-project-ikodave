package com.example.user_profile.dao;

import com.example.registration.model.User;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import static java.lang.String.format;

public class MySQLUserDetails implements UserDetails{

    private final BasicDataSource datasource;

    public MySQLUserDetails(BasicDataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public int getSolvedProblemsCount(User user){
        String sqlStatement = format()
    }
}
