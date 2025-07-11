package com.example.problems.Filters;

import com.example.problems.Filters.Filter;
import com.example.problems.Filters.FilterStatusNone;
import com.example.problems.Filters.Parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterStatusNoneTest {

    @Test
    void testToSQLStatement_CombinesSQLWithUnionAll() {
        Filter mockFilter1 = mock(Filter.class);
        Filter mockFilter2 = mock(Filter.class);

        when(mockFilter1.toSQLStatement()).thenReturn("SELECT * FROM table1");
        when(mockFilter2.toSQLStatement()).thenReturn("SELECT * FROM table2");

        FilterStatusNone filterStatusNone = new FilterStatusNone();
        filterStatusNone.addFilter(mockFilter1);
        filterStatusNone.addFilter(mockFilter2);

        String sql = filterStatusNone.toSQLStatement();

        assertTrue(sql.contains("UNION ALL"));
        assertTrue(sql.contains("SELECT * FROM table1"));
        assertTrue(sql.contains("SELECT * FROM table2"));
    }

    @Test
    void testGetParameters_CollectsAllParameters() {
        Parameter mockParam1 = mock(Parameter.class);
        Parameter mockParam2 = mock(Parameter.class);

        Filter mockFilter1 = mock(Filter.class);
        Filter mockFilter2 = mock(Filter.class);

        when(mockFilter1.getParameters()).thenReturn(List.of(mockParam1));
        when(mockFilter2.getParameters()).thenReturn(List.of(mockParam2));

        FilterStatusNone filterStatusNone = new FilterStatusNone();
        filterStatusNone.addFilter(mockFilter1);
        filterStatusNone.addFilter(mockFilter2);

        List<Parameter> parameters = filterStatusNone.getParameters();

        assertEquals(2, parameters.size());
        assertTrue(parameters.contains(mockParam1));
        assertTrue(parameters.contains(mockParam2));
    }

    @Test
    void testToSQLPreparedStatement_SetsAllParametersInOrder() throws SQLException {
        // Arrange
        Parameter mockParam1 = mock(Parameter.class);
        Parameter mockParam2 = mock(Parameter.class);

        Filter mockFilter1 = mock(Filter.class);
        Filter mockFilter2 = mock(Filter.class);

        when(mockFilter1.toSQLStatement()).thenReturn("SELECT * FROM table1 WHERE col1 = ?");
        when(mockFilter2.toSQLStatement()).thenReturn("SELECT * FROM table2 WHERE col2 = ?");

        when(mockFilter1.getParameters()).thenReturn(List.of(mockParam1));
        when(mockFilter2.getParameters()).thenReturn(List.of(mockParam2));

        FilterStatusNone filterStatusNone = new FilterStatusNone();
        filterStatusNone.addFilter(mockFilter1);
        filterStatusNone.addFilter(mockFilter2);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Act
        PreparedStatement ps = filterStatusNone.toSQLPreparedStatement(mockConnection);

        // Assert
        assertNotNull(ps);
        verify(mockConnection).prepareStatement(contains("UNION ALL"));
        verify(mockParam1).setParameter(eq(1), eq(mockPreparedStatement));
        verify(mockParam2).setParameter(eq(2), eq(mockPreparedStatement));
    }
}
