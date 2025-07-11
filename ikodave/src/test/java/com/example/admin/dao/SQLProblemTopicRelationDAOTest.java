package com.example.admin.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SQLProblemTopicRelationDAOTest {

    private BasicDataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    private SQLProblemTopicRelationDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        mockDataSource = mock(BasicDataSource.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao = new SQLProblemTopicRelationDAO(mockDataSource);
    }

    @Test
    void testInsertProblemTopicRelation_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.insertProblemTopicRelation(1, 2);

        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeUpdate();
        verify(mockConnection).close();
    }


    @Test
    void testInsertProblemTopicRelation_SQLException_ThrowsRuntimeException() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("DB error"));

        assertThrows(RuntimeException.class, () -> dao.insertProblemTopicRelation(1, 2));

        verify(mockPreparedStatement).executeUpdate();
    }
}
