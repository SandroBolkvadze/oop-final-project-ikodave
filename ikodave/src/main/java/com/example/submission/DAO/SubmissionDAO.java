package com.example.submission.DAO;

import com.example.submission.DTO.Submission;

public interface SubmissionDAO {

    int insertSubmission(Submission submission);

    void updateSubmission(Submission submission);

}
