package com.example.submission.DAO;

import com.example.submission.DTO.Submission;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLSubmissionDAO implements SubmissionDAO {

    private final BasicDataSource basicDataSource;

    public SQLSubmissionDAO(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public void insertSubmission(Submission submission) {

    }
}
