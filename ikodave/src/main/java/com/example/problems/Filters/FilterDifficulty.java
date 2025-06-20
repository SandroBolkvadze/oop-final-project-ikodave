package com.example.problems.Filters;

import com.example.problems.DTO.Difficulty;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.example.util.DatabaseConstants.*;
import static java.lang.String.format;

public class FilterDifficulty implements Filter {

    private final Difficulty difficulty;

    private final BasicDataSource basicDataSource;

    public FilterDifficulty(BasicDataSource basicDataSource, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.basicDataSource = basicDataSource;
    }

    @Override
    public String toSQLStatement() {
        return format(
                "SELECT * FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?",
                Problems.TABLE_NAME,
                ProblemDifficulty.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_DIFFICULTY_ID,
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.COL_ID,
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.COL_DIFFICULTY
        );
    }

    @Override
    public PreparedStatement toSQLPreparedStatement() {
        String sqlStatement = toSQLStatement();
        PreparedStatement preparedStatement = null;
        try (Connection connection = basicDataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlStatement);
            int index = 0;
            for (String parameter : getParameters()) {
                preparedStatement.setString(index++, parameter);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getParameters() {
        return List.of(difficulty.getDifficulty());
    }

}
