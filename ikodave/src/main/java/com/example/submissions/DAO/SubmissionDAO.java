package com.example.submissions.DAO;

import com.example.submissions.DTO.Submission;

public interface SubmissionDAO {

    int insertSubmission(Submission submission);

    void updateSubmission(Submission submission);

}
