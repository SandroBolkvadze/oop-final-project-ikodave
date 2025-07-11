package com.example.admin.dao;

import com.example.admin.dto.Role;
import org.apache.commons.dbcp2.BasicDataSource;

public interface RoleDAO {


    Role getRoleById(int id);
}
