package com.example.admin.dao;

import com.example.admin.dto.Role;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.admin.util.ToDTO.toRole;
import static com.example.admin.util.ToSQL.getRoleByIdSQL;

public class SQLRoleDAO implements RoleDAO{

    private final BasicDataSource dataSource;

    public SQLRoleDAO(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public Role getRoleById(int id) {
        String sqlStatement = getRoleByIdSQL();
        try(Connection con = dataSource.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return toRole(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
