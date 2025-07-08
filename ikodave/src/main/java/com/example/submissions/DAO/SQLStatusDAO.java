package com.example.submissions.DAO;

import com.example.problems.DTO.Status;

import java.util.List;

public class SQLStatusDAO implements StatusDAO {
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
