package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Topic;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.problems.utils.ToDTO.toDifficulty;
import static com.example.problems.utils.ToDTO.toTopic;
import static com.example.problems.utils.ToDifficultySQL.*;
import static com.example.problems.utils.ToProblemTopicsSQL.*;

public class SQLDifficultyDAO implements DifficultyDAO {

    private final BasicDataSource basicDataSource;

    public SQLDifficultyDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public List<Difficulty> getDifficulties() {
        String sqlStatement = toProblemDifficultiesSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Difficulty> difficulties = new ArrayList<>();
            while (resultSet.next()) {
                difficulties.add(toDifficulty(resultSet));
            }
            return difficulties;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Difficulty getDifficultyById(int difficultyId) {
        String sqlStatement = toProblemDifficultiesByIdSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, difficultyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return toDifficulty(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Difficulty getDifficultyByName(String difficultyName) {
        String sqlStatement = toProblemDifficultiesByDifficultyNameSQL();
        try (Connection connection = basicDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, difficultyName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return toDifficulty(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
