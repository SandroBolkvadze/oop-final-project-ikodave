package com.example.submissions.DAO;

import com.example.submissions.DTO.CodeLanguage;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static com.example.submissions.DAO.ToDTO.*;
import static com.example.submissions.DAO.ToSQL.*;

public class SQLCodeLanguageDAO implements  CodeLanguageDAO {
    private final BasicDataSource basicDataSource;

    public SQLCodeLanguageDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    @Override
    public CodeLanguage getCodeLanguageByName(String codeLanguage) {
        String sqlStatement = toCodeLanguageByNameSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, codeLanguage);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toCodeLanguage(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CodeLanguage getCodeLanguageById(int id) {
        String sqlStatement = toCodeLanguageByIdSQL();
        try (Connection connection = basicDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return toCodeLanguage(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CodeLanguage> getCodeLanguages() {
        try (Connection connection = basicDataSource.getConnection()){
            String sqlStatement = toCodeLanguageSQL();
            PreparedStatement preparedStatement= connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CodeLanguage> codeLanguages = new ArrayList<>();
            while (resultSet.next()) {
                codeLanguages.add(toCodeLanguage(resultSet));
            }
            return codeLanguages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
