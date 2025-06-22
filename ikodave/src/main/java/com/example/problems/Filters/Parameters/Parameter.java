package com.example.problems.Filters.Parameters;

import java.sql.PreparedStatement;

public interface Parameter {

    void setParameter(int index, PreparedStatement preparedStatement);

}
