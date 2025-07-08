package com.example.submissions.DAO;

import com.example.problems.DTO.Status;

import java.util.List;

public interface StatusDAO {

    List<Status> getStatuses();

    Status getStatusById(int statusId);

    Status getStatusByStatusName(String statusName);

}
