package com.example.problems.Filters.Parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterString implements Parameter {

    private final String value;

    public ParameterString(String value) {
        this.value = value;
    }

    @Override
    public void setParameter(int index, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(index, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
