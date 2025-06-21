package com.example.problems.Filters.Parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterInteger implements Parameter {

    private int value;

    public ParameterInteger(int value) {
        this.value = value;
    }

    @Override
    public void setParameter(int index, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setInt(index, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
