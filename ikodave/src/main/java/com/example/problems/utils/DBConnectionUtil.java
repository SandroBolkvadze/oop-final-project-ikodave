package com.example.problems.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Ikodave";
        String user = "root";
        String password = "pozitron4";
        return DriverManager.getConnection(url, user, password);
    }
}
