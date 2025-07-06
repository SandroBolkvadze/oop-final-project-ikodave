package com.example.submission.DAO;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static com.example.submission.DAO.ToDTO.toCodeLanguageId;
import static com.example.submission.DAO.ToSQL.toCodeLanguageNameSQL;
import static com.example.submission.DAO.ToSQL.toVerdictNameSQL;

public class SQLCodeLanguageDAO implements  CodeLanguageDAO {
    private final BasicDataSource basicDataSource;

    public SQLCodeLanguageDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    @Override
    public int getCodeLanguageIdByName(String codeLanguage) {
        String sqlStatement = toCodeLanguageNameSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, codeLanguage);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toCodeLanguageId(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
