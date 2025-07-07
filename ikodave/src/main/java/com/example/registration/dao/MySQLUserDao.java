package com.example.registration.dao;

import com.example.registration.model.User;
import com.example.util.DatabaseConstants.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.problems.utils.ToDTO.toUser;
import static java.lang.String.format;

public class MySQLUserDao implements UserDAO {
    private final BasicDataSource dataSource;

    public MySQLUserDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUser(int userId) {
        String sqlStatement = format("SELECT * FROM %s WHERE %s.%s = ?;",
                    Users.TABLE_NAME,
                    Users.TABLE_NAME,
                    Users.COL_ID
        );

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection con = dataSource.getConnection()){
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error adding user", e);
        }
    }

    @Override
    public boolean authenticate(User user) {
        // Implementation to authenticate user
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error authenticating user", e);
        }
        return false;
    }

    @Override
    public void deleteUser(String username) {
        try(Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
    @Override
    public boolean userExists(String username) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if user exists", e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String sqlStatement = format("SELECT * FROM %s WHERE %s.%s = ?;",
                    Users.TABLE_NAME,
                    Users.TABLE_NAME,
                    Users.COL_USERNAME
        );

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return toUser(resultSet);
            } else {
                return null; // User not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
