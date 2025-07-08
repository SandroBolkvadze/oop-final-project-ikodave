package com.example.submissions.DAO;

import com.example.problems.DTO.Status;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class SQLStatusDAO implements StatusDAO {

    private final BasicDataSource basicDataSource;

    public SQLStatusDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public List<Status> getStatuses() {
        return List.of();
    }

    @Override
    public Status getStatusById(int statusId) {
        return null;
    }

    @Override
    public Status getStatusByStatusName(String statusName) {
        return null;
    }
}
