package com.example.submissions.DAO;

import com.example.submissions.DTO.Submission;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLSubmissionDAO implements SubmissionDAO {

    private final BasicDataSource basicDataSource;

    public SQLSubmissionDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public int insertSubmission(Submission submission) {
        return -1;
    }

    @Override
    public void updateSubmission(Submission submission) {

    }
}
