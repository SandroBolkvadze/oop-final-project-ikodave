package com.example.admin.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.admin.util.ToSQL.toInsertProblemTopicRelationSQL;

public class SQLProblemTopicRelationDAO implements ProblemTopicRelationDAO{

    private final BasicDataSource dataSource;

    public SQLProblemTopicRelationDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insertProblemTopicRelation(int problemId, int topicId) {
        String sql = toInsertProblemTopicRelationSQL();
        try (Connection con = dataSource.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, problemId);
            preparedStatement.setInt(2, topicId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting problem-topic relation failed, no rows affected.");
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error inserting problem-topic relation", e);
        }
    }
}
