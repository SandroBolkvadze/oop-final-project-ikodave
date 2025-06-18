package com.example.problems.DAO;

import com.example.problems.DTO.Problem;
import com.example.problems.Filters.Filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.problems.utils.StringToEnum.stringToEnum;
import static com.example.util.Constants.DATABASE_PROBLEMS_TABLE;

public class MySQLProblemDAO implements ProblemDAO {

    private final Connection connection;

    public MySQLProblemDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Problem> getProblemsByFilter(Filter filter) {
        String sqlStatement = "SELECT * FROM " + DATABASE_PROBLEMS_TABLE + " WHERE " + filter.toString();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            List<Problem> problems = new ArrayList<>();
            while (resultSet.next()) {
                Problem problem = new Problem();
                problem.setId(Integer.parseInt(resultSet.getString(1)));
                problem.setTitle(resultSet.getString(2));
                problem.setDescription(resultSet.getString(3));
                problem.setProblemDifficulty(stringToEnum(resultSet.getString(4)));
                problems.add(problem);
            }
            return problems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
